package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface CategoryService extends IService<Category> {
    void remove(Long id);

    Result<String> saveCategory(Category category);
    Result<Page> generatePage(int page, int pageSize);
    Result<String> deleteCategory(Long id);
    Result<String> updateCategory(Category category);
    Result<List<Category>> generateList(Category category);
}
