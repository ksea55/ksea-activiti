package org.ksea.activiti.vo;

import org.ksea.activiti.model.LeaveBill;

public class LeaveBillVo extends LeaveBill {


    private  String applicator; //请假人

    public LeaveBillVo() {
    }

    public String getApplicator() {
        return applicator;
    }

    public void setApplicator(String applicator) {
        this.applicator = applicator;
    }


}
