package priv.xin.xd.core.service;

import priv.xin.xd.common.result.Result;

/**
 * @author ：lu
 * @date ：2021/5/31 18:17
 */
public interface ISysCommonService {

    /**
     * 登录
     *
     * @param userNumber 账号
     * @param userPwd    密码
     * @return
     */
    public Result login(String userNumber, String userPwd);

    /**
     * 获取用戶的菜單列表
     *
     * @param userId
     * @return data: userMenu
     */
    public Result getUserMenu(String userId);

}
