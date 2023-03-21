package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
    @Override
    public Result<AddressBook> saveAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        this.save(addressBook);
        return Result.success(addressBook);
    }

    @Override
    public Result<List<AddressBook>> getAddressList(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<AddressBook> addressQueryWrapper = new LambdaQueryWrapper<>();
        addressQueryWrapper.eq(addressBook.getUserId() != null,AddressBook::getUserId,addressBook.getUserId());
        addressQueryWrapper.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> addressBooks = this.list(addressQueryWrapper);
        return Result.success(addressBooks);
    }

    @Override
    public Result<AddressBook> getAddressById(Long id) {
        AddressBook addressBook = this.getById(id);
        return Result.success(addressBook);
    }

    @Override
    public Result<String> updateAddress(AddressBook addressBook) {
        this.updateById(addressBook);
        return Result.success("地址修改成功");
    }

    @Override
    public Result<String> deleteAddress(Long ids) {
        this.removeById(ids);
        return Result.success("地址删除成功");
    }

    @Override
    public Result<AddressBook> setDefaultAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.set(AddressBook::getIsDefault, 0);

        this.update(queryWrapper);
        addressBook.setIsDefault(1);
        this.updateById(addressBook);
        return Result.success(addressBook);
    }

    @Override
    public Result<AddressBook> getDefaultAddress() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(userId != null,AddressBook::getUserId,userId);
        lqw.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = this.getOne(lqw);
        return Result.success(addressBook);
    }
}
