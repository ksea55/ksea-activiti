package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 基本的activiti测试
 */
public class BaseActiviti {

    @Test
    public void processTable1() {
        //执行改程序之后,mysql数据库将自动创建25张关于activiti表结构信息
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti/config/activiti.cfg.xml")
                .buildProcessEngine();
    }

    @Test//指定默认加载形式需要将配置文件放在根目录
    public void processTable2() {

        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
    }


    @Test //开始部署
    public void deployment1() {
        //这种方式,可以不将 activiti.cfg.xml 配置文件放在根目录中
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti/config/activiti.cfg.xml")
                .buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService(); //获取repositoryService服务
        repositoryService.createDeployment() //创建deployment
                .addClasspathResource("activiti/bmp/qingjia.bpmn")//加载流程文件
                .addClasspathResource("activiti/bmp/qingjia.png")
                .deploy();//部署流程
    }


    @Test
    public void deployment2() {
        //这种方式 需要将activiti.cfg.xml 配置文件放在根目录下
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        processEngine.getRepositoryService()
                .createDeployment()
                .name("测试请假流程")
                .addClasspathResource("activiti/bmp/qingjiaProcess.bpmn")//加载流程文件
                .deploy();

    }

    @Test
    public void deployment3() {
        //这种方式 需要将activiti.cfg.xml 配置文件放在根目录下
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("activiti/bmp/qingjiaProcess.zip");
        ZipInputStream zipInputStream= new ZipInputStream(inputStream);


        processEngine.getRepositoryService()
                .createDeployment()
                .name("测试zip请假流程")
                .addZipInputStream(zipInputStream)
                .deploy();

    }



    @Test //开始流程实例
    public void startProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("myProcess_1:1:4");
    }

    @Test //查看当前节点任务列表
    public void queryTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService() //获取任务服务
                .createTaskQuery() //创建任务查询
                .list(); //获取任务列表

        for (Task task : tasks) {
            System.out.println("task name:" + task.getName());
        }

        //
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskDefinitionKey("dept") //根据任务节点id也就是key获取
                .taskAssignee("张学友")
                .list();
        for (Task task : taskList) {
            System.out.println("task : " + task.getName());
        }


    }

    @Test //部门经理提交任务
    public void complete() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("2504");

    }



    @Test //总经理审批 流程结束
    public  void  end(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("5002");

    }


}
