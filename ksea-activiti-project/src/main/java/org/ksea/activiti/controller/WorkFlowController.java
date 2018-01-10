package org.ksea.activiti.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.ksea.activiti.model.Employee;
import org.ksea.activiti.utils.ActivitiUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            if (filename.endsWith(".bpmn") || filename.endsWith(".xml")) {
                this.activitiUtils.deploymentProcessByInputstream(processName, file.getInputStream());
            } else {
                this.activitiUtils.deploymentProcessByFile(processName, file.getInputStream());
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件部署发生异常,message:" + e.getMessage());
        }

        return "redirect:/deployment/manager";
    }


    @RequestMapping(value = "deployment/remove/{id}", method = RequestMethod.GET)
    public String removeDeploymentById(@PathVariable("id") String id) {
        this.activitiUtils.removeDeploymentById(id);
        return "redirect:/deployment/manager";
    }

    @RequestMapping(value = "deployment/show/image/{pdid}", method = RequestMethod.GET)
    @ResponseBody
    public void showProcessImage(@PathVariable("pdid") String pdid, HttpServletResponse response) {
        //  InputStream inputStream = this.activitiUtils.showProcessImages(pdid);
        InputStream inputStream = this.activitiUtils.generatorProcessImage(pdid);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();

            byte[] bytes = new byte[10 * 1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
                if (null != outputStream)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @RequestMapping(value = "task/manager", method = RequestMethod.GET)
    public String taskManager(HttpServletRequest request, Model model) {
        Employee user = (Employee) request.getSession().getAttribute("user");
        List<Task> tasks = this.activitiUtils.listTask(user.getId() + "");
        model.addAttribute("tasks", tasks);


        return "workflow/task";
    }

}
