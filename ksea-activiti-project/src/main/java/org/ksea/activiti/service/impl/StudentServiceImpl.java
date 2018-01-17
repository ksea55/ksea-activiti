package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.StudentDao;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.ksea.activiti.vo.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;


    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void save(Student student) {
        studentDao.add(student);

    }

    public List<Student> list() {
        return studentDao.list();
    }

    @Override
    public List<Ztree> findAllNodes() {
        return this.studentDao.findAllNodes();
    }

    @Override
    public Student getStudentById(String id) {
        return this.studentDao.getStudentById(id);
    }

    @Override
    public List<Ztree> findAllFirstNodes(String parent) {
        return this.studentDao.findAllFirstNodes(parent);
    }

    @Override
    public List<Ztree> findAllChildrenByParentNode(String parent) {
        return this.studentDao.findAllChildrenByParentNode(parent);
    }
}
