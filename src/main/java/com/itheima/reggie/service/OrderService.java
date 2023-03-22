package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Orders;


import java.util.Map;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

    Result<Page> getPage(int page, int page1);

    Result<String> orderAgain(Map<String,String> map);

    Result<Page> getOrderHistoryPage(int page, int pageSize, Long number, String beginTime, String endTime);

    Result<String> changeStatus(Map<String, String> map);
}
