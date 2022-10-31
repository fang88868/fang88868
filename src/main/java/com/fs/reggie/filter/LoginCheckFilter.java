package com.fs.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.fs.reggie.common.BaseContext;
import com.fs.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //1、获取本次请求的URI
        String requestURL=request.getRequestURI();
        log.info("拦截到的请求:{}",requestURL);
        //定义不需要处理的请求路径
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };
        
        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURL);

        //3、如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURL);
            filterChain.doFilter(request,response);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee")!=null){
            log.info("后台用户已登录,id为{}",request.getSession().getAttribute("employee"));

            //设置当前用户id
            Long empId = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);


            Long id=Thread.currentThread().getId();
            log.info("当前线程id为:{}",id);
            return;
        }else if (request.getSession().getAttribute("user")!=null){
            log.info("移动端用户已登录,id为{}",request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }else {
            log.info("用户未登录");
        }



        //5、如果未登录则返回未登录结果,通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配,检查本次请求是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls,String requestURL){
        for (String url:urls) {
            Boolean match=PATH_MATCHER.match(url,requestURL);
            if(match) {
                return true;
            }
        }
        return false;
    }
}
