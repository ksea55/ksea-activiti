package org.ksea.activiti.service;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.Approve;

import java.util.List;

public interface ApproveService {
    void save(Approve approve);
    List<Approve> listApproveByLeaveBillId(Integer lbid);
}
