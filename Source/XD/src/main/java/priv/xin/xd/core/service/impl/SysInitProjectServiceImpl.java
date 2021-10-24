package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.properties.DataProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.dao.*;
import priv.xin.xd.core.entity.*;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.service.ISysInitProjectService;
import priv.xin.xd.core.entity.ex.FileTypeEx;
import priv.xin.xd.core.entity.ex.UserEx;
import priv.xin.xd.expansionService.redis.service.FileTypeRedis;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ：lu
 * @date ：2021/9/24 10:32
 */
@Service
public class SysInitProjectServiceImpl implements ISysInitProjectService {

    @Resource
    private FileTypeRedis fileTypeRedis;

    @Resource
    private DataProperties dataProperties;
    @Resource
    private CodeMapper codeMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ISysFolderService sysFolderService;
    @Resource
    private UserPositionMapper userPositionMapper;
    @Resource
    private PositionMapper positionMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private AuthMapper authMapper;
    @Resource
    private AuthPositionMapper authPositionMapper;
    @Resource
    private UrlMapper urlMapper;
    @Resource
    private FileTypeMapper fileTypeMapper;

    @Override
    public void addCodeDB() {
        // 清除记录
        codeMapper.deleteAll();

        EnumSet<CodeEnum> codes = EnumSet.allOf(CodeEnum.class);
        if (codes == null) {
            return;
        }

        //管理员账号id
        String adminAccountId = dataProperties.getAdminAccountId();
        int order = 0;
        String groupBefore = "";

        A:
        for (CodeEnum code : codes) {

            String group = code.getGroup();
            String name = code.getName();
            String value = code.getValue();

            if (StrUtil.isEmpty(groupBefore) || !groupBefore.equals(group)) {
                order = 0;
            } else {
                order++;
            }
            groupBefore = group;

            Code c = new Code();
            c.setCodeId(StrUtil.getUUID());
            c.setCodeGroup(group);
            c.setCodeName(name);
            c.setCodeValue(value);
            c.setCodeOrder(String.valueOf(order));
            c.setCodeCreateUser(adminAccountId);
            c.setCodeModifyUser(adminAccountId);

            if (codeMapper.insert(c) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break A;
            }
        }
    }

