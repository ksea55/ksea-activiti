package org.ksea.activiti.dao;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.LeaveBill;

import java.util.List;

public interface LeaveBillDao {

    List<LeaveBill> listLeaveBillByEmployeeId(@Param("eid")Integer eid);

}
