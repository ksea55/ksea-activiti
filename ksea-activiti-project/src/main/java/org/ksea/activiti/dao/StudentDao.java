package org.ksea.activiti.dao;

import org.ksea.activiti.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface StudentDao {

    void add(Student stu);
}
