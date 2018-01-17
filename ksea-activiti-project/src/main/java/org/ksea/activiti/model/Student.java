package org.ksea.activiti.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    private String id;
    private String sid;
    private String name;
    private String parent;
    @JsonProperty(value = "ischildren")
    private boolean ischildren;


    public Student() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isIschildren() {
        return ischildren;
    }

    public void setIschildren(boolean ischildren) {
        this.ischildren = ischildren;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", children='" + ischildren + '\'' +
                '}';
    }
}
