package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * This method handles a POST request to add a new Category to the system.
     * @param category The Category object to be added to the system.
     * @return A Result object with a success message if the operation was successful.
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        log.info("category:{}",category);
        categoryService.save(category);
        return Result.success("Added new category successfully");
    }


    /**
     * This method handles a GET request to retrieve a page of Category objects from the system.
     * @param page The page number to retrieve.
     * @param pageSize The number of items to include in the page.
     * @return A Result object containing a Page object with the requested Category objects.
     */

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //Conditional Constructor,base on sort
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    @DeleteMapping
    public Result<String> delete(Long id) {
        log.info("Delete Category, id is {]");
        categoryService.remove(id);
        return Result.success("Category deleted successfully ！");
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        log.info("Modify category information");
        categoryService.updateById(category);
        return Result.success("Modified successfully !");
    }



}
