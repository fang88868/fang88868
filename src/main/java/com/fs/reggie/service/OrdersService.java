package com.fs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.reggie.entity.OrderDetail;
import com.fs.reggie.entity.Orders;

import java.util.List;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId);
}
