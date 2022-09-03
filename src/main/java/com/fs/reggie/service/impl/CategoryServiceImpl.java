package com.fs.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fs.reggie.common.CustomException;
import com.fs.reggie.entity.Category;
import com.fs.reggie.entity.Dish;
import com.fs.reggie.entity.Setmeal;
import com.fs.reggie.mapper.CategoryMapper;
import com.fs.reggie.service.CategoryService;

import com.fs.reggie.service.DishService;
import com.fs.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要判断
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishLambdaQueryWrapper);

    //查看当前分类是否关联了菜品，如果已经关联，则抛出一个业务异常
        if(count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
    //查看当前分类是否关联了套餐，如果已经关联，则抛出一个业务异常
        if(count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
    //正常删除分类
        super.removeById(id);
    }
}