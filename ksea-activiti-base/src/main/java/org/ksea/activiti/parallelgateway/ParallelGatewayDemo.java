package org.ksea.activiti.parallelgateway;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 并行网关流程
 */
public class ParallelGatewayDemo {

    /**
     * 这里以购物流程为一个案例，这个也是看了网上很多朋友都用这个案例，学习的使用也用这个demo进行学习
     * 商家：收款-->发货
     * 买家: 付款-->收货
     * 2个节点就按并行执行 流程图 activiti/bmp/parallelgateway/parallelGateway.bpmn
     */

    private ProcessEngine processEngine;

    @Before
    public void initPorcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }


    @Test
    public void deploymentProcess() {
        processEngine.getRepositoryService().createDeployment().addClasspathResource("activiti/bmp/parallelgateway/parallelGateway.bpmn").deploy();
    }


    @Test
    public void startProcessInstance() {
        processEngine.getRuntimeService().startProcessInstanceById("myProcess_1:1:4");
    }


    /**
     * 开启实例之后我们可以看到 在task中一个processinstance对应多个任务，
     * 在并发流程中 一个流程实例对应一个或者多个任务
     */

    @Test
    public void queryTaskByprocessInstanceId() {

        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId("2501").list();
        for (Task task : tasks) {
            System.out.println(task.getId() + "," + task.getName());
        }

    }


    @Test //根据executionId是返回单个任务
    public void queryTaskByExecutionId() {
        Task task = processEngine.getTaskService().createTaskQuery().executionId("2504").singleResult();
        System.out.println(task.getId() + "," + task.getName());
    }


    @Test
    public void compeltedTask() {
        processEngine.getTaskService().complete("5002");
        //在执行并行网关，一个一边执行完毕，会在并行网关处等待，等待另一边流程也到达此处，当两边同时到达 流程执行结束
    }


}
