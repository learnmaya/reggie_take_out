package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
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
    };
}
