package com.itheima.reggie.controller;

import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public Result<String> saveSetMeal(@RequestBody SetmealDto setmealDto) {
        log.info("SetMeal Information：{}", setmealDto);
        setmealService.saveSetMeal(setmealDto);
        return Result.success("SetMeal added successfully!");
    }


}
