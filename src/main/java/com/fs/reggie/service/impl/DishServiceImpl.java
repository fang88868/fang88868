package com.fs.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.reggie.dto.DishDto;
import com.fs.reggie.entity.Dish;
import com.fs.reggie.entity.DishFlavor;
import com.fs.reggie.mapper.DishMapper;
import com.fs.reggie.service.DishFlavorService;
import com.fs.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品，同时保存口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long id = dishDto.getId();//获取菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item -> {
            item.setDishId(id);
            return item;
        })).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dishflavor
        dishFlavorService.saveBatch(flavors);

    }



}
