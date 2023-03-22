package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "Employee-related API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.loginByEmployee(request, employee);
    }


    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //clean the id of the currently logged-in employee saved in Session
        employeeService.logoutByEmployee(request);
        return Result.success("Exit successful");
    }

    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("New Staff Information：{}", employee.toString());
        return employeeService.saveEmployee(request,employee);
    }


    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        return employeeService.generatePage(page,pageSize,name);
    }

    @PutMapping
    public Result<String> update(@RequestBody Employee employee,HttpServletRequest request) {
        log.info(employee.toString());
        return employeeService.updateEmployee(employee,request);
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("Search employee information by id...");
        return employeeService.getByEmployeeId(id);
    }


}
