package org.ksea.activiti.service;

import org.ksea.activiti.model.LeaveBill;

import java.util.List;

public interface LeaveBillService {
    List<LeaveBill> listLeaveBillByEmployeeId(Integer eid);
}
