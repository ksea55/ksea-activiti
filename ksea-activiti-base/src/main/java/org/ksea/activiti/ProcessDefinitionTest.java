package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义的几种方式
 */
public class ProcessDefinitionTest {


    @Test
    public void test1() {
        //第一种通过根据节点资源部署
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("activiti/bmp/qingjia.bpmn")
                .addClasspathResource("activiti/bmp/qingjia.png")
                .deploy();
    }

    @Test
    public void test2() {
        //第二种通过stream
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("activiti/bmp/qingjia.bpmn");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addInputStream("qingjia.bpmn", inputStream)
                .deploy();
    }


    @Test
    public void test3() {
        //第三种也是通过stream,将文件进行压缩
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("activiti/bmp/qingjia.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        processEngine.getRepositoryService()
                .createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    @Test
    public void test4() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                //.deleteDeployment("1")  //删除流程部署，但是不会删除如 任务，变量等相关的
                .deleteDeployment("1", true); //删除与部署所有相关联的东西


    }

}
