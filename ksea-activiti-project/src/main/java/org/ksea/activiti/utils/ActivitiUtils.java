package org.ksea.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;

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


}
