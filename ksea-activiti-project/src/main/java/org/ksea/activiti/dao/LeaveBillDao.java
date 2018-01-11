package org.ksea.activiti.dao;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.vo.LeaveBillVo;

import java.util.List;

public interface LeaveBillDao {

    List<LeaveBillVo> listLeaveBillByEmployeeId(@Param("eid")Integer eid);
    void  save(LeaveBill leaveBill);
    LeaveBillVo getLeaveBillById(@Param("id")Integer id);
    void  modifyLeaveBillById(LeaveBill leaveBill);

}
