package org.ksea.activiti.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ksea.activiti.model.Student;

import java.util.List;

public class Ztree extends Student {

    private List<Ztree> childrens;

    private boolean open;
    @JsonProperty(value = "isParent")
    private boolean isparent;

    public boolean isOpen() {
        if (isparent)
            this.open = true;
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isIsparent() {
        if (null != childrens && childrens.size() > 0)
            this.isparent = true;
        return isparent;
    }

    public void setIsparent(boolean isparent) {
        this.isparent = isparent;
    }

    public List<Ztree> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Ztree> childrens) {
        this.childrens = childrens;
    }
}
