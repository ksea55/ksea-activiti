package org.ksea.activiti.dao;

import org.ksea.activiti.model.Employee;

import java.util.List;

public interface EmployeeDao {
    /**
     * 获取所有员工信息
     *
     * @return
     */
    List<Employee> listEmployee();

}
