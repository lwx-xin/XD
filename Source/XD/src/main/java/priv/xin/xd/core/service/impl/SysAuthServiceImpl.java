package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysAuthService;
import priv.xin.xd.core.dao.AuthMapper;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.ex.AuthEx;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/9/1 18:45
 */
@Service
public class SysAuthServiceImpl implements ISysAuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public Result queryListLimit(AuthEx authEx, Page page) {
        List<AuthEx> authList = authMapper.queryListLimit(authEx, page);
        int count = authMapper.queryListLimitCount(authEx);
        return new Result(true).data("authList", authList).data("count", count);
    }

    @Override
    public Result queryListLimitCount(AuthEx authEx) {
        int count = authMapper.queryListLimitCount(authEx);
        return new Result(true).data("count", count);
    }

    @Override
    public Result deleteByAuthId(String authId) {
        if (StrUtil.isEmpty(authId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.AUTH_UNKNOWN);
        }
        // check
        Result checkResult = new Result(true);
        AuthEx authUsedCount = authMapper.queryAuthUsedCount(authId);
        int menuCount = authUsedCount.getMenuCount();
        int urlCount = authUsedCount.getUrlCount();
        int positionCount = authUsedCount.getPositionCount();
        if (menuCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "有" + menuCount + "个菜单正在使用该权限");
        }
        if (urlCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "有" + urlCount + "个请求正在使用该权限");
        }
        if (positionCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "有" + positionCount + "个职位正在使用该权限");
        }

        if (!checkResult.getStatus()) {
            return checkResult;
        }

        int i = authMapper.deleteByPrimaryKey(authId);
        if (i == 0) {
            return new Result(false);
        }
        return new Result(true);
    }

    @Override
    public Result updateAuthDetail(String authId, Auth auth) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        if (StrUtil.isEmpty(authId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.AUTH_UNKNOWN);
        }

        Auth updateAuth = new Auth();
        updateAuth.setAuthId(authId);
        updateAuth.setAuthName(auth.getAuthName());
        updateAuth.setAuthModifyUser(operator);
        if (authMapper.updateByPrimaryKey(updateAuth) != 1) {
            return new Result(false);
        }
        return new Result(true);
    }

    @Override
    public Result insertAuthDetail(Auth auth) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        Auth insertAuth = new Auth();
        insertAuth.setAuthId(StrUtil.getUUID());
        insertAuth.setAuthName(auth.getAuthName());
        insertAuth.setAuthDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        insertAuth.setAuthCreateUser(operator);
        insertAuth.setAuthModifyUser(operator);
        if (authMapper.insert(insertAuth) != 1) {
            return new Result(false);
        }
        return new Result(true);
    }

    @Override
    public Result queryDetail(String authId) {
        if (StrUtil.isEmpty(authId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.AUTH_UNKNOWN);
        }
        Auth authDetail = authMapper.selectByPrimaryKey(authId);
        return new Result(true).data("authDetail", authDetail);
    }
}
