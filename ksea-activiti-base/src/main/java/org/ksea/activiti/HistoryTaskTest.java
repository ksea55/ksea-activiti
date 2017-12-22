package org.ksea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;

import java.util.List;

/**
 * 历史任务的查看
 */
public class HistoryTaskTest {

    @Test
    public void historyTaskTest1() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**
         * 注意：该方法是查看了所有历史任务，
         * act_hi_taskinst 的一个特殊性，它既包含了已经执行完毕的任务，同时也包含了正在执行的任务，也就是任务还没结束
         * 如何判定一个任务是否执行完毕，可以根据END_TIME_字段，如果为null表示正在执行的任务，如果不为null表示已经执行完毕
         * 或者delete_reason字段值不等于null 有值 completed 表示已经执行完毕
         *
         *
         */
        List<HistoricTaskInstance> historyTasks = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .list(); //改方法返回的是所有任务，其中包含已经执行与未执行的任务
        System.out.println(historyTasks.size());

    }

    @Test //查看已经执行了的任务，也就是真正意义上的历史任务
    public void historyTask2() {

        //第一种方式

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .finished() //表示已经执行完成的任务
                .list();
        System.out.println(list.size());


        //第二种方式

        List<HistoricTaskInstance> list1 = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskDeleteReason("completed") //根据字段delete_reason字段进行过滤
                .list();
        System.out.println(list1.size());



    }
}
