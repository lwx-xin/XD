package priv.xin.xd.core.entity;

import java.util.Date;

public class FileType {
    private String fileSuffix;

    private String fileType;

    private String fileTypeDelFlag;

    private Date fileTypeCreateTime;

    private String fileTypeCreateUser;

    private Date fileTypeModifyTime;

    private String fileTypeModifyUser;

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileTypeDelFlag() {
        return fileTypeDelFlag;
    }

    public void setFileTypeDelFlag(String fileTypeDelFlag) {
        this.fileTypeDelFlag = fileTypeDelFlag == null ? null : fileTypeDelFlag.trim();
    }

    public Date getFileTypeCreateTime() {
        return fileTypeCreateTime;
    }

    public void setFileTypeCreateTime(Date fileTypeCreateTime) {
        this.fileTypeCreateTime = fileTypeCreateTime;
    }

    public String getFileTypeCreateUser() {
        return fileTypeCreateUser;
    }

    public void setFileTypeCreateUser(String fileTypeCreateUser) {
        this.fileTypeCreateUser = fileTypeCreateUser == null ? null : fileTypeCreateUser.trim();
    }

    public Date getFileTypeModifyTime() {
        return fileTypeModifyTime;
    }

    public void setFileTypeModifyTime(Date fileTypeModifyTime) {
        this.fileTypeModifyTime = fileTypeModifyTime;
    }

    public String getFileTypeModifyUser() {
        return fileTypeModifyUser;
    }

    public void setFileTypeModifyUser(String fileTypeModifyUser) {
        this.fileTypeModifyUser = fileTypeModifyUser == null ? null : fileTypeModifyUser.trim();
    }
}