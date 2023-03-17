package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CategoryServiceImpl class extends the ServiceImpl class from the MyBatis-Plus framework.
 * The ServiceImpl class is a generic class that provides basic CRUD (create, read, update, delete) operations on a database table.
 * The first generic type parameter CategoryMapper specifies the type of the mapper interface
 * that the CategoryServiceImpl class will use to interact with the database.
 * The CategoryMapper interface should contain methods to perform CRUD operations on the Category entity.
 * The second generic type parameter Category specifies the type of the entity that the CategoryServiceImpl class will manage.
 * The Category class is an entity that represents a category in a system.
 * By extending the ServiceImpl class, the CategoryServiceImpl class inherits all the basic CRUD operations provided by the ServiceImpl class.
 * The CategoryServiceImpl class can also define additional methods to implement custom business logic or to override the default behavior provided by the ServiceImpl class.
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setMealService;

    /**
     * Delete Category,if current category is not linked to other dish or set meal
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int countDish = dishService.count(dishLambdaQueryWrapper);
        log.info("The number of entries for the dish query：{}",countDish);
        if (countDish > 0) {
            throw new CustomException("This category has already tied to dishes so it cannot be deleted.");

        }

        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int countSetMeal = setMealService.count(setMealLambdaQueryWrapper);
        log.info("The number of entries for the dish query setmeal：{}",countSetMeal);
        if (countSetMeal > 1) {
            throw new CustomException("This category has already tied to set meals so it cannot be deleted.");
        }
        super.removeById(id);
    }

    @Override
    public Result<String> saveCategory(Category category) {
        this.save(category);
        return Result.success("Added new category successfully");
    }

    @Override
    public Result<Page> generatePage(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //Conditional Constructor,base on sort
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        this.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    @Override
    public Result<String> deleteCategory(Long id) {
        this.remove(id);
        return Result.success("Category deleted successfully ！");
    }

    @Override
    public Result<String> updateCategory(Category category) {
        this.updateById(category);
        return Result.success("Modified successfully !");
    }

    @Override
    public Result<List<Category>> generateList(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = this.list(lambdaQueryWrapper);
        return Result.success(list);
    }
}
