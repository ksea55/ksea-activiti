package org.ksea.activiti.service;

import org.ksea.activiti.model.Student;

import java.util.List;

public interface StudentService {
    void  saveStudent(Student student);
    void  save(Student student);
    List<Student> list();
}
