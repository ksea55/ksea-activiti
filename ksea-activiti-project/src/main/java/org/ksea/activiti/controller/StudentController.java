package org.ksea.activiti.controller;

import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "student")
public class StudentController {


    @Resource
    private StudentService studentService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@ModelAttribute Student student) {
        studentService.saveStudent(student);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void add(@ModelAttribute Student student) {

        studentService.save(student);
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home() {

        return "home";
    }

}
