package org.ksea.activiti.controller;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.ksea.activiti.model.Approve;
import org.ksea.activiti.model.Employee;
import org.ksea.activiti.service.ApproveService;
import org.ksea.activiti.service.LeaveBillService;
import org.ksea.activiti.utils.ActivitiUtils;
import org.ksea.activiti.vo.LeaveBillVo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WorkFlowController {

    @Resource(name = "activitiUtils")
    private ActivitiUtils activitiUtils;
    @Resource
    private LeaveBillService leaveBillService;

    @Resource
    private ApproveService approveService;


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
        for (Task task : tasks) {
            task.setAssignee(user.getName());
        }

        model.addAttribute("tasks", tasks);
        return "workflow/task";
    }


    @RequestMapping(value = "task/view/{taskId}", method = RequestMethod.GET)
    public String startProcessPage(@PathVariable("taskId") String taskId, HttpServletRequest request, Model model) {

        String businessKey = this.activitiUtils.businessKeyByTaskId(taskId);

        LeaveBillVo leaveBillVo = this.leaveBillService.getLeaveBillById(Integer.parseInt(businessKey));
        model.addAttribute("lb", leaveBillVo);


        Employee user = (Employee) request.getSession().getAttribute("user");
        //查找sequenceflow
        List<PvmTransition> pvmTransitions = this.activitiUtils.listSequenceFlowByTaskId(taskId);

        List<String> sequenceFlowName = new ArrayList<>();

        for (PvmTransition pvmTransition : pvmTransitions) {
            sequenceFlowName.add(String.valueOf(pvmTransition.getProperty("name")));
        }

        //审批记录

        List<Approve> approves = approveService.listApproveByLeaveBillId(Integer.parseInt(businessKey));

        model.addAttribute("approves",approves);

        model.addAttribute("sequenceFlowName", sequenceFlowName);
        model.addAttribute("taskId",taskId);

        return "workflow/taskForm";
    }

    /**
     * 发起请假，启动流程实例，绑定业务
     *
     * @param businessKey
     * @param model
     * @return
     */
    @RequestMapping(value = "deployment/startProcess/page/{businessKey}", method = RequestMethod.GET)
    public String startProcessPage(@PathVariable("businessKey") Integer businessKey, HttpServletRequest request, Model model) {

        LeaveBillVo leaveBillVo = this.leaveBillService.getLeaveBillById(businessKey);
        model.addAttribute("lb", leaveBillVo);

        Employee user = (Employee) request.getSession().getAttribute("user");

        //启动流程实例
        this.activitiUtils.startProcessInstanceByProncessDefinitionKey("qingjiaProcess", businessKey + "", user.getId() + "");


        //查找当前登录人的task
        Task task = this.activitiUtils.getTaskByBusinessKeyAndAssignee(businessKey + "", user.getId() + "");

        //查找sequenceflow

        List<PvmTransition> pvmTransitions = this.activitiUtils.listSequenceFlowByTaskId(task.getId());

        List<String> sequenceFlowName = new ArrayList<>();

        for (PvmTransition pvmTransition : pvmTransitions) {
            sequenceFlowName.add(String.valueOf(pvmTransition.getProperty("name")));
        }


        model.addAttribute("sequenceFlowName", sequenceFlowName);


        return "workflow/taskForm";
    }

    @RequestMapping(value = "task/completed", method = RequestMethod.POST)
    public String taskCompelted(@ModelAttribute Approve approve, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        Employee user = (Employee) request.getSession().getAttribute("user");

        String businesskey = this.activitiUtils.businessKeyByTaskId(taskId);
        approve.setLeaveBillId(Integer.parseInt(businesskey));
        approve.setState("1");
        approve.setName(user.getName());

        //此处需同事更新请假单中的状态，需将两个是事务放在一起
        this.approveService.save(approve);

        //设置下一个节点审批人

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", user.getManagerId());
        //设置任务提交
        this.activitiUtils.finishTask(taskId, variables);

        return "redirect:/leave/manager";
    }


}
