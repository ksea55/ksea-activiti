package org.ksea.activiti.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于#{ } 方式给任务节点进行任务审批赋值
 */


public class TaskDemo1 {

    /**
     * 任务
     * 1、概念
     * 需要有人进行审批或者申请的为任务
     * 2、任务的执行人的情况
     * 1、当没有进入该节点之前，就可以确定任务的执行人
     * <userTask id="请假申请" name="请假申请" activiti:assignee="#{userId}"></userTask>
     * 在进入该节点之前，必须通过流程变量给 userId赋值
     * 2、有可能一个任务节点的执行人是固定的
     * 3、如果当前的流程实例正在执行自荐信审批，这个时候，自荐信审批没有任务执行人，只有当咨询员登录系统以后才能给该任务赋值执行人
     * 4、一个任务节点有n多人能够执行该任务，但是只要有一个人执行完毕就完成该任务了：组任务
     */


    /***
     *
     * 第一步:
     * 此处模拟 根据TaskDemo1.bpmn流程图
     * 第一步登录人登录进来，首先要填写请假流程单，填写完毕之后，提交请假流程
     * 这就其中涉及到，开启流程实例，提交任务
     */
    @Test
    public void startAskForLeaveTest() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("activiti/bmp/demo/TaskDemo1.bpmn")
                .deploy();


        String deploymentId = deployment.getId();

        //根据部署deploymentId获取对应的流程定义id-->(processDefinitionId) pdid
        ProcessDefinition processDefinition = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();

        //获取流程定义Id
        String processDefinitionId = processDefinition.getId();

        //根据流程定义Id-->pdid开启对应的流程实例
        // processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId);

        /**
         *
         * 在实际需求中，当一个用户登录之后，这个用户发起请假流程申请的时候，那么在开启流程实例的时候就需要将这个assignee给设置进行
         * 一般我们在画流程图的时候在assignee这个属性通常会设置成#{userId} 就是某某某，谁登录进来，就根据这个用户
         * 因此在开启流程实例的时候，我们需要将其进行变量设置，或者就会抛出异常
         * org.activiti.engine.ActivitiException: Unknown property used in expression: #{userId}
         *
         *
         */
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", "张三");
        //根据流程定义id，设置其变量开启流程实例
        processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId, variables);


        /**
         *
         * 当我们将请假流程填写完毕，之后确认进行提交，那么发起请假流程的任务也就提交了，而流程就将进入审批阶段
         * 因此在这里还需要直接将发起请假流程任务给直接complete
         *
         */

        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .processDefinitionId(processDefinitionId) //根据流程定义Id
                .taskAssignee(String.valueOf(variables.get("userId"))) //根据当前执行人过滤
                .singleResult();


        /**
         * 提交任务
         * 在进行任务提交的时候，一般我们会选择我们的领导，设置下一个节点任务的审批人，
         * 有的是提前就写死了，说明是这个人审批，而在流程中，某些是动态指定审批人的时候，我们则需要进行设置流程变量
         *
         */
        variables.clear();
        variables.put("deptId", "林冲");

        processEngine.getTaskService().complete(task.getId(), variables);
    }


    /**
     * 第二步：
     * 当部门领导进入审批页面之后，肯定是根据部门领导查看自己的一个任务列表，然后直接点击审批，
     * 审批就进入下一个环节，一般在这种情况，肯定下一个环节的领导是谁是确定的，当然也可以有当前部门领导直接指定，最终boss审批
     */
    @Test
    public void deptApproval() {

        //根据当前登人查询任务列表
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("林冲")
                .singleResult(); //这里应该用的是list，而我这里知道只有一个任务

        Map<String, Object> variables = new HashMap<>();
        variables.put("managerId", "马云");
        processEngine.getTaskService().complete(task.getId(),variables);


    }


    @Test //总经理审批 流程结束
    public void managerApproval(){
        //根据当前登人查询任务列表
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("马云")
                .singleResult(); //这里应该用的是list，而我这里知道只有一个任务
        processEngine.getTaskService().complete(task.getId());
    }

}
