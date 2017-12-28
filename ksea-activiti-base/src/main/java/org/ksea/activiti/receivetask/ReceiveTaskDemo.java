package org.ksea.activiti.receivetask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * receiveTask与userTask区别
 * <p>
 * usertask 在节点的时候是有执行人的，assignee 并且 usertask在act_ru_task是有数据的
 * <p>
 * <p>
 * <p>
 * receiveTask
 * <p>
 * 接收任务（ReceiveTask）即等待任务，接收任务是一个简单任务，它会等待对应消息的到达。当前，官方只实现
 * 了这个任务的java语义。 当流程达到接收任务，流程状态会保存到数据库中。在任务创建后，意味着流程会进入等
 * 待状态，直到引擎接收了一个特定的消息， 这会触发流程穿过接收任务继续执行。
 * 它只在act_ru_execution表中有数据
 */
public class ReceiveTaskDemo {


    /**
     * 来看这样一个案例receive.bpmn
     * 一个贷款申请的一个流程，当贷款审批通过之后，流程节点下划到贷款信息通知节点，然后短信直接通知到对应的贷款人
     * 此刻该节点就不需要认为干预，而是系统自己下划
     */

    @Test
    public void deploymentProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService().createDeployment().addClasspathResource("activiti/bmp/receivetask/receive.bpmn").deploy();
    }


    @Test
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService().startProcessInstanceById("daikuan:1:42504");
    }

    @Test
    public void startTask() {
        /**
         * 贷款审批通过之
         *
         * */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("45004");
    }


    @Test
    public void receiveTaskTest() {

        //此处当贷款审批通过之后，就进入贷款信息下划节点，而此刻任务节点在此刻处于等待状态，需要等到某个信号，执行到下一个节点
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId("45001").singleResult();
        Execution execution = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstance.getId()).singleResult();

        //给出信号执行短信下发
        processEngine.getRuntimeService().signal(execution.getId());


    }

}
