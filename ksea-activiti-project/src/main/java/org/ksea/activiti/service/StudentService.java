package org.ksea.activiti.service;

import org.ksea.activiti.model.Student;
import org.ksea.activiti.vo.Ztree;

import java.util.List;

public interface StudentService {

    void  save(Student student);
    List<Student> list();
    List<Ztree> findAllNodes();
    Student getStudentById(String id);
    List<Ztree> findAllFirstNodes(String parent);
    List<Ztree> findAllChildrenByParentNode(String parent);
}