    @Override
    public void addAdminUser() throws Exception {
        String accountId = dataProperties.getAdminAccountId();

        if (userMapper.selectByPrimaryKey(accountId) == null) {
            Result result = sysFolderService.addUserDefaultFolder(accountId);
            if (!result.getStatus()) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                LoggerUtil.error(JsonUtil.toJson(result.getMessages()), getClass());
                return;
            }

            UserEx adminUser = new UserEx();
            adminUser.setUserId(accountId);
            adminUser.setUserNumber(dataProperties.getAdminAccountNumber());
            adminUser.setUserPwd(PasswordUtil.encrypt(dataProperties.getAdminAccountNumber(), dataProperties.getAdminAccountPassword()));
            adminUser.setUserName(dataProperties.getAdminAccountName());
            adminUser.setUserPlatform(CodeEnum.PLATFORM_ALL.getValue());
            adminUser.setUserDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            adminUser.setUserCreateUser(accountId);
            adminUser.setUserModifyUser(accountId);
            if (userMapper.insert(adminUser) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public void addAdminDepartment() {
        String adminAccountId = dataProperties.getAdminAccountId();
        String adminDepartmentId = dataProperties.getAdminDepartmentId();
        String adminDepartmentName = dataProperties.getAdminDepartmentName();

        if (departmentMapper.selectByPrimaryKey(adminDepartmentId) == null) {
            Department adminDepartment = new Department();
            adminDepartment.setDepartmentId(adminDepartmentId);
            adminDepartment.setDepartmentName(adminDepartmentName);
            adminDepartment.setDepartmentDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            adminDepartment.setDepartmentCreateUser(adminAccountId);
            adminDepartment.setDepartmentModifyUser(adminAccountId);
            if (departmentMapper.insert(adminDepartment) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

    }

    @Override
    public void addAdminPosition() {
        String adminAccountId = dataProperties.getAdminAccountId();
        String adminPositionId = dataProperties.getAdminPositionId();
        String adminPositionName = dataProperties.getAdminPositionName();
        String adminPositionDepartmentId = dataProperties.getAdminPositionDepartmentId();

        if (positionMapper.selectByPrimaryKey(adminPositionId) == null) {
            Position adminPosition = new Position();
            adminPosition.setPositionId(adminPositionId);
            adminPosition.setPositionName(adminPositionName);
            adminPosition.setPositionDepartmentId(adminPositionDepartmentId);
            adminPosition.setPositionDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            adminPosition.setPositionCreateUser(adminAccountId);
            adminPosition.setPositionModifyUser(adminAccountId);
            if (positionMapper.insert(adminPosition) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public void addAdminUserPosition() {
        String adminAccountId = dataProperties.getAdminAccountId();
        String adminPositionId = dataProperties.getAdminPositionId();

        UserPosition adminUserPositionQuery = new UserPosition();
        adminUserPositionQuery.setUserId(adminAccountId);
        adminUserPositionQuery.setPositionId(adminPositionId);
        if (ListUtil.isEmpty(userPositionMapper.queryAll(adminUserPositionQuery))) {
            UserPosition adminUserPosition = new UserPosition();
            adminUserPosition.setUserPositionId(StrUtil.getUUID());
            adminUserPosition.setUserId(adminAccountId);
            adminUserPosition.setPositionId(adminPositionId);
            adminUserPosition.setUserPositionDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            adminUserPosition.setUserPositionCreateUser(adminAccountId);
            adminUserPosition.setUserPositionModifyUser(adminAccountId);
            if (userPositionMapper.insert(adminUserPosition) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public void addAdminAuth() {
        String adminAccountId = dataProperties.getAdminAccountId();
        String adminAuthId = dataProperties.getAdminAuthId();
        String adminAuthName = dataProperties.getAdminAuthName();

        if (authMapper.selectByPrimaryKey(adminAuthId) == null) {
            Auth adminAuth = new Auth();
            adminAuth.setAuthId(adminAuthId);
            adminAuth.setAuthName(adminAuthName);
            adminAuth.setAuthDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            adminAuth.setAuthCreateUser(adminAccountId);
            adminAuth.setAuthModifyUser(adminAccountId);
            if (authMapper.insert(adminAuth) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public void addAdminAuthPosition() {
        String adminAccountId = dataProperties.getAdminAccountId();
        String adminAuthId = dataProperties.getAdminAuthId();
        String adminPositionId = dataProperties.getAdminPositionId();

        AuthPosition authPositionQuery = new AuthPosition();
        authPositionQuery.setAuthId(adminAuthId);
        authPositionQuery.setPositionId(adminPositionId);
        if (ListUtil.isEmpty(authPositionMapper.queryAll(authPositionQuery))) {
            AuthPosition authPosition = new AuthPosition();
            authPosition.setAuthPositionId(StrUtil.getUUID());
            authPosition.setAuthId(adminAuthId);
            authPosition.setPositionId(adminPositionId);
            authPosition.setAuthPositionDelFlag(CodeEnum.DEL_FLAG_1.getValue());
            authPosition.setAuthPositionCreateUser(adminAccountId);
            authPosition.setAuthPositionModifyUser(adminAccountId);
            if (authPositionMapper.insert(authPosition) != 1) {
                // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public void addUser() throws Exception {
        addAdminUser();
        addAdminDepartment();
        addAdminPosition();
        addAdminUserPosition();
        addAdminAuth();
        addAdminAuthPosition();
    }

    @Override
    public void addRequestUrl() {
        String adminAccountId = dataProperties.getAdminAccountId();

        Set<Url> allRequestUrl = ClassUtil.getAllRequestUrl("priv.xin.xd");
        if (ListUtil.isNotEmpty(allRequestUrl)) {
            List<Url> allUrlList = urlMapper.queryAll(null);
            Map<String, Url> allUrlMap = new HashMap<>();
            if (ListUtil.isNotEmpty(allUrlList)) {
                for (Url url : allUrlList) {
                    String urlPath = url.getUrlPath();
                    String urlType = url.getUrlType();
                    allUrlMap.put(urlPath + "_" + urlType, url);
                }
            }

            A:
            for (Url url : allRequestUrl) {
                String urlPath = url.getUrlPath();
                String urlType = url.getUrlType();
                if (allUrlMap.get(urlPath + "_" + urlType) == null) {

                    Url urlInsert = new Url();
                    urlInsert.setUrlId(StrUtil.getUUID());
                    urlInsert.setUrlPath(urlPath);
                    urlInsert.setUrlType(urlType);
                    urlInsert.setUrlPlatform(CodeEnum.PLATFORM_ALL.getValue());
                    urlInsert.setUrlDelFlag(CodeEnum.DEL_FLAG_1.getValue());
                    urlInsert.setUrlCreateUser(adminAccountId);
                    urlInsert.setUrlModifyUser(adminAccountId);
                    if (urlMapper.insert(urlInsert) != 1) {
                        // 回滚数据
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        break A;
                    }
                }
            }
        }
    }

    @Override
    public void addRedis_fileType() {
        List<FileTypeEx> allFileTypeEx = fileTypeMapper.queryAll(null);
        if (ListUtil.isNotEmpty(allFileTypeEx)) {
            List<FileType> allFileType = JsonUtil.toList(allFileTypeEx, FileType.class);
            fileTypeRedis.initFileType(allFileType);
        }
    }

}
