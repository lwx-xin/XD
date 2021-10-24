package priv.xin.xd.core.service;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.ex.UrlEx;

/**
 * @author ：lu
 * @date ：2021/8/14 11:56
 */
public interface ISysUrlService {

    /**
     * 带条件的分页查询
     *
     * @param urlEx 查询条件
     * @param page  分页/排序信息
     * @return data: urlList,count
     */
    public Result queryListLimit(UrlEx urlEx, Page page);

    /**
     * 查询复核条件的数据条数
     *
     * @param urlEx 查询条件
     * @return data: count
     */
    public Result queryListLimitCount(@Param("urlEx") UrlEx urlEx);

    /**
     * 查询请求详细信息
     *
     * @param urlId
     * @return data: urlDetail
     */
    public Result queryDetail(String urlId);

    /**
     * 修改请求信息
     *
     * @param urlId 请求id
     * @param urlEx 要修改的信息
     * @return
     */
    public Result updateUrlDetail(String urlId, UrlEx urlEx);

    /**
     * 添加请求信息
     *
     * @param urlEx 要修改的信息
     * @return
     */
    public Result insertUrlDetail(UrlEx urlEx);

    /**
     * 根据请求id删除请求数据
     *
     * @param urlId
     * @return
     */
    public Result deleteByUrlId(String urlId);

}
