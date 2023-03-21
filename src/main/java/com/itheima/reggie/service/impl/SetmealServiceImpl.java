package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    protected SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public void saveSetMeal(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public Result<Page> generatePage(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>();
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();

        lqw.like(name != null,Setmeal::getName,name);
        lqw.orderByDesc(Setmeal::getUpdateTime);
        this.page(setmealPage,lqw);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //将数据赋给dishDto对象
            BeanUtils.copyProperties(item, setmealDto);
            //然后获取一下dish对象的category_id属性
            Long categoryId = item.getCategoryId();  //分类id
            //根据这个属性，获取到Category对象（这里需要用@Autowired注入一个CategoryService对象）
            Category category = categoryService.getById(categoryId);
            //随后获取Category对象的name属性，也就是菜品分类名称
            String categoryName = category.getName();
            //最后将菜品分类名称赋给dishDto对象就好了
            setmealDto.setCategoryName(categoryName);
            //结果返回一个dishDto对象
            return setmealDto;
            //并将dishDto对象封装成一个集合，作为我们的最终结果
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return Result.success(setmealDtoPage);
    }

    @Override
    @Transactional
    public Result<String> deleteWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId,ids);
        lqw.eq(Setmeal::getStatus,1);
        int count = this.count(lqw);
        if (count > 0) {
            throw new CustomException("Set meal is on sale, please stop selling before deleting!");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> setmealDishQW = new LambdaQueryWrapper<>();
        setmealDishQW.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(setmealDishQW);
        return Result.success("Setmeal deleted successfully！");
    }

    @Override
    public Result<SetmealDto> getByIdWithCategory(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.list(lqw);
        setmealDto.setSetmealDishes(setmealDishes);

        return Result.success(setmealDto);
    }

    @Override
    @Transactional
    public Result<String> updateWithId(SetmealDto setmealDto) {
        Long setmealId = setmealDto.getId();

        this.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(lqw);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
        return Result.success("Modified setmeal successfully");
    }

    @Override
    public void updateStatusById(Integer statusInt, List<Long> ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId, ids);
        updateWrapper.set(Setmeal::getStatus, statusInt);
        this.update(updateWrapper);
    }

    @Override
    public Result<List<Setmeal>> getListById(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null,Setmeal::getStatus,1);
        lqw.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = this.list(lqw);
        return Result.success(list);
    }
}
