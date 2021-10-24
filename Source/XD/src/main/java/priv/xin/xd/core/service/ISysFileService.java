package priv.xin.xd.core.service;

import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.File;
import priv.xin.xd.core.entity.ex.FileEx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：lu
 * @date ：2021/7/6 17:03
 */
public interface ISysFileService {

    /**
     * 保存日志文件
     *
     * @param userId
     * @param folderId
     * @param filePath
     * @param fileSize
     * @return
     */
    public Result saveLogFile(String userId, String folderId, String filePath, String fileSize);

    /**
     * 保存文件
     *
     * @param folderId     文件所属文件夹id
     * @param file         文件主体
     * @param resourceType 资源类型
     * @return data: fileId, fileName
     */
    public Result saveFile(String folderId, MultipartFile file, CodeEnum resourceType);

    /**
     * 上传文件到用户自定义文件夹
     *
     * @param files
     * @return data: uploadFailedFileName
     */
    public Result uploadFile(MultipartFile[] files);

    /**
     * @param fileEx
     * @param page
     * @return data: fileList,count
     */
    public Result queryListLimit(FileEx fileEx, Page page);

    /**
     * 查询文件详细信息
     *
     * @param fileId
     * @return data: fileDetail
     */
    public Result queryDetail(String fileId);

    /**
     * 修改文件信息
     *
     * @param fileId
     * @param file
     * @return
     */
    public Result updateFileDetail(String fileId, File file);

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    public Result deleteByFileId(String fileId);

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     */
    public void downloadFile(String fileId, HttpServletResponse response);

    /**
     * 获取图片流
     *
     * @param fileId
     * @param isThumbnails 是否为封面图片
     * @param response
     */
    public void getImageContent(String fileId, boolean isThumbnails, HttpServletResponse response);

    /**
     * 获取视屏流
     *
     * @param fileId
     * @param request
     * @param response
     */
    public void getVideoContent(String fileId, HttpServletRequest request, HttpServletResponse response);

}
