package com.fs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.reggie.dto.DishDto;
import com.fs.reggie.entity.Dish;


import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时加入菜品的口味，操作dish、dishflavor两张表
    public void saveWithFlavor(DishDto dishDto);
}
