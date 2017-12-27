package org.ksea.activiti.candidateuser;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 在画流程图的时候，有一个
 * assignee 流程任务执行人，它只是一个人
 * 当有这样的一个需求，一个任务过来之后，多个人都可以看到这个任务，并且在这之中，只要其中一个人审批了任务就提交了
 * 此刻就要用到 candidateUser 候选人用户，他是多个
 * 也可以用到candidateGroup
 */
public class CandidateUserTest {

    @Test
    public void deploymentProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("activiti/bmp/candidateuser/candidateuser.bpmn")
                .deploy();
    }

    @Test
    public void startProncessInstance() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("myProcess_1:2:7504");
    }


    @Test//发起论文
    public void startTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("10004");

    }

    /**
     * 当发起人提交论文之后，节点就进行到论文审批阶段
     * 在这个阶段，审批有多个参与者，其中只要一个人审批通过，该节点的任务就呗通过
     * 此刻
     * act_ru_identitylink
     * 用于存储临时数据，当这个流程实例结束之后改数据就被清空
     * act_hi_identitylink
     * 同样保存了当前数据，但是是永久保存
     * 从表结构的数据看，是可以通过taskId来查询到 当前的参与者 也就是审批人组，也可以通过流程实例id查询到
     * <p>
     * <p>
     * <userTask activiti:candidateUsers="张山,李四,小五,小六" activiti:exclusive="true" id="shenpi" name="论文审批"></userTask>
     */
    @Test
    public void shenpiTask() {
        //通过task查询当前任务的候选人组

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //根据任务id查询到当前任务的候选人组
        List<IdentityLink> identityLinks = processEngine.getTaskService().getIdentityLinksForTask("15002");

        for (IdentityLink identityLink : identityLinks) {
            System.out.println(identityLink.getUserId());//获取到当前的候选人
        }

    }

    @Test
    public void shenpiTask_() {
        //根据流程实例Id查询候选人组
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<IdentityLink> identityLinks = processEngine.getRuntimeService()
                .getIdentityLinksForProcessInstance("10001");
        for (IdentityLink identityLink : identityLinks) {
            System.out.println(identityLink.getUserId());
        }

    }

    //正常情况下，我们是根据登录人查询任务，那么如何通过候选人查询到任务呢
    @Test
    public void queryTaskByCandidateUser() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskCandidateUser("小五")
                .list();

        for (Task task : tasks) {
            System.out.println(task.getId() + "," + task.getName());
        }

    }


    //候选人领取任务
    @Test
    public void claimTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().claim("15002", "小五"); //根据候选人与任务id，其实就是将任务为15002的assignee设置成了小五

        /**
         * 当一个任务被认领之后，该任务就属于被认领人的任务
         * 当另外的人再来领取该任务则
         * org.activiti.engine.ActivitiTaskAlreadyClaimedException: Task '15002' is already claimed by someone else.
         * 意思就是说 任务ID为15002的已经被认领了
         *
         *
         *
         *
         */

    }

    @Test
    public void submitTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete("15002");

    }


    /**
     * 此处的候选人，我们是写死的，在实际开发中人员是未知的也就是动态添加的，那么我们可以根据taskLinstener去设置
     * 也可以通过group组来设置，其中在activiti中有用户表与用户组表，这个在实际开发中我们都有自己的一套数据库表，需要将2这进行同步
     * 它添加此处添加 他删除这边也同样删除
     */


    //通过组的方式设置其候选人
    @Test
    public void setCandidateGroup() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //创建组
        Group group = new GroupEntity();
        group.setId("1");
        group.setName("论文审批组");
        group.setType("论文");


        //创建用户
        User user1 = new UserEntity();
        user1.setId("1");
        user1.setFirstName("jacky");

        User user2 = new UserEntity();
        user2.setId("2");
        user2.setFirstName("mexican");


        //保存组
        processEngine.getIdentityService().saveGroup(group);

        //保存用户
        processEngine.getIdentityService().saveUser(user1);
        processEngine.getIdentityService().saveUser(user2);

        /**
         * 注意这里是先保存上面的，在保存下面的 否则异常
         *
         */
        //保存用户与用户组之间的关系
        processEngine.getIdentityService().createMembership(user1.getId(), group.getId());
        processEngine.getIdentityService().createMembership(user2.getId(), group.getId());


        /**
         * 运行结果
         * group的数据就保存到：act_id_group表中
         * user的数据就保存到:act_id_user表中
         * user与group之间的关系则保存到:act_id_membership表中
         *
         */


        /**
         *   processEngine.getTaskService().addCandidateGroup("taskId", "groupId");
         *   processEngine.getTaskService().addCandidateUser("taskId", "userId");
         *   此处就可以通过任务监听动态设置候选人或者候选者
         *   public class TaskDemo2Linstener implements TaskListener {
         *      @Override
         *      public void notify(DelegateTask delegateTask) {
         *          delegateTask.addCandidateGroup("groupId");
         *          delegateTask.addCandidateUser("userId");
         *      }
         *  }
         */

    }


}
