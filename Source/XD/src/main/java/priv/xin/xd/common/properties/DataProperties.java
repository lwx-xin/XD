package priv.xin.xd.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:data.properties"}, encoding = "utf-8")
@Configuration
public class DataProperties {

    /**
     * 管理员账号id
     */
    @Value("${admin.account.id}")
    private String adminAccountId;
    /**
     * 管理员账号
     */
    @Value("${admin.account.number}")
    private String adminAccountNumber;
    /**
     * 管理员账号密码
     */
    @Value("${admin.account.password}")
    private String adminAccountPassword;
    /**
     * 管理员账号昵称
     */
    @Value("${admin.account.name}")
    private String adminAccountName;

    @Value("${admin.department.id}")
    private String adminDepartmentId;
    @Value("${admin.department.name}")
    private String adminDepartmentName;

    @Value("${admin.position.id}")
    private String adminPositionId;
    @Value("${admin.position.name}")
    private String adminPositionName;
    @Value("${admin.position.department.id}")
    private String adminPositionDepartmentId;

    @Value("${admin.auth.id}")
    private String adminAuthId;
    @Value("${admin.auth.name}")
    private String adminAuthName;

    public String getAdminAccountId() {
        return adminAccountId;
    }

    public void setAdminAccountId(String adminAccountId) {
        this.adminAccountId = adminAccountId;
    }

    public String getAdminAccountNumber() {
        return adminAccountNumber;
    }

    public void setAdminAccountNumber(String adminAccountNumber) {
        this.adminAccountNumber = adminAccountNumber;
    }

    public String getAdminAccountPassword() {
        return adminAccountPassword;
    }

    public void setAdminAccountPassword(String adminAccountPassword) {
        this.adminAccountPassword = adminAccountPassword;
    }

    public String getAdminAccountName() {
        return adminAccountName;
    }

    public void setAdminAccountName(String adminAccountName) {
        this.adminAccountName = adminAccountName;
    }

    public String getAdminDepartmentId() {
        return adminDepartmentId;
    }

    public void setAdminDepartmentId(String adminDepartmentId) {
        this.adminDepartmentId = adminDepartmentId;
    }

    public String getAdminDepartmentName() {
        return adminDepartmentName;
    }

    public void setAdminDepartmentName(String adminDepartmentName) {
        this.adminDepartmentName = adminDepartmentName;
    }

    public String getAdminPositionId() {
        return adminPositionId;
    }

    public void setAdminPositionId(String adminPositionId) {
        this.adminPositionId = adminPositionId;
    }

    public String getAdminPositionName() {
        return adminPositionName;
    }

    public void setAdminPositionName(String adminPositionName) {
        this.adminPositionName = adminPositionName;
    }

    public String getAdminPositionDepartmentId() {
        return adminPositionDepartmentId;
    }

    public void setAdminPositionDepartmentId(String adminPositionDepartmentId) {
        this.adminPositionDepartmentId = adminPositionDepartmentId;
    }

    public String getAdminAuthId() {
        return adminAuthId;
    }

    public void setAdminAuthId(String adminAuthId) {
        this.adminAuthId = adminAuthId;
    }

    public String getAdminAuthName() {
        return adminAuthName;
    }

    public void setAdminAuthName(String adminAuthName) {
        this.adminAuthName = adminAuthName;
    }
}
