package org.ksea.activiti.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.ksea.activiti.utils.ActivitiUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class WorkFlowController {

    @Resource(name = "activitiUtils")
    private ActivitiUtils activitiUtils;


    @RequestMapping(value = "deployment/manager", method = RequestMethod.GET)
    public String deploymentManager(HttpServletRequest request, Model model) {
        //获取流程部署信息
        List<Deployment> deployments = activitiUtils.listDeployment();
        //获取流程定义信息
        List<ProcessDefinition> processDefinitions = activitiUtils.listProcessDefinition();

        model.addAttribute("deployments", deployments);
        model.addAttribute("processDefinitions", processDefinitions);
        return "workflow/workflow";
    }

    @RequestMapping(value = "deployment/doDeployment", method = RequestMethod.POST)
    public String deployment(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) {

        String processName = request.getParameter("processName");

        try {
            //文件实际名称
            String filename = file.getOriginalFilename();
            if (filename.endsWith(".bpmn") || filename.endsWith(".xml")){
                this.activitiUtils.deploymentProcessByInputstream(processName, file.getInputStream());
            }else{
                this.activitiUtils.deploymentProcessByFile(processName,file.getInputStream());
            }



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件部署发生异常,message:" + e.getMessage());
        }

        return "redirect:/deployment/manager";
    }


    @RequestMapping(value = "task/manager", method = RequestMethod.GET)
    public String taskManager(HttpServletRequest request, Model model) {
        return "workflow/task";
    }

}
