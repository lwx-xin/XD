package priv.xin.xd.core.service;

/**
 * @author ：lu
 * @date ：2021/9/24 10:32
 */
public interface ISysInitProjectService {

    void addCodeDB();

    void addAdminUser() throws Exception;

    void addAdminDepartment();

    void addAdminPosition();

    void addAdminUserPosition();

    void addAdminAuth();

    void addAdminAuthPosition();

    void addUser() throws Exception;

    void addRequestUrl();

    void addRedis_fileType();
}
