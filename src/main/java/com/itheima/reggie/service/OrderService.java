package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);

    Result<Page> getPage(int page, int page1);
}
