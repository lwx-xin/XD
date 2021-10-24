package priv.xin.xd.core.entity;

import java.util.Date;

public class File {
    private String fileId;

    private String fileName;

    private String fileSize;

    private String fileSuffix;

    private String fileFolder;

    private String fileOwner;

    private String filePath;

    private String fileDelFlag;

    private Date fileCreateTime;

    private String fileCreateUser;

    private Date fileModifyTime;

    private String fileModifyUser;

    private String fileDetailInfo;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize == null ? null : fileSize.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder == null ? null : fileFolder.trim();
    }

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner == null ? null : fileOwner.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileDelFlag() {
        return fileDelFlag;
    }

    public void setFileDelFlag(String fileDelFlag) {
        this.fileDelFlag = fileDelFlag == null ? null : fileDelFlag.trim();
    }

    public Date getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(Date fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    public String getFileCreateUser() {
        return fileCreateUser;
    }

    public void setFileCreateUser(String fileCreateUser) {
        this.fileCreateUser = fileCreateUser == null ? null : fileCreateUser.trim();
    }

    public Date getFileModifyTime() {
        return fileModifyTime;
    }

    public void setFileModifyTime(Date fileModifyTime) {
        this.fileModifyTime = fileModifyTime;
    }

    public String getFileModifyUser() {
        return fileModifyUser;
    }

    public void setFileModifyUser(String fileModifyUser) {
        this.fileModifyUser = fileModifyUser == null ? null : fileModifyUser.trim();
    }

    public String getFileDetailInfo() {
        return fileDetailInfo;
    }

    public void setFileDetailInfo(String fileDetailInfo) {
        this.fileDetailInfo = fileDetailInfo == null ? null : fileDetailInfo.trim();
    }
}