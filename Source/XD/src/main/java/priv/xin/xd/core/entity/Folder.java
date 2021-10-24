package priv.xin.xd.core.entity;

import java.util.Date;

public class Folder {
    private String folderId;

    private String folderName;

    private String folderParent;

    private String folderPath;

    private String folderOwner;

    private String folderResourceType;

    private String folderDelFlag;

    private Date folderCreateTime;

    private String folderCreateUser;

    private Date folderModifyTime;

    private String folderModifyUser;

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderParent() {
        return folderParent;
    }

    public void setFolderParent(String folderParent) {
        this.folderParent = folderParent;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderOwner() {
        return folderOwner;
    }

    public void setFolderOwner(String folderOwner) {
        this.folderOwner = folderOwner;
    }

    public String getFolderResourceType() {
        return folderResourceType;
    }

    public void setFolderResourceType(String folderResourceType) {
        this.folderResourceType = folderResourceType;
    }

    public String getFolderDelFlag() {
        return folderDelFlag;
    }

    public void setFolderDelFlag(String folderDelFlag) {
        this.folderDelFlag = folderDelFlag;
    }

    public Date getFolderCreateTime() {
        return folderCreateTime;
    }

    public void setFolderCreateTime(Date folderCreateTime) {
        this.folderCreateTime = folderCreateTime;
    }

    public String getFolderCreateUser() {
        return folderCreateUser;
    }

    public void setFolderCreateUser(String folderCreateUser) {
        this.folderCreateUser = folderCreateUser;
    }

    public Date getFolderModifyTime() {
        return folderModifyTime;
    }

    public void setFolderModifyTime(Date folderModifyTime) {
        this.folderModifyTime = folderModifyTime;
    }

    public String getFolderModifyUser() {
        return folderModifyUser;
    }

    public void setFolderModifyUser(String folderModifyUser) {
        this.folderModifyUser = folderModifyUser;
    }
}