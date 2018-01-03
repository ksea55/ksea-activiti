package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.StudentDao;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
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

    public void saveStudent(Student student) {
        studentDao.add(student);
        System.out.println(1 / 0);


    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor ={Exception.class})
    public void save(Student student) {
        studentDao.add(student);
        System.out.println(1 / 0);
    }

    public List<Student> list() {
        return studentDao.list();
    }
}
