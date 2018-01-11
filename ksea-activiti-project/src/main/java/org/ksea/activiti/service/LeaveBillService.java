package org.ksea.activiti.service;

import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.vo.LeaveBillVo;

import java.util.List;

public interface LeaveBillService {
    List<LeaveBillVo> listLeaveBillByEmployeeId(Integer eid);
    void  save(LeaveBill leaveBill);
    LeaveBillVo getLeaveBillById(Integer id);
    void  modifyLeaveBillById(LeaveBill leaveBill);
}
