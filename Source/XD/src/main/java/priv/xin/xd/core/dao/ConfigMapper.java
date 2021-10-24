package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Config;

import java.util.List;

public interface ConfigMapper {
    int deleteByPrimaryKey(String configId);

    int insert(Config record);

    Config selectByPrimaryKey(String configId);

    int updateByPrimaryKey(Config record);

    /**
     * 通过实体作为筛选条件查询
     *
     * @return 对象列表
     */
    List<Config> queryAll();
}