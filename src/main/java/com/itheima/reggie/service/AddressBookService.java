package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {
    Result<AddressBook> saveAddress(AddressBook addressBook);

    Result<List<AddressBook>> getAddressList(AddressBook addressBook);


    Result<AddressBook> getAddressById(Long id);

    Result<String> updateAddress(AddressBook addressBook);

    Result<String> deleteAddress(Long ids);

    Result<AddressBook> setDefaultAddress(AddressBook addressBook);
}
