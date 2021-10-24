package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.UrlParam;
import priv.xin.xd.core.entity.ex.UrlParamEx;

import java.util.List;

public interface UrlParamMapper {
    int deleteByPrimaryKey(String urlParamId);

    int insert(UrlParam record);

    UrlParam selectByPrimaryKey(String urlParamId);

    int updateByPrimaryKey(UrlParam record);

    int deleteByUrlId(String urlId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param urlParam 实例对象
     * @return 对象列表
     */
    List<UrlParam> queryAll(UrlParam urlParam);

    /**
     * 获取全部数据（包含请求URL数据）
     * @return
     */
    List<UrlParamEx> queryAllDetail();
}