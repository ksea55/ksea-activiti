package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.LeaveBillDao;
import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.service.LeaveBillService;
import org.ksea.activiti.vo.LeaveBillVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {

    @Resource
    private LeaveBillDao leaveBillDao;

    @Override
    public List<LeaveBillVo> listLeaveBillByEmployeeId(Integer eid) {
        return leaveBillDao.listLeaveBillByEmployeeId(eid);
    }

    @Override
    public void save(LeaveBill leaveBill) {
        this.leaveBillDao.save(leaveBill);
    }

    @Override
    public LeaveBillVo getLeaveBillById(Integer id) {
        return this.leaveBillDao.getLeaveBillById(id);
    }

    @Override
    public void modifyLeaveBillById(LeaveBill leaveBill) {
        this.leaveBillDao.modifyLeaveBillById(leaveBill);
    }
}
