package com.fs.reggie.common;

/**
 * 基于TreadLocal封装工具类，用于保存和获取当前用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static long getCurrentId(){
        return threadLocal.get();
    }
}
