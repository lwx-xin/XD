package priv.xin.xd.core.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.service.ISysFileService;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.dao.FileMapper;
import priv.xin.xd.core.dao.FolderMapper;
import priv.xin.xd.core.entity.File;
import priv.xin.xd.core.entity.ex.FileEx;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/6 17:04
 */
@Service
public class SysFileServiceImpl implements ISysFileService {

    @Resource
    private PathProperties pathProperties;

    @Resource
    private FileMapper fileMapper;
    @Resource
    private FolderMapper folderMapper;
    @Resource
    private ISysFolderService sysFolderService;

    @Override
    public Result saveLogFile(String userId, String folderId, String filePath, String fileSize) {
        String fileId = StrUtil.getUUID();

        String fileName = FilenameUtils.getName(filePath);
        String extension = FilenameUtils.getExtension(filePath);

        File fileInsert = new File();
        fileInsert.setFileId(fileId);
        fileInsert.setFileName(fileName);
        fileInsert.setFileSize(fileSize);
        fileInsert.setFileSuffix(extension.toLowerCase());
        fileInsert.setFileFolder(folderId);
        fileInsert.setFileOwner(userId);
        fileInsert.setFilePath(filePath);
        fileInsert.setFileDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        fileInsert.setFileCreateUser(userId);
        fileInsert.setFileModifyUser(userId);

        int insert = fileMapper.insert(fileInsert);
        if (insert == 0) {
            return new Result(false);
        }

        return new Result(true).data("fileId", fileId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result saveFile(String folderId, MultipartFile file, CodeEnum resourceType) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();
        String fileId = StrUtil.getUUID();

        File fileInfo = FileUtil.getFileInfo(file, resourceType);

        // 获取文件夹路径
        Result folderPathResult = sysFolderService.getFolderPath(folderId);
        if (!folderPathResult.getStatus()) {
            return folderPathResult.clearData()
                    .data("fileName", fileInfo.getFileName());
        }
        String folderRealPath = StrUtil.format(folderPathResult.getDatas().get("folderRealPath"));
        String folderPath = StrUtil.format(folderPathResult.getDatas().get("folderPath"));

        // 去除第一个斜杠
//        if (folderPath.startsWith("/")) {
//            folderPath = folderPath.substring(1);
//        }

        fileInfo.setFileId(fileId);
        fileInfo.setFileFolder(folderId);
        fileInfo.setFileOwner(operator);
        fileInfo.setFilePath(folderPath + "/" + fileInfo.getFileName());
        fileInfo.setFileDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        fileInfo.setFileCreateUser(operator);
        fileInfo.setFileModifyUser(operator);

        int insert = fileMapper.insert(fileInfo);
        if (insert == 0) {
            return new Result(false)
                    .data("fileName", fileInfo.getFileName());
        }

        // 上传文件本体
        String filePath = folderRealPath + "/" + fileInfo.getFileName();
        java.io.File uploadFile = new java.io.File(filePath);
        uploadFile.setReadOnly();
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
        } catch (IOException e) {
            LoggerUtil.error(e, getClass());
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false)
                    .data("fileName", fileInfo.getFileName())
                    .message(MessageLevel.ERROR, Message.FILE_UPLOAD_ERROR);
        }

        // 生成略缩图
        CodeEnum fileType = FileUtil.getFileType(fileInfo.getFileSuffix());
        if (CodeEnum.FILE_TYPE_PICTURE.equals(fileType)) {
            try {
                String thumbnailsPath = FileUtil.getFileThumbnailsPath(filePath);
                FileUtil.createThumbnails(file.getInputStream(), 198, 132, thumbnailsPath);
            } catch (IOException e) {
                LoggerUtil.error(e, getClass());
                // 回滚数据
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                uploadFile.delete();
                return new Result(false)
                        .data("fileName", fileInfo.getFileName())
                        .message(MessageLevel.ERROR, Message.FILE_UPLOAD_ERROR);
            }
        }

