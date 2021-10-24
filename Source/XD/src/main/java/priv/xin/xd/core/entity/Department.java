package priv.xin.xd.core.entity;

import java.util.Date;

public class Department {
    private String departmentId;

    private String departmentName;

    private String departmentDelFlag;

    private Date departmentCreateTime;

    private String departmentCreateUser;

    private Date departmentModifyTime;

    private String departmentModifyUser;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDelFlag() {
        return departmentDelFlag;
    }

    public void setDepartmentDelFlag(String departmentDelFlag) {
        this.departmentDelFlag = departmentDelFlag;
    }

    public Date getDepartmentCreateTime() {
        return departmentCreateTime;
    }

    public void setDepartmentCreateTime(Date departmentCreateTime) {
        this.departmentCreateTime = departmentCreateTime;
    }

    public String getDepartmentCreateUser() {
        return departmentCreateUser;
    }

    public void setDepartmentCreateUser(String departmentCreateUser) {
        this.departmentCreateUser = departmentCreateUser;
    }

    public Date getDepartmentModifyTime() {
        return departmentModifyTime;
    }

    public void setDepartmentModifyTime(Date departmentModifyTime) {
        this.departmentModifyTime = departmentModifyTime;
    }

    public String getDepartmentModifyUser() {
        return departmentModifyUser;
    }

    public void setDepartmentModifyUser(String departmentModifyUser) {
        this.departmentModifyUser = departmentModifyUser;
    }
}