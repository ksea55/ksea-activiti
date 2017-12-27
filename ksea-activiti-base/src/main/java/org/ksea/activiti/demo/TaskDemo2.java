package org.ksea.activiti.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态赋值与assignee
 */
public class TaskDemo2 {


    @Test// 部署流程
    public void deploymentProncess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("activiti/bmp/demo/TaskDemo2.bpmn")
                .deploy();
    }

    @Test
    public void startProncessInstance() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicantor", "小帅王");//设置发起人

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("askForLeave:13:75004", variables);
    }

    @Test //此处在申请人提交申请的任务，此处就会进行对部门task的linstener 从而实现对部门审批人的动态赋值
    public void applicantorCompletedTask() {
        //申请人提交申请任务，节点进入部门经理审批，而此刻在部门经理审批节点并没有设置assignee，二十通过TaskDemo2Linstener来进行动态设置
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Map<String, Object> variables = new HashMap<>();
        variables.put("dept_", "部门小金龙");
        processEngine.getTaskService().complete("77505", variables);
    }

    @Test
    public void deptTask() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("42502");
    }

    @Test
    public void managerTask() {
        //通过程序的方式设置assignee

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .setAssignee("52502", "王小帅");


    }

}
