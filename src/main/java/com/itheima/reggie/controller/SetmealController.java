package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.SetmealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
@Api(tags = "SetMeal-related API")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public Result<String> saveSetMeal(@RequestBody SetmealDto setmealDto) {
        log.info("SetMeal Information：{}", setmealDto);
        setmealService.saveSetMeal(setmealDto);
        return Result.success("SetMeal added successfully!");
    }

    @GetMapping("/page")
    public Result<Page> generatePage (int page,int pageSize, String name) {
        return setmealService.generatePage(page,pageSize,name);
    }

    @GetMapping("/{id}")
    public Result<SetmealDto> getByIdWithCategory (@PathVariable Long id) {
        return setmealService.getByIdWithCategory(id);
    }

    @PutMapping
    public Result<String> updateWithId(@RequestBody SetmealDto setmealDto) {
        return setmealService.updateWithId(setmealDto);
    }

    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable String status, @RequestParam List<Long> ids) {
        Integer statusInt = Integer.valueOf(status);
        setmealService.updateStatusById(statusInt,ids);
        return Result.success("Modify status successfully！");
    }

    @GetMapping("/list")
    public Result<List<Setmeal>> getListById(Setmeal setmeal) {
        return setmealService.getListById(setmeal);
    }

    @DeleteMapping
    public Result<String> deleteByIds(@RequestParam List<Long> ids) {
        log.info("The setmeal id to be deleted is：{}",ids);
        setmealService.deleteWithDish(ids);
        return Result.success("Deleted successfully");
    }


}
