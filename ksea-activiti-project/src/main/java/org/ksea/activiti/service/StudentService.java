package org.ksea.activiti.service;

import org.ksea.activiti.model.Student;

import java.util.List;

public interface StudentService {

    void  save(Student student);
    List<Student> list();
    Student getStudentById(String id);
}
