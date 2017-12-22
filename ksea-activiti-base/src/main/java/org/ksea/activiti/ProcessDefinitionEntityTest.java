package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.text.MessageFormat;
import java.util.List;

/**
 * 该部分主要说明，查看任务在某个节点的流程图并标注对应节点
 */
public class ProcessDefinitionEntityTest {


    @Test
    public void processDefinitionEntityTest() {


        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService()

                .getProcessDefinition("myProcess_1:4:15004");


        /**
         *
         * ActivityImpl是一个对象
         * 一个ActivityImpl对象代表一个正在执行的节点
         *
         */
        List<ActivityImpl> activities = processDefinitionEntity.getActivities();

        for (ActivityImpl activity : activities) {

            System.out.println(activity.getId());  //节点id
            System.out.print(" width:" + activity.getWidth()); //流程图 节点图大小的宽度
            System.out.print(" heigth:" + activity.getHeight()); //流程图 节点图大小的高度
            System.out.print(" X:" + activity.getX());
            System.out.println(" Y:" + activity.getY());


            /**
             * 打印结果:
             *
             *   dept
             width:85 heigth:55 X:150 Y:140
             manager
             width:85 heigth:55 X:155 Y:305
             start
             width:32 heigth:32 X:185 Y:5
             end
             width:32 heigth:32 X:180 Y:445

             *
             *
             */

        }


    }


    /**
     * 获取ProcessDefinitionEntity中 所有的activityImpl的PvmTransition出处，也就是在流程图中的每个箭头的指向
     */
    @Test
    public void activityImplTest() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //根据流程定义id获取到流程定义实体
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("myProcess_1:4:15004");

        //获取到ActivityImpl节点对象
        List<ActivityImpl> activities = processDefinitionEntity.getActivities();

        for (ActivityImpl activity : activities) {

            /**
             * 获取到每个节点的出处，也就是在每个节点上面箭头图标指向
             * 根据箭头的出处，在web中我们就可以动态的实现button的出现，比如通过，不通过
             *
             *
             * */
            List<PvmTransition> pvmTransitions = activity.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                System.out.println(pvmTransition.getId());
            }


        }


    }


    /**
     * 基于上面知识的积累，那么我们就可以根据当前流程id获取当前正在执行的流程节点
     */
    @Test
    public void activeAvtivityTest() {


        //1：首先根据流程定义id获取到ProcessDefinitionEntity

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processEngine.getRepositoryService().getProcessDefinition("myProcess_1:2:10004");


        //2：根据流程实例Id获取到当前正在执行的活动流程节点

        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId("17501")
                .singleResult();

        //获取到当前的活动流程节点Id
        String activityId = processInstance.getActivityId();

        //3:根据流程定义实体 获取到当前的流程节点对象
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);

        //4：根据当前流程节点实体 获取到其 当前流程节点图的大小(宽度，长度)以及坐标(position: x ,y) 根据这些信息我们就可以在web页面中用div来设置其位置，并标注其当前流程节点图

        System.out.print("processInstanceId:" + processInstance.getId());
        System.out.print(" activityId:" + activity.getId());  //节点id
        System.out.print(" width:" + activity.getWidth()); //流程图 节点图大小的宽度
        System.out.print(" heigth:" + activity.getHeight()); //流程图 节点图大小的高度
        System.out.print(" X:" + activity.getX());
        System.out.println(" Y:" + activity.getY());


        // 打印结果:processInstanceId:17501 activityId:manager width:85 heigth:55 X:155 Y:305
    }

}
