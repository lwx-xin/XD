package priv.xin.xd.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.service.ISysFileService;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.dao.PositionMapper;
import priv.xin.xd.core.dao.UserMapper;
import priv.xin.xd.core.dao.UserPositionMapper;
import priv.xin.xd.core.entity.User;
import priv.xin.xd.core.entity.UserPosition;
import priv.xin.xd.core.entity.ex.PositionEx;
import priv.xin.xd.core.entity.ex.UserEx;
import priv.xin.xd.core.service.ISysUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/2 17:31
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private PathProperties pathProperties;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private UserPositionMapper userPositionMapper;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFolderService sysFolderService;

    @Override
    public Result queryListLimit(UserEx userEx, Page page) {
        List<UserEx> userList = userMapper.queryListLimit(userEx, page);
        int count = userMapper.queryListLimitCount(userEx);
        return new Result(true)
                .data("userList", userList)
                .data("count", count);
    }

    @Override
    public Result queryListLimitCount(UserEx userEx) {
        int count = userMapper.queryListLimitCount(userEx);
        return new Result(true).data("count", count);
    }

    @Override
    public Result queryDetail(String userId) {
        if (StrUtil.isEmpty(userId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.USER_UNKNOWN);
        }
        UserEx userDetail = userMapper.queryDetail(userId);
        if (userDetail == null) {
            return new Result(false);
        }
        return new Result(true).data("userDetail", userDetail);
    }

    @Override
    public Result updateUserDetail(String userId, UserEx userEx, MultipartFile files) {
        if (StrUtil.isEmpty(userId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.USER_UNKNOWN);
        }

        //修改用户头像文件
        String userHeadFileId = "";
        if (files != null) {
            Result userHeadFolderResult = sysFolderService.getUserHeadFolderId(userId);
            if (!userHeadFolderResult.getStatus()) {
                return new Result(false).message(MessageLevel.ERROR, Message.USER_HEAD_FOLDER_ERROR);
            }
            String userHeadFolderId = StrUtil.format(userHeadFolderResult.getData("folderId"));
            Result saveFileResult = sysFileService.saveFile(userHeadFolderId, files, CodeEnum.RESOURCE_TYPE_SYSTEM);
            if (!saveFileResult.getStatus()) {
                return saveFileResult.message(MessageLevel.ERROR, Message.USER_HEAD_EDIT_ERROR);
            }
            userHeadFileId = StrUtil.format(saveFileResult.getData("fileId"));
        }

        //修改用户信息
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userEx.getUserName());
        user.setUserEmail(userEx.getUserEmail());
        user.setUserQq(userEx.getUserQq());
        user.setUserPlatform(userEx.getUserPlatform());
        user.setUserModifyUser(userId);
        if (StrUtil.isNotEmpty(userHeadFileId)) {
            user.setUserHead(userHeadFileId);
        }
        if (userMapper.updateByPrimaryKey(user) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        // 清除旧的用户职位信息
        userPositionMapper.deleteByUserId(userId);
        // 添加用户职位信息
        return saveUserPosition(userEx, userId, userId);
    }

    @Override
    public Result insertUserDetail(UserEx userEx, MultipartFile files) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();
        String userId = StrUtil.getUUID();

        Result addUserDefaultFolderResult = sysFolderService.addUserDefaultFolder(userId);
        if (!addUserDefaultFolderResult.getStatus()) {
            return addUserDefaultFolderResult.message(MessageLevel.ERROR, Message.USER_HEAD_FOLDER_ERROR);
        }

        //修改用户头像文件
        String userHeadFileId = "";
        if (files != null) {
            String userHeadFolderId = StrUtil.format(addUserDefaultFolderResult.getData("userHeadFolderId"));
            Result saveFileResult = sysFileService.saveFile(userHeadFolderId, files, CodeEnum.RESOURCE_TYPE_SYSTEM);
            if (!saveFileResult.getStatus()) {
                return saveFileResult.message(MessageLevel.ERROR, Message.USER_HEAD_INSERT_ERROR);
            }
            userHeadFileId = StrUtil.format(saveFileResult.getData("fileId"));
        }

        // 添加用户信息
        String userNumber = userEx.getUserName();
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userEx.getUserName());
        user.setUserNumber(userNumber);
        try {
            user.setUserPwd(PasswordUtil.encrypt(userNumber, userEx.getUserPwd()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false).message(MessageLevel.ERROR, Message.USER_ENCRYPTION_PASSWORD_ERROR);
        }
        user.setUserQq(userEx.getUserQq());
        user.setUserEmail(userEx.getUserEmail());
        user.setUserPlatform(userEx.getUserPlatform());
        user.setUserDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        user.setUserCreateUser(operator);
        user.setUserModifyUser(operator);
        if (StrUtil.isNotEmpty(userHeadFileId)) {
            user.setUserHead(userHeadFileId);
        }
        if (userMapper.insert(user) != 1) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        // 添加用户职位信息
        return saveUserPosition(userEx, operator, userId);
    }

    @Override
    public Result deleteByUserId(String userId) {
        if (StrUtil.isEmpty(userId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.USER_UNKNOWN);
        }

        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return new Result(false).message(MessageLevel.ERROR, Message.USER_UNKNOWN);
        }
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        User updateUser = new User();
        updateUser.setUserId(userId);
        updateUser.setUserModifyUser(operator);
        if (CodeEnum.DEL_FLAG_1.getValue().equals(user.getUserDelFlag())) {
            //封禁
            updateUser.setUserDelFlag(CodeEnum.DEL_FLAG_0.getValue());
        } else {
            //解封
            updateUser.setUserDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        }
        if (userMapper.updateByPrimaryKey(updateUser) != 1) {
            return new Result(false);
        }
        return new Result(true);
    }

    /**
     * 添加用户职位信息
     *
     * @param userEx
     * @param operator
     * @param userId
     * @return
     */
    private Result saveUserPosition(UserEx userEx, String operator, String userId) {
        List<PositionEx> positionExList = userEx.getPositionExList();
        if (ListUtil.isNotEmpty(positionExList)) {
            for (PositionEx positionEx : positionExList) {
                String positionId = positionEx.getPositionId();
                if (positionMapper.selectByPrimaryKey(positionId) == null) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false)
                            .message(MessageLevel.ERROR, Message.POSITION_UNKNOWN);
                }

                UserPosition userPosition = new UserPosition();
                userPosition.setUserPositionId(StrUtil.getUUID());
                userPosition.setUserId(userId);
                userPosition.setPositionId(positionId);
                userPosition.setUserPositionDelFlag(CodeEnum.DEL_FLAG_1.getValue());
                userPosition.setUserPositionCreateUser(operator);
                userPosition.setUserPositionModifyUser(operator);
                userPositionMapper.insert(userPosition);
            }
        }
        return new Result(true);
    }

}
