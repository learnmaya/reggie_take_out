package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    Result<Employee> loginByEmployee(HttpServletRequest request, @RequestBody Employee employee);
    void logoutByEmployee(HttpServletRequest request);
    Result<String> saveEmployee(HttpServletRequest request, @RequestBody Employee employee);

    Result<Page> generatePage(int page, int pageSize, String name);
    Result<String> updateEmployee(@RequestBody Employee employee,HttpServletRequest request);

    Result<Employee> getByEmployeeId(@PathVariable Long id);
}
