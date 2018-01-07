package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.EmployeeDao;
import org.ksea.activiti.model.Employee;
import org.ksea.activiti.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> listEmployee() {
        return employeeDao.listEmployee();
    }
}
