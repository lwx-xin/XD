package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Log;

public interface LogMapper {
    int deleteByPrimaryKey(String logId);

    int insert(Log record);

    Log selectByPrimaryKey(String logId);

    int updateByPrimaryKey(Log record);
}