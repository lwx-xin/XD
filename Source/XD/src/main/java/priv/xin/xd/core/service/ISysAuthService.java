package priv.xin.xd.core.service;

import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.ex.AuthEx;

/**
 * @author ：lu
 * @date ：2021/9/1 18:44
 */
public interface ISysAuthService {

    /**
     * 条件分页查询
     *
     * @param authEx
     * @param page
     * @return data: authList,count
     */
    public Result queryListLimit(AuthEx authEx, Page page);

    /**
     * 符合条件的个数
     *
     * @param authEx
     * @return data: count
     */
    public Result queryListLimitCount(AuthEx authEx);

    public Result deleteByAuthId(String authId);

    public Result updateAuthDetail(String authId, Auth auth);

    public Result insertAuthDetail(Auth auth);

    /**
     *
     * @param authId
     * @return data: authDetail
     */
    public Result queryDetail(String authId);

}
