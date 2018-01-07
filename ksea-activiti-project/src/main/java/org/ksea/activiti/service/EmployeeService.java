package org.ksea.activiti.service;

import org.ksea.activiti.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {

    List<Employee>  listEmployee();
}
