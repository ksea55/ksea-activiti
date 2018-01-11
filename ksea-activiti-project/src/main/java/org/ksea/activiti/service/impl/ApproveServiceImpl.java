package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.ApproveDao;
import org.ksea.activiti.model.Approve;
import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.service.ApproveService;
import org.ksea.activiti.service.LeaveBillService;
import org.ksea.activiti.vo.LeaveBillVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ApproveServiceImpl implements ApproveService {

    @Resource
    private ApproveDao approveDao;

    @Resource
    private LeaveBillService leaveBillService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void save(Approve approve) {

        this.approveDao.save(approve);
        LeaveBill leaveBill =this.leaveBillService.getLeaveBillById(approve.getLeaveBillId());
        leaveBill.setState(Integer.parseInt(approve.getState()));

        this.leaveBillService.modifyLeaveBillById(leaveBill);

    }

    @Override
    public List<Approve> listApproveByLeaveBillId(Integer lbid) {
        return this.approveDao.listApproveByLeaveBillId(lbid);
    }
}
