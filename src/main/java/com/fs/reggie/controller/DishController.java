package com.fs.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.reggie.common.R;


import com.fs.reggie.dto.DishDto;
import com.fs.reggie.entity.Category;
import com.fs.reggie.entity.Dish;
import com.fs.reggie.service.CategoryService;
import com.fs.reggie.service.DishFlavorService;
import com.fs.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info("dishDto:{}",dishDto);
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page<Dish> page1=new Page<>(page,pageSize);
        Page<DishDto> page2=new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        //添加过滤条件
        queryWrapper.like(name!=null,Dish::getName,name);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getUpdateTime);

        //执行查询
        dishService.page(page1,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(page1,page2,"records");

        List<Dish> records=page1.getRecords();
        List<DishDto> list=records.stream().map((item) ->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        page2.setRecords(list);

        return R.success(page2);
    }

    /**
     * 根据id查询菜品信息及对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info("dishDto:{}",dishDto);
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }
}


