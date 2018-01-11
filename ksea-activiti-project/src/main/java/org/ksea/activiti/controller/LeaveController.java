package org.ksea.activiti.controller;

import org.ksea.activiti.model.Employee;
import org.ksea.activiti.model.LeaveBill;
import org.ksea.activiti.service.LeaveBillService;
import org.ksea.activiti.vo.LeaveBillVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "leave")
@Controller
public class LeaveController {

    @Resource
    private LeaveBillService leaveBillService;

    @RequestMapping(value = "manager", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {

        Employee user = (Employee) request.getSession().getAttribute("user");
        List<LeaveBillVo> leaveBills = leaveBillService.listLeaveBillByEmployeeId(user.getId());
        model.addAttribute("leaveBills", leaveBills);

        return "leaveBill/list";
    }

    @RequestMapping(value = "add/view",method = RequestMethod.GET)
    public String addPage(HttpServletRequest request,Model model){

        LeaveBill leaveBill= new LeaveBill();
        model.addAttribute("lb",leaveBill);

        return "leaveBill/input";
    }
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public String addLeaveBill(@ModelAttribute LeaveBill leaveBill,HttpServletRequest request){

        Employee user = (Employee) request.getSession().getAttribute("user");

        leaveBill.setState(0);
        leaveBill.setEmployeeId(user.getId());
        leaveBill.setLeaveDate(new Date());

        this.leaveBillService.save(leaveBill);

        return "redirect:/leave/manager";
    }
}
