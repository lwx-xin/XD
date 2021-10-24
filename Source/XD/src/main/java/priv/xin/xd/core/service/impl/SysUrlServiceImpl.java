package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.AuthUrl;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.UrlParam;
import priv.xin.xd.core.service.ISysUrlService;
import priv.xin.xd.core.dao.AuthMapper;
import priv.xin.xd.core.dao.AuthUrlMapper;
import priv.xin.xd.core.dao.UrlMapper;
import priv.xin.xd.core.dao.UrlParamMapper;
import priv.xin.xd.core.entity.ex.UrlEx;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/8/14 11:57
 */
@Service
public class SysUrlServiceImpl implements ISysUrlService {

    @Resource
    private UrlMapper urlMapper;
    @Resource
    private AuthUrlMapper authUrlMapper;
    @Resource
    private AuthMapper authMapper;
    @Resource
    private UrlParamMapper urlParamMapper;

    @Override
    public Result queryListLimit(UrlEx urlEx, Page page) {
        List<UrlEx> urlList = urlMapper.queryListLimit(urlEx, page);
        int count = urlMapper.queryListLimitCount(urlEx);
        return new Result(true)
                .data("urlList", urlList)
                .data("count", count);
    }

    @Override
    public Result queryListLimitCount(UrlEx urlEx) {
        int count = urlMapper.queryListLimitCount(urlEx);
        return new Result(true).data("count", count);
    }

    @Override
    public Result queryDetail(String urlId) {
        if (StrUtil.isEmpty(urlId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.URL_UNKNOWN);
        }
        UrlEx urlDetail = urlMapper.queryDetail(urlId);
        return new Result(true).data("urlDetail", urlDetail);
    }

    @Override
    public Result updateUrlDetail(String urlId, UrlEx urlEx) {
        if (StrUtil.isEmpty(urlId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.URL_UNKNOWN);
        }
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        // 修改请求信息
        Url updateUrl = new Url();
        updateUrl.setUrlId(urlId);
        updateUrl.setUrlPath(urlEx.getUrlPath());
        updateUrl.setUrlType(urlEx.getUrlType());
        updateUrl.setUrlPlatform(urlEx.getUrlPlatform());
        updateUrl.setUrlRemarks(urlEx.getUrlRemarks());
        updateUrl.setUrlModifyUser(operator);
        if (urlMapper.updateByPrimaryKey(updateUrl) == 0) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        // 修改请求权限信息
        authUrlMapper.deleteByUrlId(urlId);
        Result saveAuthUrlResult = saveAuthUrl(urlEx, operator, urlId);
        if (!saveAuthUrlResult.getStatus()) {
            return saveAuthUrlResult;
        }

        // 修改请求参数信息
        urlParamMapper.deleteByUrlId(urlId);
        Result saveUrlParamResult = saveUrlParam(urlEx, operator, urlId);
        if (!saveUrlParamResult.getStatus()) {
            return saveUrlParamResult;
        }

        return new Result(true);
    }

    @Override
    public Result insertUrlDetail(UrlEx urlEx) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        String urlId = StrUtil.getUUID();

        Url insertUrl = new Url();
        insertUrl.setUrlId(urlId);
        insertUrl.setUrlPath(urlEx.getUrlPath());
        insertUrl.setUrlType(urlEx.getUrlType());
        insertUrl.setUrlPlatform(urlEx.getUrlPlatform());
        insertUrl.setUrlRemarks(urlEx.getUrlRemarks());
        insertUrl.setUrlDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        insertUrl.setUrlModifyUser(operator);
        insertUrl.setUrlCreateUser(operator);
        if (urlMapper.insert(insertUrl) == 0) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        // 添加请求权限信息
        Result saveAuthUrlResult = saveAuthUrl(urlEx, operator, urlId);
        if (!saveAuthUrlResult.getStatus()) {
            return saveAuthUrlResult;
        }

        // 添加请求参数信息
        Result saveUrlParamResult = saveUrlParam(urlEx, operator, urlId);
        if (!saveUrlParamResult.getStatus()) {
            return saveUrlParamResult;
        }

