package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.File;
import priv.xin.xd.core.entity.ex.FileEx;

import java.util.List;

public interface FileMapper {
    int deleteByPrimaryKey(String fileId);

    int insert(File record);

    File selectByPrimaryKey(String fileId);

    int updateByPrimaryKey(File record);

    List<FileEx> queryListLimit(@Param("file") FileEx fileEx, @Param("page") Page page);

    int queryListLimitCount(@Param("file") FileEx fileEx);

    List<File> queryAll(File file);

    FileEx queryDetail(String fileId);
}