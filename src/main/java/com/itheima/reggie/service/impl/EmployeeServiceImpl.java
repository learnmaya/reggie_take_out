package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;


    public Result<Employee> loginByEmployee(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(lqw);

        if (emp == null) {
            return Result.error("Incorrect login name or password");
        }

        if (!emp.getPassword().equals(password)) {
            return Result.error("Incorrect login name or password");
        }

        if (emp.getStatus() == 0) {
            return Result.error("This employee account has been disabled");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);

    }

    @Override
    public void logoutByEmployee(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
    }

    @Override
    public Result<String> saveEmployee(HttpServletRequest request, Employee employee) {
        log.info("New Staff Information：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        this.save(employee);
        return Result.success("Add Staff Successfully！");
    }

    @Override
    public Result<Page> generatePage(int page, int pageSize, String name) {
        Page<Employee> pageInfo =new Page(page,pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!(name == null || "".equals(name)), Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        this.page(pageInfo,wrapper);
        return Result.success(pageInfo);
    }

    @Override
    public Result<String> updateEmployee(Employee employee, HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        this.updateById(employee);
        return Result.success("Staff information change successfully!");
    }

    @Override
    public Result<Employee> getByEmployeeId(Long id) {
        Employee employee = this.getById(id);
        if(employee != null){
            return Result.success(employee);
        }
        return Result.error("There is no information about the corresponding employee.");
    }
}
