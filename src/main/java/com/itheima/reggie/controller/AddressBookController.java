package com.itheima.reggie.controller;


import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public Result<AddressBook> saveAddress(@RequestBody AddressBook addressBook) {
        log.info("Detail:{}",addressBook.toString());
        return addressBookService.saveAddress(addressBook);
    }

    @GetMapping("/list")
    public Result<List<AddressBook>> getAddressList(AddressBook addressBook) {
        return addressBookService.getAddressList(addressBook);
    }

    @GetMapping("/{id}")
    public Result<AddressBook> getAddressById(@PathVariable Long id) {
        log.info("The id we got {}",id);
        return addressBookService.getAddressById(id);
    }

    @PutMapping
    public Result<String> updateAddress(@RequestBody AddressBook addressBook) {
        return addressBookService.updateAddress(addressBook);
    }

    @GetMapping("/default")
    public Result<AddressBook> getDefaultAddress() {
        return addressBookService.getDefaultAddress();
    }

    @PutMapping("/default")
    public Result<AddressBook> setDefaultAddress(@RequestBody AddressBook addressBook) {
        return addressBookService.setDefaultAddress(addressBook);
    }

    @DeleteMapping
    public Result<String> deleteAddress(@RequestParam Long ids) {
        return addressBookService.deleteAddress(ids);
    }


}
