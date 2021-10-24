package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Code;

import java.util.List;

public interface CodeMapper {
    int deleteByPrimaryKey(String codeId);

    int insert(Code record);

    Code selectByPrimaryKey(String codeId);

    int updateByPrimaryKey(Code record);

    int deleteAll();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param code 实例对象
     * @return 对象列表
     */
    List<Code> queryAll(Code code);
}