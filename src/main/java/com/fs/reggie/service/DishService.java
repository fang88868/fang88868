package com.fs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.reggie.dto.DishDto;
import com.fs.reggie.entity.Dish;


import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时加入菜品的口味，操作dish、dishflavor两张表
    public void saveWithFlavor(DishDto dishDto);
    //根据菜品id查询菜品信息和对应口味
    public DishDto getByIdWithFlavor(Long id);
    //更新信息及口味信息
    public void updateWithFlavor(DishDto dishDto);

    void deleteByIds(List<Long> ids);
}
