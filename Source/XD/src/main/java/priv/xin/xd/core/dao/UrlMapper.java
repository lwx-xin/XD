package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.ex.UrlEx;

import java.util.List;

public interface UrlMapper {
    int deleteByPrimaryKey(String urlId);

    int insert(Url record);

    Url selectByPrimaryKey(String urlId);

    int updateByPrimaryKey(Url record);

    /**
     * 通过实体作为筛选条件查询(请求地址使用正表达式匹配)
     *
     * @param url 实例对象
     * @return 对象列表
     */
    List<Url> queryAllRegexp(Url url);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param url 实例对象
     * @return 对象列表
     */
    List<Url> queryAll(Url url);

    /**
     * 带条件的分页查询
     *
     * @param urlEx 查询条件
     * @param page
     * @return
     */
    List<UrlEx> queryListLimit(@Param("urlEx") UrlEx urlEx, @Param("page") Page page);

    /**
     * 查询复核条件的数据条数
     *
     * @param urlEx 查询条件
     * @return
     */
    int queryListLimitCount(@Param("urlEx") UrlEx urlEx);

    /**
     * 查询请求详细信息(包括职位信息)
     *
     * @param urlId
     * @return
     */
    public UrlEx queryDetail(@Param("urlId") String urlId);

    /**
     * url被使用的次数
     * @param urlId
     * @return
     */
    public UrlEx queryUrlUsedCount(String urlId);
}