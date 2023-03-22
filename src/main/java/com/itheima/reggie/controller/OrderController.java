package com.itheima.reggie.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrderService;
import com.itheima.reggie.service.ShoppingCartService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/order")
@Slf4j
@Api(tags = "Order-related API")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/submit")
    public Result<String> submitOrder(@RequestBody Orders orders) {
        log.info("orders:{}", orders);
        orderService.submit(orders);
        return Result.success("Customer order successfully");
    }

    @GetMapping("/userPage")
    public Result<Page> getPage(int page,int pageSize) {
        return orderService.getPage(page,pageSize);
    }

    @PostMapping("/again")
    public Result<String> again(@RequestBody Map<String,String> map){
        return orderService.orderAgain(map);
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, Long number, String beginTime, String endTime) {
        return orderService.getOrderHistoryPage(page,pageSize,number,beginTime,endTime);
    }

    @PutMapping
    public Result<String> changeStatus(@RequestBody Map<String, String> map) {
        return orderService.changeStatus(map);
    }
}
