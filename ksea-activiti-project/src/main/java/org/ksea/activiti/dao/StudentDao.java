package org.ksea.activiti.dao;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentDao {

    void add(Student stu);

    List<Student> list();

    Student getStudentById(@Param("id")String id);

    List<Student> getChildrensByParent(@Param("parent")String parent);

}
