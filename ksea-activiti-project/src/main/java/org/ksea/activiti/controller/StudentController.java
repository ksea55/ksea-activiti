package org.ksea.activiti.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "ztree")
public class StudentController {


    @Resource
    private StudentService studentService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@ModelAttribute Student student) {
        studentService.save(student);
    }

    @RequestMapping(value = "add/{parent}", method = RequestMethod.GET)
    public String add(@PathVariable("parent") String parent, Model model) {
        model.addAttribute("parent", parent);
        return "ztree/add";
    }


    /**
     * 查找一级菜单
     *
     * @return
     */

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return "ztree/home";
    }

    @RequestMapping(value = "json", method = RequestMethod.POST)
    @ResponseBody
    public Object listJson() {
        List<Student> list = this.studentService.list();
        return list;
    }

    @RequestMapping(value = "info/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object getObj(@PathVariable("id") String id) {
        return this.studentService.getStudentById(id);
    }


}