        return new Result(true)
                .data("fileId", fileId)
                .data("fileName", fileInfo.getFileName());
    }

    @Override
    public Result uploadFile(MultipartFile[] files) {
        if (ListUtil.isEmpty(files)) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UPLOAD_NO_FILE);
        }
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        // 获取用户自定义文件夹
        Result customFolderResult = sysFolderService.getCustomFolder(operator);
        if (!customFolderResult.getStatus()) {
            return customFolderResult;
        }
        String customFolderId = StrUtil.format(customFolderResult.getData("folderId"));

        SysFileServiceImpl thisService = SpringUtil.getBean(this.getClass());
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            Result result = thisService.saveFile(customFolderId, file, CodeEnum.RESOURCE_TYPE_CUSTOM);
            if (!result.getStatus()) {
                fileNames.add(StrUtil.format(result.getData("fileName")));
            }
        }

        return new Result(true)
                .data("uploadFailedFileName", fileNames);
    }

    @Override
    public Result queryListLimit(FileEx fileEx, Page page) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        FileEx fileQuery = new FileEx();

        if(!ShiroUtil.isAdmin()){
            // 获取用户自定义文件夹
            Result customFolderResult = sysFolderService.getCustomFolder(operator);
            if (!customFolderResult.getStatus()) {
                return customFolderResult;
            }
            String customFolderId = StrUtil.format(customFolderResult.getData("folderId"));
            fileQuery.setFileFolder(customFolderId);
        }

        fileQuery.setFileName(fileEx.getFileName());
        fileQuery.setFileType(fileEx.getFileType());
        fileQuery.setFileOwner(operator);

        List<FileEx> fileList = fileMapper.queryListLimit(fileQuery, page);
        int count = fileMapper.queryListLimitCount(fileQuery);
        Result result = new Result(true)
                .data("fileList", fileList)
                .data("count", count);

        if (ListUtil.isEmpty(fileList)) {
            result.message(MessageLevel.ERROR, Message.FILE_LIST_NULL);
        }
        return result;
    }

    @Override
    public Result queryDetail(String fileId) {
        if (StrUtil.isEmpty(fileId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UNKNOWN);
        }
        FileEx fileEx = fileMapper.queryDetail(fileId);
        return new Result(true).data("fileDetail", fileEx);
    }

    @Override
    public Result updateFileDetail(String fileId, File file) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        if (StrUtil.isEmpty(fileId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UNKNOWN);
        }

        File fileInfo = fileMapper.selectByPrimaryKey(fileId);
        if (fileInfo == null) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UNKNOWN);
        }

        String filePath = fileInfo.getFilePath();
        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        filePath = fileSaveRealPath + filePath;

        String newFileName = file.getFileName();
        String newFileSuffix = FilenameUtils.getExtension(newFileName);
        String newFilePath = FilenameUtils.getFullPath(fileInfo.getFilePath()) + newFileName;

        File fileUpdate = new File();
        fileUpdate.setFileId(fileId);
        fileUpdate.setFileName(newFileName);
        fileUpdate.setFilePath(newFilePath);
        fileUpdate.setFileSuffix(newFileSuffix);
        fileUpdate.setFileModifyUser(operator);

        int i = fileMapper.updateByPrimaryKey(fileUpdate);
        if (i != 1) {
            return new Result(false);
        }

        // 重命名文件
        java.io.File renameFile = new java.io.File(filePath);
        if (renameFile.exists()) {
            // 不带文件名的路径
            String fullPath = FilenameUtils.getFullPath(filePath);
            java.io.File newFile = new java.io.File(fullPath + newFileName);
            if (!renameFile.renameTo(newFile)) {
                // 回滚数据
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Result(false);
            }
        }

        // 重命名略缩图
        CodeEnum fileType = FileUtil.getFileType(fileUpdate.getFileSuffix());
        if (CodeEnum.FILE_TYPE_PICTURE.equals(fileType)) {
            String thumbnailsPath = FileUtil.getFileThumbnailsPath(filePath);
            java.io.File renameFileThumbnails = new java.io.File(thumbnailsPath);
            if (renameFileThumbnails.exists()) {
                // 不带文件名的路径
                String fullPath = FilenameUtils.getFullPath(thumbnailsPath);
                String newFileThumbnailsName = FileUtil.getFileThumbnailsName(newFileName);
                java.io.File newFile = new java.io.File(fullPath + newFileThumbnailsName);
                if (!renameFileThumbnails.renameTo(newFile)) {
                    // 回滚数据
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false);
                }
            }
        }


        return new Result(true);
    }

    @Override
    public Result deleteByFileId(String fileId) {
        if (StrUtil.isEmpty(fileId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UNKNOWN);
        }

        File file = fileMapper.selectByPrimaryKey(fileId);
        if (file == null) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_UNKNOWN);
        }

        int i = fileMapper.deleteByPrimaryKey(fileId);
        if (i == 0) {
            return new Result(false);
        }

        String filePath = file.getFilePath();
        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        filePath = fileSaveRealPath + filePath;

        // 删除文件本体
        java.io.File deleteFile = new java.io.File(filePath);
        if (deleteFile.exists()) {
            if (!deleteFile.delete()) {
                // 回滚数据
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Result(false);
            }
        }

        // 删除文件略缩图
        CodeEnum fileType = FileUtil.getFileType(file.getFileSuffix());
        if (CodeEnum.FILE_TYPE_PICTURE.equals(fileType)) {
            String thumbnailsPath = FileUtil.getFileThumbnailsPath(filePath);
            java.io.File deleteFileThumbnails = new java.io.File(thumbnailsPath);
            if (deleteFileThumbnails.exists()) {
                if (!deleteFileThumbnails.delete()) {
                    // 回滚数据
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false);
                }
            }
        }

        return new Result(true);
    }

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) {
        if (StrUtil.isEmpty(fileId)) {
            return;
        }

        File file = fileMapper.selectByPrimaryKey(fileId);
        if (file == null) {
            return;
        }
        String filePath = file.getFilePath();

        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        filePath = fileSaveRealPath + filePath;
        try {
            // 下载本地文件
            String fileName = file.getFileName(); // 文件的默认保存名
            // 读到流中
            InputStream inStream = new FileInputStream(filePath);// 文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getImageContent(String fileId, boolean isThumbnails, HttpServletResponse response) {
        if (StrUtil.isEmpty(fileId)) {
            return;
        }

        File file = fileMapper.selectByPrimaryKey(fileId);
        if (file == null) {
            return;
        }
        String filePath = file.getFilePath();
        if (isThumbnails) {
            filePath = FileUtil.getFileThumbnailsPath(filePath);
        }

        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        filePath = fileSaveRealPath + filePath;

        java.io.File filePic = new java.io.File(filePath);
        if (filePic.exists()) {
            FileInputStream is = null;
            OutputStream toClient = null;
            try {
                is = new FileInputStream(filePic);
                int i = is.available(); // 得到文件大小
                byte data[] = new byte[i];
                is.read(data); // 读数据
                response.setContentType("image/*"); // 设置返回的文件类型
                toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                toClient.write(data); // 输出数据
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    toClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getVideoContent(String fileId, HttpServletRequest request, HttpServletResponse response) {
        if (StrUtil.isEmpty(fileId)) {
            return;
        }

        File fileInfo = fileMapper.selectByPrimaryKey(fileId);
        if (fileInfo == null) {
            return;
        }
        String filePath = fileInfo.getFilePath();
        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        filePath = fileSaveRealPath + filePath;

        response.reset();
        // 获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        if (rangeString == null) {
            return;
        }
        try {
            // 获取响应的输出流
            OutputStream outputStream = response.getOutputStream();
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                RandomAccessFile targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                // 播放
                if (rangeString != null) {
                    long range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                    // 设置内容类型
                    response.setHeader("Content-Type", "video/mp4");
                    // 设置此次相应返回的数据长度
                    response.setHeader("Content-Length", String.valueOf(fileLength - range));
                    // 设置此次相应返回的数据范围
                    response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
                    // 返回码需要为206，而不是200
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    // 设定文件读取开始位置（以字节为单位）
                    targetFile.seek(range);
                }
//				else {// 下载
//					System.err.println("下载");
//					// 设置响应头，把文件名字设置好
//					response.setHeader("Content-Disposition", "attachment; filename=" + "test.mp4");
//					// 设置文件长度
//					response.setHeader("Content-Length", String.valueOf(fileLength));
//					// 解决编码问题
//					response.setHeader("Content-Type", "application/octet-stream");
//				}

                byte[] cache = new byte[1024 * 300];
                int flag;
                while ((flag = targetFile.read(cache)) != -1) {
                    outputStream.write(cache, 0, flag);
                }
            } else {
                String message = "file:" + "test.mp4" + " not exists";
                // 解决编码问题
                response.setHeader("Content-Type", "application/json");
                outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            }

            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
