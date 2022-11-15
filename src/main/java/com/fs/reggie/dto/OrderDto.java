package com.fs.reggie.dto;

import com.fs.reggie.entity.OrderDetail;
import com.fs.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}