        return new Result(true);
    }

    @Override
    public Result deleteByUrlId(String urlId) {
        if (StrUtil.isEmpty(urlId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.URL_UNKNOWN);
        }

        // check
        Result checkResult = new Result(true);
        UrlEx urlUsedCount = urlMapper.queryUrlUsedCount(urlId);
        int menuCount = urlUsedCount.getMenuCount();
        // 是否有menu使用过该url
        if (menuCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "该请求已被" + menuCount + "个菜单使用");
        }

        if (!checkResult.getStatus()) {
            return checkResult;
        }

        // 删除url
        int i = urlMapper.deleteByPrimaryKey(urlId);
        if (i == 0) {
            return new Result(false).message(MessageLevel.ERROR, "请求信息删除失败");
        }
        // 删除url的参数
        urlParamMapper.deleteByUrlId(urlId);
        // 删除url的权限
        authUrlMapper.deleteByUrlId(urlId);

        return new Result(true);
    }

    /**
     * 添加请求权限信息
     *
     * @param urlEx
     * @param operator
     * @param urlId
     * @return
     */
    private Result saveAuthUrl(UrlEx urlEx, String operator, String urlId) {
        List<Auth> authList = urlEx.getAuthList();
        if (ListUtil.isNotEmpty(authList)) {
            for (Auth auth : authList) {
                String authId = auth.getAuthId();
                if (authMapper.selectByPrimaryKey(authId) == null) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false).message(MessageLevel.ERROR, Message.AUTH_UNKNOWN);
                }

                AuthUrl authUrl = new AuthUrl();
                authUrl.setAuthUrlId(StrUtil.getUUID());
                authUrl.setAuthId(authId);
                authUrl.setUrlId(urlId);
                authUrl.setAuthUrlDelFlag(CodeEnum.DEL_FLAG_1.getValue());
                authUrl.setAuthUrlModifyUser(operator);
                authUrl.setAuthUrlCreateUser(operator);
                if (authUrlMapper.insert(authUrl) == 0) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false).message(MessageLevel.ERROR, Message.URL_AUTH_EDIT_ERROR);
                }
            }
        }
        return new Result(true);
    }

    /**
     * 添加请求参数信息
     *
     * @param urlEx
     * @param operator
     * @param urlId
     * @return
     */
    private Result saveUrlParam(UrlEx urlEx, String operator, String urlId) {
        List<UrlParam> urlParamList = urlEx.getUrlParamList();
        if (ListUtil.isNotEmpty(urlParamList)) {
            for (UrlParam urlParam : urlParamList) {
                String urlParamId = urlParam.getUrlParamId();

                UrlParam insertUrlParam = new UrlParam();
                insertUrlParam.setUrlId(urlId);
                insertUrlParam.setUrlParamName(urlParam.getUrlParamName());
                insertUrlParam.setUrlParamValue(urlParam.getUrlParamValue());
                insertUrlParam.setUrlParamRequired(urlParam.getUrlParamRequired());
                insertUrlParam.setUrlParamErrHint(urlParam.getUrlParamErrHint());
                insertUrlParam.setUrlParamRemark(urlParam.getUrlParamRemark());
                insertUrlParam.setUrlParamDelFlag(CodeEnum.DEL_FLAG_1.getValue());
                insertUrlParam.setUrlParamModifyUser(operator);
                if (StrUtil.isNotEmpty(urlParamId)) {
                    insertUrlParam.setUrlParamId(urlParamId);
                    insertUrlParam.setUrlParamCreateUser(urlParam.getUrlParamCreateUser());
                } else {
                    insertUrlParam.setUrlParamId(StrUtil.getUUID());
                    insertUrlParam.setUrlParamCreateUser(operator);
                }

                if (urlParamMapper.insert(insertUrlParam) == 0) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false).message(MessageLevel.ERROR, Message.URL_PARAM_EDIT_ERROR);
                }
            }
        }
        return new Result(true);
    }
}
