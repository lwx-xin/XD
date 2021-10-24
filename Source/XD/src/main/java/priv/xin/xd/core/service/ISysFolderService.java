package priv.xin.xd.core.service;

import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.FolderOrFile;

/**
 * @author ：lu
 * @date ：2021/7/6 17:03
 */
public interface ISysFolderService {

    /**
     * 获取文件夹路劲(真实路径)
     *
     * @param folderId 文件夹
     * @return data:
     * <br/>folderRealPath -- C://a/userid/system/folder 完整路径
     * <br/>folderPath -- /userid/system/folder
     */
    public Result getFolderPath(String folderId);

    /**
     * 添加用户默认文件夹(添加新用户时调用)
     *
     * @param userId
     * @return data:<br/>rootFolderId<br/>systemFolderId<br/>customFolderId<br/>userHeadFolderId<br/>userLogFolderId
     */
    public Result addUserDefaultFolder(String userId);

    /**
     * 获取用户头像文件夹
     *
     * @param userId
     * @return data:folderId
     */
    public Result getUserHeadFolderId(String userId);

    /**
     * 获取文件夹下的全部资源(文件夹，文件)
     *
     * @param resource 文件夹为空，默认根目录
     * @param page
     * @return data: resourceList
     */
    public Result getResources(FolderOrFile resource, Page page);

    /**
     * 获取用户自定义文件夹
     *
     * @param userId
     * @return data: folderId
     */
    public Result getCustomFolder(String userId);

}
