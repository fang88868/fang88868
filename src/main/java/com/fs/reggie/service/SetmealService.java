package com.fs.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.fs.reggie.dto.SetmealDto;
import com.fs.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存菜品和套餐的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

    void updateSetmealStatusById(Integer status, List<Long> ids);
}
