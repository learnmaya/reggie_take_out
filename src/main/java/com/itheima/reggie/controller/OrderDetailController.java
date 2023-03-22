package com.itheima.reggie.controller;

import com.itheima.reggie.service.OrderDetailService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/orderDetail")
@Api(tags = "Order Detail API")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
}
