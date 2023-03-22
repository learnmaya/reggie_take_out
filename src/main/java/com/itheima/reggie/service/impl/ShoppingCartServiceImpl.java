package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.mapper.ShoppingCartMapper;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public Result<ShoppingCart> saveToCart(ShoppingCart shoppingCart) {
        log.info("shoppingCart={}", shoppingCart);

        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = this.getOne(queryWrapper);
        if (cartServiceOne != null) {

            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            this.updateById(cartServiceOne);
        } else {

            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return Result.success(cartServiceOne);
    }

    @Override
    public Result<List<ShoppingCart>> getList() {
        LambdaQueryWrapper<ShoppingCart> shoppingCartQW = new LambdaQueryWrapper<>();
        Long userId = BaseContext.getCurrentId();
        shoppingCartQW.eq(ShoppingCart::getUserId,userId);
        shoppingCartQW.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCarts = this.list(shoppingCartQW);
        return Result.success(shoppingCarts);
    }

    @Override
    public Result<String> deleteAll() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartQW = new LambdaQueryWrapper<>();
        shoppingCartQW.eq(ShoppingCart::getUserId,userId);
        this.remove(shoppingCartQW);
        return Result.success("Empty your shopping cart successfully");
    }

    @Override
    public Result<ShoppingCart> removeFromCart(ShoppingCart shoppingCart) {
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
            ShoppingCart dishCart = this.getOne(queryWrapper);
            dishCart.setNumber(dishCart.getNumber() - 1);
            Integer currentNum = dishCart.getNumber();
            if (currentNum > 0) {
                this.updateById(dishCart);
            } else if (currentNum == 0) {
                this.removeById(dishCart.getId());
            }
            return Result.success(dishCart);
        }
        if (setmealId != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
            ShoppingCart setmealCart = this.getOne(queryWrapper);
            setmealCart.setNumber(setmealCart.getNumber() - 1);
            Integer currentNum = setmealCart.getNumber();
            if (currentNum > 0) {
                this.updateById(setmealCart);
            } else if (currentNum == 0) {
                this.removeById(setmealCart.getId());
            }
            return Result.success(setmealCart);
        }
        return Result.error("System busy, please try again later");
    }
}
