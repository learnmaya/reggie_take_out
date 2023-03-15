package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        // 1.Md5 encryption of passwords.
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // Query employee info from database

        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lqw);

        // 3. if didn't find the username
        if (emp == null) {
            return Result.error("Incorrect login name or password");
        }

        // 4. compare password

        if (!emp.getPassword().equals(password) ) {
            return Result.error("Incorrect login name or password");
        }

        // 5. Verify does the account disabled

        if (emp.getStatus() == 0) {
            return Result.error("This employee account has been disabled");
        }

        // 6.Store employee id in session and put back successful results
        request.getSession().setAttribute("employee",emp.getId());
        return Result.success(emp);

    }

    /**
     * Employee logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //clean the id of the currently logged-in employee saved in Session
        request.getSession().removeAttribute("employee");
        return Result.success("Exit successful");
    }

    /**
     * Adding New staff
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("New Staff Information：{}", employee.toString());

        //Set the default password to 123456 and use MD5 encryption
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // Handled by Mybatis, MyMetaObjectHandler
        //Set createTime & updateTime
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //Get  user id of the creator based on the session
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        //Save to database,
        employeeService.save(employee);

        return Result.success("Add Staff Successfully！");
    }

    /**
     * Staff Paging queries
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);

        Page pageInfo =new Page(page,pageSize);

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!(name == null || "".equals(name)), Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,wrapper);
        return Result.success(pageInfo);
    }

    /**
     * Update staff information base on ID
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Employee employee,HttpServletRequest request) {
        log.info(employee.toString());
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return Result.success("Staff information change successfully!");
    }

    /**
     * Query employee information by id, this function is just to display information in add.html
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("Search employee information by id...");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return Result.success(employee);
        }
        return Result.error("There is no information about the corresponding employee.");
    }
}
