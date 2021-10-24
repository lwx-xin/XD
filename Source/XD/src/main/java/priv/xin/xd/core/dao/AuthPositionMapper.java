package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.AuthPosition;

import java.util.List;

public interface AuthPositionMapper {
    int deleteByPrimaryKey(String authPositionId);

    int insert(AuthPosition record);

    AuthPosition selectByPrimaryKey(String authPositionId);

    int updateByPrimaryKey(AuthPosition record);

    int deleteByPositionId(String positionId);

    List<AuthPosition> queryAll(AuthPosition authPosition);
}