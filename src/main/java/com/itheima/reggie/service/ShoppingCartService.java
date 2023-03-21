package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    Result<ShoppingCart> saveToCart(ShoppingCart shoppingCart);

    Result<List<ShoppingCart>> getList();

    Result<String> deleteAll();
}
