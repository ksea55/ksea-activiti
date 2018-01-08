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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    private EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        logger.debug(" --this is  log---");
        List<Employee> employees = employeeService.listEmployee();
        model.addAttribute("employees", employees);
        return "login";
    }

    @RequestMapping(value = "login/doLogin", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request) {

        String userId = request.getParameter("userId");
        Employee employee = employeeService.getEmployeeById(userId);
        //将登录信息保存到session中
        request.getSession().setAttribute("user", employee);

        return "main";
    }


    @RequestMapping(value = "login/top", method = RequestMethod.GET)
    public String pageTop() {

        return "top";
    }

    @RequestMapping(value = "login/left", method = RequestMethod.GET)
    public String pageLeft() {
        return "left";

    }

    @RequestMapping(value = "login/right", method = RequestMethod.GET)
    public String pageRight() {

        return "welcome";
    }


    @RequestMapping(value = "login/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        //移除session中的user对象
        request.getSession().removeAttribute("user");

        //注销整个session,在session中的所有内容都会被清空
        //request.getSession().invalidate();


        //重定向到controller的时候需要加上/表示跟路径
        return "redirect:/login";
    }
}
