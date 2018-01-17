package org.ksea.activiti.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.ksea.activiti.vo.Ztree;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "nodes", method = RequestMethod.GET)
    public String nodes() {
        return "ztree/nodes";
    }

    @RequestMapping(value = "json", method = RequestMethod.POST)
    @ResponseBody
    public Object listJson() {
        List<Ztree> list = this.studentService.findAllNodes();
        return list;
    }

    @RequestMapping(value = "info/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object getObj(@PathVariable("id") String id) {
        return this.studentService.getStudentById(id);
    }


    /**
     * 获取所有一级节点
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "init/node", method = RequestMethod.POST)
    @ResponseBody
    public Object initNode(HttpServletRequest request) {
        List<Ztree> childrens = this.studentService.findAllFirstNodes("-1");

        return childrens;
    }


    /**
     * 异步加载
     *
     * @return
     */
    @RequestMapping(value = "async/node/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object asyncLoadNode(@PathVariable("id") String id) {

        List<Ztree> childrens = this.studentService.findAllChildrenByParentNode(id);

        return childrens;
    }


    /**
     * 根据id  post异步加载
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "async/node", method = RequestMethod.POST)
    @ResponseBody
    public Object asyncLoadNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Ztree> childrens = this.studentService.findAllChildrenByParentNode(id);

        return childrens;
    }

    /**
     * 根据id  post异步加载
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "load/async/node", method = RequestMethod.POST)
    @ResponseBody
    public Object loadAsyncLoadNode(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Ztree> childrens = this.studentService.findAllFirstNodes(id);

        return childrens;
    }


}
