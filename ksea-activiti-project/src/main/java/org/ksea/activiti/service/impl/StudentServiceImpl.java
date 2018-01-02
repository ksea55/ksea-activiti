package org.ksea.activiti.service.impl;

import org.ksea.activiti.dao.StudentDao;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    public void saveStudent(Student student) {
        studentDao.add(student);
    }
}
