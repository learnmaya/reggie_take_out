package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveSetMeal(SetmealDto setmealDto);

    Result<Page> generatePage(int page,int pageSize, String name);

    Result<String> deleteWithDish(List<Long> ids);

    Result<SetmealDto> getByIdWithCategory(Long id);

    Result<String> updateWithId(SetmealDto setmealDto);

    void updateStatusById(Integer statusInt, Long ids);
}
