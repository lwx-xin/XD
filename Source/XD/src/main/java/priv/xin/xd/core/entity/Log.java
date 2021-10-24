package priv.xin.xd.core.entity;

import java.util.Date;

public class Log {
    private String logId;

    private Date logHappenTime;

    private String logUser;

    private String logContent;

    private String logFile;

    private String logReqHead;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public Date getLogHappenTime() {
        return logHappenTime;
    }

    public void setLogHappenTime(Date logHappenTime) {
        this.logHappenTime = logHappenTime;
    }

    public String getLogUser() {
        return logUser;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser == null ? null : logUser.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public String getLogReqHead() {
        return logReqHead;
    }

    public void setLogReqHead(String logReqHead) {
        this.logReqHead = logReqHead == null ? null : logReqHead.trim();
    }
}