package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.FolderPath;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.dao.FolderMapper;
import priv.xin.xd.core.entity.Folder;
import priv.xin.xd.core.entity.FolderOrFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/9/9 19:05
 */
@Service
public class SysFolderServiceImpl implements ISysFolderService {

    @Resource
    private PathProperties pathProperties;

    @Resource
    private FolderMapper folderMapper;

    @Override
    public Result getFolderPath(String folderId) {

        Folder folderInfo = folderMapper.selectByPrimaryKey(folderId);
        if (folderInfo == null) {
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_UNKNOWN);
        }

        String folderPath = folderInfo.getFolderPath();
        String folderOwner = folderInfo.getFolderOwner();

        String fileSaveRealPath = pathProperties.getFileSaveRealPath();

        fileSaveRealPath = fileSaveRealPath + folderPath;

        return new Result(true)
                .data("folderRealPath", fileSaveRealPath)
                .data("folderPath", folderPath);
    }

    @Override
    public Result addUserDefaultFolder(String userId) {

        String fileSaveRealPath = pathProperties.getFileSaveRealPath();

        // 根目录
        String rootFolderPath = "/" + userId;
        String rootFolderName = userId;
        String rootFolderId = StrUtil.getUUID();
        // 系统资源目录
        String systemFolderPath = String.format(FolderPath.SYSTEM_RESOURCE_FOLDER_PATH, userId);
        String systemFolderName = FolderPath.SYSTEM_RESOURCE_FOLDER_NAME;
        String systemFolderId = StrUtil.getUUID();
        // 自定义资源目录
        String customFolderPath = String.format(FolderPath.CUSTOM_RESOURCE_FOLDER_PATH, userId);
        String customFolderName = FolderPath.CUSTOM_RESOURCE_FOLDER_NAME;
        String customFolderId = StrUtil.getUUID();
        // 用户头像目录
        String userHeadFolderPath = String.format(FolderPath.USER_HEAD_FOLDER_PATH, userId);
        String userHeadFolderName = FolderPath.USER_HEAD_FOLDER_NAME;
        String userHeadFolderId = StrUtil.getUUID();
        // 用户日志目录
        String userLogFolderPath = String.format(FolderPath.USER_LOG_FOLDER_PATH, userId);
        String userLogFolderName = FolderPath.USER_LOG_FOLDER_NAME;
        String userLogFolderId = StrUtil.getUUID();

        // 保存根目录信息
        Folder rootFolder = new Folder();
        rootFolder.setFolderId(rootFolderId);
        rootFolder.setFolderName(rootFolderName);
        rootFolder.setFolderParent("");
        rootFolder.setFolderPath(rootFolderPath);
        rootFolder.setFolderOwner(userId);
        rootFolder.setFolderResourceType(CodeEnum.RESOURCE_TYPE_SYSTEM.getValue());
        rootFolder.setFolderDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        rootFolder.setFolderCreateUser(userId);
        rootFolder.setFolderModifyUser(userId);
        if (folderMapper.insert(rootFolder) != 1) {
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 保存系统资源目录信息
        Folder systemFolder = new Folder();
        systemFolder.setFolderId(systemFolderId);
        systemFolder.setFolderName(systemFolderName);
        systemFolder.setFolderParent(rootFolderId);
        systemFolder.setFolderPath(systemFolderPath);
        systemFolder.setFolderOwner(userId);
        systemFolder.setFolderResourceType(CodeEnum.RESOURCE_TYPE_SYSTEM.getValue());
        systemFolder.setFolderDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        systemFolder.setFolderCreateUser(userId);
        systemFolder.setFolderModifyUser(userId);
        if (folderMapper.insert(systemFolder) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 保存自定义资源目录信息
        Folder customFolder = new Folder();
        customFolder.setFolderId(customFolderId);
        customFolder.setFolderName(customFolderName);
        customFolder.setFolderParent(rootFolderId);
        customFolder.setFolderPath(customFolderPath);
        customFolder.setFolderOwner(userId);
        customFolder.setFolderResourceType(CodeEnum.RESOURCE_TYPE_SYSTEM.getValue());
        customFolder.setFolderDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        customFolder.setFolderCreateUser(userId);
        customFolder.setFolderModifyUser(userId);
        if (folderMapper.insert(customFolder) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 保存头像目录信息
        Folder userHeadFolder = new Folder();
        userHeadFolder.setFolderId(userHeadFolderId);
        userHeadFolder.setFolderName(userHeadFolderName);
        userHeadFolder.setFolderParent(systemFolderId);
        userHeadFolder.setFolderPath(userHeadFolderPath);
        userHeadFolder.setFolderOwner(userId);
        userHeadFolder.setFolderResourceType(CodeEnum.RESOURCE_TYPE_SYSTEM.getValue());
        userHeadFolder.setFolderDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        userHeadFolder.setFolderCreateUser(userId);
        userHeadFolder.setFolderModifyUser(userId);
        if (folderMapper.insert(userHeadFolder) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 保存日志目录信息
        Folder userLogFolder = new Folder();
        userLogFolder.setFolderId(userLogFolderId);
        userLogFolder.setFolderName(userLogFolderName);
        userLogFolder.setFolderParent(systemFolderId);
        userLogFolder.setFolderPath(userLogFolderPath);
        userLogFolder.setFolderOwner(userId);
        userLogFolder.setFolderResourceType(CodeEnum.RESOURCE_TYPE_SYSTEM.getValue());
        userLogFolder.setFolderDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        userLogFolder.setFolderCreateUser(userId);
        userLogFolder.setFolderModifyUser(userId);
        if (folderMapper.insert(userLogFolder) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }


        // 创建根目录
        java.io.File folderRoot = new java.io.File(fileSaveRealPath + rootFolderPath);
        if (!folderRoot.exists() && !folderRoot.mkdirs()) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 创建系统资源目录
        java.io.File folderSystem = new java.io.File(fileSaveRealPath + systemFolderPath);
        if (!folderSystem.exists() && !folderSystem.mkdirs()) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 创建自定义资源目录
        java.io.File folderCustom = new java.io.File(fileSaveRealPath + customFolderPath);
        if (!folderCustom.exists() && !folderCustom.mkdirs()) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 创建用户头像文件夹
        java.io.File folderUserHead = new java.io.File(fileSaveRealPath + userHeadFolderPath);
        if (!folderUserHead.exists() && !folderUserHead.mkdirs()) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        // 创建用户头像文件夹
        java.io.File folderUserLog = new java.io.File(fileSaveRealPath + userLogFolderPath);
        if (!folderUserLog.exists() && !folderUserLog.mkdirs()) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_INIT_ERROR);
        }

        Map<String, Object> resultDataMap = new HashMap<>();
        resultDataMap.put("rootFolderId", rootFolderId);
        resultDataMap.put("systemFolderId", systemFolderId);
        resultDataMap.put("customFolderId", customFolderId);
        resultDataMap.put("userHeadFolderId", userHeadFolderId);
        resultDataMap.put("userLogFolderId", userLogFolderId);

        return new Result(true).datas(resultDataMap);
    }

    @Override
    public Result getUserHeadFolderId(String userId) {

        String userHeadFolderPath = String.format(FolderPath.USER_HEAD_FOLDER_PATH, userId);

        Folder folderQuery = new Folder();
        folderQuery.setFolderName(FolderPath.USER_HEAD_FOLDER_NAME);
        folderQuery.setFolderPath(userHeadFolderPath);
        folderQuery.setFolderOwner(userId);
        List<Folder> userHeadFolders = folderMapper.queryAll(folderQuery);
        if (ListUtil.isEmpty(userHeadFolders)) {
            return new Result(false);
        }

        return new Result(true).data("folderId", userHeadFolders.get(0).getFolderId());
    }

    @Override
    public Result getResources(FolderOrFile resource, Page page) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        String folderId = resource.getResourcesParent();
        if (StrUtil.isEmpty(folderId)) {
            Folder rootFolder = folderMapper.queryRootFolder(operator);
            if (rootFolder == null) {
                return new Result(false).message(MessageLevel.ERROR, Message.FOLDER_UNKNOWN);
            }
            folderId = rootFolder.getFolderId();
        }

        List<FolderOrFile> resourceList = folderMapper.queryAllResources(folderId, page);

        return new Result(true).data("resourceList", resourceList);
    }

    @Override
    public Result getCustomFolder(String userId) {
        String customFolderPath = String.format(FolderPath.CUSTOM_RESOURCE_FOLDER_PATH, userId);
        Folder folderQuery = new Folder();
        folderQuery.setFolderOwner(userId);
        folderQuery.setFolderPath(customFolderPath);
        List<Folder> customFolders = folderMapper.queryAll(folderQuery);
        if (ListUtil.isEmpty(customFolders)) {
            return new Result(false)
                    .message(MessageLevel.ERROR, Message.FOLDER_ERROR);
        }
        String folderId = customFolders.get(0).getFolderId();
        return new Result(true).data("folderId", folderId);
    }
}
