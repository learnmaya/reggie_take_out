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
        //获取当前用户id
        Long currentId = BaseContext.getCurrentId();
        //设置当前用户id
        shoppingCart.setUserId(currentId);
        //获取当前菜品id
        Long dishId = shoppingCart.getDishId();
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //判断添加的是菜品还是套餐
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //查询当前菜品或者套餐是否在购物车中
        ShoppingCart cartServiceOne = this.getOne(queryWrapper);
        if (cartServiceOne != null) {
            //如果已存在就在当前的数量上加1
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            this.updateById(cartServiceOne);
        } else {
            //如果不存在，则还需设置一下创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            //如果不存在，则添加到购物车，数量默认为1
            this.save(shoppingCart);
            //这里是为了统一结果，最后都返回cartServiceOne会比较方便
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


}
