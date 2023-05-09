package com.fs.reggie.dto;


import com.fs.reggie.entity.Setmeal;
import com.fs.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
