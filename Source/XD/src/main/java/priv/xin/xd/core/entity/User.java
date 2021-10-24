package priv.xin.xd.core.entity;

import java.util.Date;

public class User {
    private String userId;

    private String userNumber;

    private String userPwd;

    private String userName;

    private String userHead;

    private String userPlatform;

    private String userQq;

    private String userEmail;

    private String userDelFlag;

    private Date userCreateTime;

    private String userCreateUser;

    private Date userModifyTime;

    private String userModifyUser;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber == null ? null : userNumber.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead == null ? null : userHead.trim();
    }

    public String getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(String userPlatform) {
        this.userPlatform = userPlatform == null ? null : userPlatform.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserDelFlag() {
        return userDelFlag;
    }

    public void setUserDelFlag(String userDelFlag) {
        this.userDelFlag = userDelFlag == null ? null : userDelFlag.trim();
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserCreateUser() {
        return userCreateUser;
    }

    public void setUserCreateUser(String userCreateUser) {
        this.userCreateUser = userCreateUser == null ? null : userCreateUser.trim();
    }

    public Date getUserModifyTime() {
        return userModifyTime;
    }

    public void setUserModifyTime(Date userModifyTime) {
        this.userModifyTime = userModifyTime;
    }

    public String getUserModifyUser() {
        return userModifyUser;
    }

    public void setUserModifyUser(String userModifyUser) {
        this.userModifyUser = userModifyUser == null ? null : userModifyUser.trim();
    }
}