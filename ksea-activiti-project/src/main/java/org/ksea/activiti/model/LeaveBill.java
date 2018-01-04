package org.ksea.activiti.model;

import java.util.Date;
import java.util.Set;

/**
 * 请假单
 */
public class LeaveBill {
    private Integer id;//主键ID
    private Integer days;// 请假天数
    private String content;// 请假内容
    private Date leaveDate = new Date();// 请假时间
    private String remark;// 备注
    private Integer employeeId;// 请假人

    private Integer state = 0;// 请假单状态 0初始录入,1.开始审批,2为审批完成

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LeaveBill() {
    }
}
