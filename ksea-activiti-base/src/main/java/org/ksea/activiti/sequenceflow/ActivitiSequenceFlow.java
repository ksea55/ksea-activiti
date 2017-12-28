package org.ksea.activiti.sequenceflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * SequenceFlow 工作流的走向指示
 */
public class ActivitiSequenceFlow {

    /**
     * 案例需求，在sequenflow.bpmn流程图中
     * 我们可以可以看到在请假人发起请假的时候，填写请假填入，流程节点到达部门经理审批节点
     * 部门审批人根据请假天数进行流程的跳转,此刻sequenceflow就发挥了当前的作用
     * 在流程图中根据condition 填入表达式${days<=3}或者${days>3} 当流程节点到达部门审批人节点就会根据天数自动判断
     */

    @Test
    public void deploymentProncess() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("activiti/bmp/sequenceflow/sequenceflow.bpmn")
                .deploy();
    }

    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("myProcess_1:3:22504");
    }

    @Test //发起请假流程
    public void startTask() {

        //设置请假天数
        Map<String, Object> variables = new HashMap<>();
        variables.put("days", 4);

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("32504", variables);
    }

    @Test//部门经理开始审批
    public void deptTask() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //此刻部门经理审批 当days<=3流程审批结束 >3流程节点进入到总经理审批节点
        processEngine.getTaskService().complete("37502");

    }

}
