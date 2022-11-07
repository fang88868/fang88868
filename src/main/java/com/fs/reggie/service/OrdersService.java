package com.fs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
