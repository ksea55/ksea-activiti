package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.LeaveBillDao;
import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.service.LeaveBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {

    @Resource
    private LeaveBillDao leaveBillDao;

    @Override
    public List<LeaveBill> listLeaveBillByEmployeeId(Integer eid) {
        return leaveBillDao.listLeaveBillByEmployeeId(eid);
    }
}
