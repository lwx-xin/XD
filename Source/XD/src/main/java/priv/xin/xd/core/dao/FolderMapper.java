package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.Folder;
import priv.xin.xd.core.entity.FolderOrFile;

import java.util.List;

public interface FolderMapper {
    int deleteByPrimaryKey(String folderId);

    int insert(Folder record);

    Folder selectByPrimaryKey(String folderId);

    int updateByPrimaryKey(Folder record);

    List<Folder> queryAll(Folder folder);

    /**
     * 获取用户文件根目录信息
     *
     * @param userId
     * @return
     */
    Folder queryRootFolder(String userId);

    /**
     * 获取文件夹下的全部资源(文件夹，文件)
     *
     * @param folderId
     * @return
     */
    List<FolderOrFile> queryAllResources(@Param("folderId") String folderId, @Param("page") Page page);
}