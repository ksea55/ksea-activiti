package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 流程实例
 */
public class ProcessInstanceTest {


    /**
     * 开启流程实例之后
     * act_hi_actinst  流程实例历史表中就有其历史数据
     * act_hi_identitylink 当前流程指定的审批人员信息进入历史数据中
     * act_hi_procinst 当前流程节点历史数据，当前执行了的流程节点信息 会保存到该表中，没执行的不会
     * 历史流程实例与正在执行中的流程实例都在这张表中
     * 如何判断流程是否执行完毕？是根据END_TIME_字段 如果有值表明已经执行完毕，如果没值表明正在执行中
     * act_hi_taskinst 当前任务节点的历史数据信息
     * 其中历史任务与正在执行的任务都会在改表中
     * 如何判断当前的任务是否是已经执行完毕的？也是根据END_TIME_是否有值来进行判断的，有值执行完毕，没值，正在执行的任务
     * act_ru_execution 当前执行节点
     * act_ru_identitylink 当前任务节点上的审批人
     * act_ru_task 当前节点的任务信息
     */
    @Test
    public void startProcessInstanceById() {
        //这里是根据流程实例id进行流程的实例开启

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //开启流程实例
        processEngine.getRuntimeService()
                .startProcessInstanceById("myProcess_1:2:10004");

    }


    /**
     * 根据流程实例的key开启流程
     */
    @Test
    public void startProcessInstanceByKey() {
        /**
         * 流程实例的key 可能在流程定义中 有多条同样的数据 key
         * 而根据流程实例的key开启流程实例，是根据最新部署的版本开启流程
         *
         *
         */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey("myProcess_1");


    }


    @Test //完成任务
    public void finishTask() {

        /**
         *
         * act_ru_task
         *    当任务被complete之后，在task表中，该数据就不存在了，从而进入下一个任务，task表在这当中只是一个临时表
         *    被complete的任务，会在历史表中的任务中END_TIME_的字段就不是null 而是执行任务结束的时间
         *
         *
         */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("17504"); //根据任务id提交任务，完成当前任务


    }


    /*
     查询所有任务
     */

    @Test
    public void queryTask() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }


    }


    /**
     * 根据任务执行认查询任务
     */
    @Test
    public void queryTaskByAssignee() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .orderByTaskCreateTime() //根据任务创建时间排序
                .desc() //按降序排序
                .taskAssignee("张学友") //并根据当前执行人查询任务
                .list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }


    }


    @Test
    public void queryTaskByExecutionIdOrProcessInstanceId() {

        //根据流程实例I的查询当前任务
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> list = processEngine.getTaskService().createTaskQuery().processInstanceId("20001").list();
        for (Task task : list) {
            System.out.println(task.getName());
        }


        //根据流程执行ID查询当前任务列表，当流程执行的时候不存在并发执行的情况，其executionId是等于ProcessInstanceId的
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().executionId("20001").list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }


    }


    /**
     * 根据流程ID获取到正在执行的流程实例的正在活动的流程节点
     */
    @Test
    public void queryActiveTaskByProcessInstanceId() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


        /**
         * 根据流程实例Id获取到该流程实例
         */
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("17501")
                .singleResult();

        //此刻就获取到当前正在活动的流程节点Id
        String activityId = processInstance.getActivityId();

        System.out.println(activityId);

        //获取现在活动节点列表任务
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId("17501").active().list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }


    }


}
