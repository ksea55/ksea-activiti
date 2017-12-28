package org.ksea.activiti.exclusivegateway;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与并行网关反向的就是排它网关，排它网关也就是 要么你进入这个节点 要么你进入另外一个节点
 */
public class ExclusiveGatewayDemo {

    private ProcessEngine processEngine;

    @Before
    public void initPorcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }


    @Test
    public void deploymentProcess() {
        processEngine.getRepositoryService().createDeployment().addClasspathResource("activiti/bmp/exclusivegateway/exclusiveGateway.bpmn").deploy();
    }


    @Test
    public void startProcessInstance() {
        processEngine.getRuntimeService().startProcessInstanceById("myProcess_1:2:15004");
    }




    @Test
    public void compeltedTask() {
        Map<String,Object> variables=new HashMap<>();
        variables.put("days",4);
        processEngine.getTaskService().complete("25004",variables);

    }

    @Test
    public void shenpiTask() {
        processEngine.getTaskService().complete("30003");

    }

}
