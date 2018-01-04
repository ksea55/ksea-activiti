package org.ksea.activiti.model;

import java.io.Serializable;

public class Approve implements Serializable {
    private Integer aid;
    private String name;//审批人的名字
    private String state;//审批是否通过
    private String comment;//标注

    private Integer leaveBillId; //审批请假条

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLeaveBillId() {
        return leaveBillId;
    }

    public void setLeaveBillId(Integer leaveBillId) {
        this.leaveBillId = leaveBillId;
    }

    public Approve() {
    }
}
