package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

import java.util.List;


public interface DishService extends IService<Dish> {

    Page generatePage(int page, int pageSize, String name);

    public void saveWithFlavor(DishDto dishDto);

    void updateWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    String updateStatusById(Integer status, List<Long> ids);

    Result<String> deleteDish(List<Long> ids);

    Result<List<DishDto>> getListByCategoryId(Dish dish);
}
