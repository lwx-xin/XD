package priv.xin.xd.core.entity;

import java.util.Date;

public class Config {
    private String configId;

    private String configKey;

    private String configValue;

    private String configRemark;

    private String configUser;

    private String configDelFlag;

    private Date configCreateTime;

    private String configCreateUser;

    private Date configModifyTime;

    private String configModifyUser;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId == null ? null : configId.trim();
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getConfigRemark() {
        return configRemark;
    }

    public void setConfigRemark(String configRemark) {
        this.configRemark = configRemark == null ? null : configRemark.trim();
    }

    public String getConfigUser() {
        return configUser;
    }

    public void setConfigUser(String configUser) {
        this.configUser = configUser == null ? null : configUser.trim();
    }

    public String getConfigDelFlag() {
        return configDelFlag;
    }

    public void setConfigDelFlag(String configDelFlag) {
        this.configDelFlag = configDelFlag == null ? null : configDelFlag.trim();
    }

    public Date getConfigCreateTime() {
        return configCreateTime;
    }

    public void setConfigCreateTime(Date configCreateTime) {
        this.configCreateTime = configCreateTime;
    }

    public String getConfigCreateUser() {
        return configCreateUser;
    }

    public void setConfigCreateUser(String configCreateUser) {
        this.configCreateUser = configCreateUser == null ? null : configCreateUser.trim();
    }

    public Date getConfigModifyTime() {
        return configModifyTime;
    }

    public void setConfigModifyTime(Date configModifyTime) {
        this.configModifyTime = configModifyTime;
    }

    public String getConfigModifyUser() {
        return configModifyUser;
    }

    public void setConfigModifyUser(String configModifyUser) {
        this.configModifyUser = configModifyUser == null ? null : configModifyUser.trim();
    }
}