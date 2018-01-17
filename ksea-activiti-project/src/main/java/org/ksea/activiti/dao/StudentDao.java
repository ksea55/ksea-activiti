package org.ksea.activiti.dao;

import org.apache.ibatis.annotations.Param;
import org.ksea.activiti.model.Student;
import org.ksea.activiti.vo.Ztree;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentDao {

    void add(Student stu);

    List<Student> list();
    List<Ztree> findAllNodes();

    Student getStudentById(@Param("id")String id);

    List<Ztree> findAllFirstNodes(@Param("parent")String parent);
    List<Ztree> findAllChildrenByParentNode(@Param("parent")String parent);

}
