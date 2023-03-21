package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.DishDto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return Result.success("Add new dish successfully ！");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        Page dishDtoPage = dishService.generatePage(page,pageSize,name);
        return Result.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public Result<DishDto> getByIdWithFlavor(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        log.info("The query returned the following data: {}", dishDto);
        return Result.success(dishDto);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
        log.info("The received data is：{}", dishDto);
        dishService.updateWithFlavor(dishDto);
        return Result.success("Modified dishes successfully");
    }

    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        dishService.updateStatusById(status,ids);
        return Result.success("Modify status successfully！");
    }

    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam List<Long> ids) {

        return dishService.deleteDish(ids);
    }

    @GetMapping("/list")
    public Result<List<DishDto>> getDishByCategoryList(Dish dish){
        log.info("The list we trying to get {}",dish.getCategoryId());
        return dishService.getListByCategoryId(dish);
    }

}
