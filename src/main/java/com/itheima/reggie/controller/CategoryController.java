package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
@Api(tags = "Category-related API")
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
        log.info("Category:{}",category);
        return categoryService.saveCategory(category);
    }


    /**
     * This method handles a GET request to retrieve a page of Category objects from the system.
     * @param page The page number to retrieve.
     * @param pageSize The number of items to include in the page.
     * @return A Result object containing a Page object with the requested Category objects.
     */

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        return categoryService.generatePage(page,pageSize);
    }

    @DeleteMapping
    public Result<String> delete(Long id) {
        log.info("Delete Category, id is {]");
        return categoryService.deleteCategory(id);
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        log.info("Modify category information");
        return categoryService.updateCategory(category);
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Category category) {
        return categoryService.generateList(category);
    }

}
