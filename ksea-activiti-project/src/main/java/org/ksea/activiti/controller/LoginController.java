package org.ksea.activiti.controller;

import org.ksea.activiti.model.Employee;
import org.ksea.activiti.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    private EmployeeService employeeService;

    Logger logger= LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {


        logger.debug(" --this is  log---");

        List<Employee> employees = employeeService.listEmployee();
        model.addAttribute("employees", employees);
        return "login";
    }
}
