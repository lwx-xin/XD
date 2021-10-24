package priv.xin.xd.core.service;

import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.ex.UserEx;

/**
 * @author ：lu
 * @date ：2021/7/2 17:31
 */
public interface ISysUserService {

    /**
     * 带条件的分页查询
     *
     * @param userEx 查询条件
     * @param page
     * @return data: userList,count
     */
    public Result queryListLimit(UserEx userEx, Page page);

    /**
     * 查询复核条件的数据条数
     *
     * @param userEx 查询条件
     * @return data: count
     */
    public Result queryListLimitCount(UserEx userEx);

    /**
     * 查询用户详细信息(包括职位信息)
     *
     * @param userId
     * @return data: userDetail
     */
    public Result queryDetail(String userId);

    /**
     * 修改用户信息(包括职位)
     *
     * @param userId 用户id
     * @param userEx 要修改的信息
     * @param files  头像文件
     * @return
     */
    public Result updateUserDetail(String userId, UserEx userEx, MultipartFile files);

    /**
     * 新增用户信息(包括职位)
     *
     * @param userEx 要修改的信息
     * @param files  头像文件
     * @return
     */
    public Result insertUserDetail(UserEx userEx, MultipartFile files);

    /**
     * 封禁/解禁 用户
     *
     * @param userId
     * @return
     */
    public Result deleteByUserId(String userId);

}
