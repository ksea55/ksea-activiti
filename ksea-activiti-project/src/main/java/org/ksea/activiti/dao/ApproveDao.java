package org.ksea.activiti.dao;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.Approve;

import java.util.List;

public interface ApproveDao {
    void save(Approve approve);
    List<Approve> listApproveByLeaveBillId(@Param("lbid")Integer lbid);
}
