package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;


    public Result<Employee> loginByEmployee(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. Query employee info from database
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(lqw);

        // 3. If the username is not found
        if (emp == null) {
            return Result.error("Incorrect login name or password");
        }

        // 4. Compare password
        if (!emp.getPassword().equals(password)) {
            return Result.error("Incorrect login name or password");
        }

        // 5. Verify if the account is disabled
        if (emp.getStatus() == 0) {
            return Result.error("This employee account has been disabled");
        }

        // 6. Store employee id in session and put back successful results
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);

    }
}
