package org.ksea.activiti.utils;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 所有activiti中的工具类
 */
public class ActivitiUtils {

    @Resource //基于spring容器中的流程引擎
    private ProcessEngine processEngine;


    /**
     * 根据流程名称与流程文件bpmn部署流程
     *
     * @param processName 流程名称
     * @param bpmnFile    流程图bpmn
     */
    public void deploymentProcess(String processName, File bpmnFile) throws FileNotFoundException {
        //得到部署
        DeploymentBuilder deployment = processEngine.getRepositoryService().createDeployment();
        String fileName = bpmnFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        InputStream inputStream = new FileInputStream(bpmnFile);

        if (suffix.equals("zip")) {
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            deployment.addInputStream(processName, inputStream).deploy();
        }

        deployment.addInputStream(processName, inputStream).deploy();

    }


    /**
     * 获取所有的流程部署列表
     *
     * @return
     */
    public List<Deployment> listDeployment() {
        return processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime() //按照部署信息降序排序
                .desc()
                .list();
    }

    /**
     * 根据流程部署Id删除流程部署
     *
     * @param deploymentId
     */
    public void removeDeploymentById(String deploymentId) {
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
    }

    /**
     * 获取流程定义信息列表
     *
     * @return
     */
    public List<ProcessDefinition> listProcessDefinition() {

        return processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion() //根据流程定义版本号降序排序
                .desc()
                .list();
    }


    /**
     * 根据流程定义id返回流程图，这里返回流程图的inputstream
     *
     * @param processDefinitionId
     * @return
     */
    public InputStream showProcessImages(String processDefinitionId) {
        return processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);


    }


    /**
     * 在启动流程实例的时候，我需要绑定其主业务的id与acitviti进行业务绑定businessKey
     * 并设定其任务申请人，也就是任务提交人
     *
     * @param processDefinitionId 流程定义Id
     * @param businessKey         绑定业务id
     * @param userId              提交人id
     */
    public void startProcessInstanceByProcessDefinitionId(String processDefinitionId, String businessKey, String userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId, businessKey, variables);
    }


    /**
     * 根据key启动流程实例，以最新流程版本启动
     *
     * @param processDefinitionKey
     * @param businessKey
     * @param userId
     */
    public void startProcessInstanceByProncessDefinitionKey(String processDefinitionKey, String businessKey, String userId) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

    }

    /**
     * 根据当前登录人，查询任务列表
     *
     * @param userId
     * @return List<Task>
     */
    public List<Task> listTask(String userId) {

        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }


    /**
     * 根据taskId获取当前任务
     *
     * @param taskId
     * @return Task
     */
    public Task getTaskById(String taskId) {

        return processEngine.getTaskService()
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
    }


    /**
     * 根据taskId获取当前流程流程实例
     *
     * @param taskId
     * @return ProcessInstance
     */
    public ProcessInstance getProcessInstanceByTaskId(String taskId) {
        //根据taskId获取到当前流程实例Id
        String processInstanceId = getTaskById(taskId).getProcessInstanceId();

        //根据流程实例Id获取当前流程实例
        return processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    /**
     * 根据taskId获取当前流程定义
     *
     * @param taskId
     * @return ProcessDefinition
     */
    public ProcessDefinition getProcessDefinitionByTaskId(String taskId) {

        //根据taskId获取到当前流程定义Id
        String processDefinitionId = getTaskById(taskId).getProcessDefinitionId();

        //根据pdid获取当前流程定义
        return processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
    }

    /**
     * 根据taskId获取流程定义实体
     *
     * @param taskId
     * @return ProcessDefinitionEntity
     */
    private ProcessDefinitionEntity getProcessDefinitionEntityByTaskId(String taskId) {

        String processDefinitionId = getTaskById(taskId).getProcessDefinitionId();

        //根据流程定义Id-->pdid获取到流程定义实体 ProcessDefinitionEntity
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return processDefinitionEntity;

    }

    /**
     * 根据taskId获取当前流程活动节点 ActivityImpl
     *
     * @param taskId
     * @return
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) {

        //获取当前流程实例的活动节点Id --> activityId
        String activityId = getProcessInstanceByTaskId(taskId).getActivityId();

        //获取当前流程定义实体 ProcessDefinitionEntity
        ProcessDefinitionEntity processDefinitionEntity = getProcessDefinitionEntityByTaskId(taskId);

        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);

        return activity;
    }

    /**
     * 根据taskId获取当前活动任务节点上的所有SequenceFlow对象(PvmTransition),根据sequenceFlow的实体类获取到它的名称，动态生成每个任务节点的审批按钮描述信息
     *
     * @param taskId
     * @return List<PvmTransition>
     */

    public List<PvmTransition> listSequenceFlowByTaskId(String taskId) {
        //获取到当前正在执行的活动任务节点
        ActivityImpl activity = getActivityImplByTaskId(taskId);

        //获取到当前任务活动节点上的所有sequenceFlow
        List<PvmTransition> pvmTransitions = activity.getOutgoingTransitions();

        return pvmTransitions;

    }


    /**
     * 根据taskId获取到外面与业务绑定的businesskey，从而根据该key关联其他
     *
     * @param taskId
     * @return businesskey
     */
    public String businessKeyByTaskId(String taskId) {

        return getProcessInstanceByTaskId(taskId).getBusinessKey();

    }


    public ProcessInstance finishTask(String taskId) {

        //完成任务
        processEngine.getTaskService().complete(taskId);

        return getProcessInstanceByTaskId(taskId); //如果PI是null表明整个流程已经执行完毕，如果不为null表明流程正在执行中


    }

}
