package org.ksea.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WorkFlowController {


    @RequestMapping(value = "deployment/manager", method = RequestMethod.GET)
    public String deploymentManager(HttpServletRequest request, Model model) {
        return "workflow/workflow";
    }

    @RequestMapping(value = "deployment/doDeployment", method = RequestMethod.POST)
    public String deployment(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {

        String processName=request.getParameter("processName");


        return "workflow/workflow";
    }


    @RequestMapping(value = "task/manager", method = RequestMethod.GET)
    public String taskManager(HttpServletRequest request, Model model) {
        return "workflow/task";
    }

}
