package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
