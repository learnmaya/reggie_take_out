package com.itheima.reggie.controller;


import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
@Api(tags = "Cart-related API")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result<ShoppingCart> saveToCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.saveToCart(shoppingCart);
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> getCartList() {
        return shoppingCartService.getList();
    }

    @DeleteMapping("/clean")
    public Result<String> deleteAll() {
        return shoppingCartService.deleteAll();
    }

    @PostMapping("/sub")
    public Result<ShoppingCart> removeFromCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.removeFromCart(shoppingCart);
    }
}
