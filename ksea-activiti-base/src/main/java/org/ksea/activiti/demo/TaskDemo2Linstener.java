package org.ksea.activiti.demo;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TaskDemo2Linstener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {

        //此处获取变量中的数据
        Object assignee = delegateTask.getVariable("dept_");
        delegateTask.setAssignee(String.valueOf(assignee));

        // delegateTask.setAssignee("k.sea部门经理");

    }
}
