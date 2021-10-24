package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.FileType;
import priv.xin.xd.core.entity.ex.FileTypeEx;

import java.util.List;

public interface FileTypeMapper {
    int deleteByPrimaryKey(String fileSuffix);

    int insert(FileType record);

    FileType selectByPrimaryKey(String fileSuffix);

    int updateByPrimaryKey(FileType record);

    List<FileTypeEx> queryListLimit(@Param("fileType") FileType fileType, @Param("page") Page page);

    int queryListLimitCount(@Param("fileType") FileType fileType);

    List<FileTypeEx> queryAll(@Param("fileType") FileType fileType);
}