package com.fs.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fs.reggie.common.BaseContext;
import com.fs.reggie.common.R;
import com.fs.reggie.entity.Orders;
import com.fs.reggie.entity.ShoppingCart;
import com.fs.reggie.service.OrdersService;
import com.fs.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("orders:{}",orders);
        ordersService.submit(orders);
        return R.success("成功");
    }

}
