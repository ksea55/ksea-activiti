package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
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


    /**
     * 在activiti中查看流程拥有非常丰富的api，每个api其实都对应其表sql
     * 每个表之间的关联关系，关联的特别的好，这也是activiti的非常优秀之作
     * 每个查询我们可以根据看表结构关系，从而可以推理出其api，相反也一样，
     * 其每个查询的模式大致一样，接下来进行延伸
     * <p>
     * 如：查看部署情况,对应的表结构名称 act_re_deployment
     */
    @Test
    public void test5() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deployments = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime() //根据部署时间排序
                .desc()// 并按降序排序
                .list(); //获取结果集

        for (Deployment deployment : deployments) {
            System.out.println(deployment.getId());
        }

        //---

        Deployment deployment = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .deploymentId("10001") //根据id查询 其它条件也一样
                .singleResult();//返回单条数据
        System.out.println(deployment.getId() + "," + deployment.getDeploymentTime());


    }


    /**
     * 如何查看流程图呢
     * <p>
     * <p>
     * act_ge_bytearray 表存储其中bpmn与png图片,也就是流程xml与流程图,这里我们可以通过关联 deploymentId找到其文件 name与types
     * <p>
     * act_re_deployment 部署信息表
     */
    @Test
    public void test6() throws IOException {
        //第一种方式通过部署信息表id获取取流程图
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getResourceAsStream("10001", "activiti/bmp/qingjia.png");//根据deploymentId以及resourceName名称，其结果返回的是一个inputStream
        byte[] bytes = new byte[10 * 1024];
        OutputStream outputStream = new FileOutputStream("D:\\qingjia.png");
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }

        outputStream.close();
        inputStream.close();

    }


    /**
     * 通过流程定义表 act_re_procdef 来获取流程图
     * 可以定义表中的deploymentyId 就可以关联部署表找到 流程图
     * 并且在流程定义表中本身就有resource_name 资源名称  dgrm_resource_name 流程图名称
     */
    @Test
    public void test7() throws IOException {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
                .getProcessDiagram("myProcess_1:2:10004");
        byte[] bytes = new byte[10 * 1024];
        OutputStream outputStream = new FileOutputStream("D:\\qingjia1.png");
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }

        outputStream.close();
        inputStream.close();

    }


    /**
     * 查看bpmn文件
     */
    @Test
    public void test8() throws IOException {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        InputStream inputStream = processEngine.getRepositoryService()
              .getProcessModel("myProcess_1:2:10004");
        byte[] bytes = new byte[10 * 1024];
        OutputStream outputStream = new FileOutputStream("D:\\qingjia.bpmn");
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }

        outputStream.close();
        inputStream.close();

    }
}
