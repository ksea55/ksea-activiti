package org.ksea.activiti.controller;

import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(name = "/student")
public class StudentController {


    @Resource
    private StudentService studentService;

    @RequestMapping(name = "save", method = RequestMethod.POST)
    public void save(@RequestBody Student student) {
        studentService.saveStudent(student);
    }

    @RequestMapping(name = "home", method = RequestMethod.GET)
    public String index() {

        System.out.println("11111---");
        return "index";
    }

}
