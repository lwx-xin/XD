package priv.xin.xd.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.expansionService.redis.service.FileTypeRedis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：lu
 * @date ：2021/9/9 18:45
 */
public class FileUtil {

    public static priv.xin.xd.core.entity.File getFileInfo(MultipartFile file, CodeEnum resourceType) {
        String fileName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName);
        long size = file.getSize();

        if (CodeEnum.RESOURCE_TYPE_SYSTEM.equals(resourceType)) {
            String baseName = FilenameUtils.getBaseName(fileName);
            String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
            if (StrUtil.isNotEmpty(ext)) {
                fileName = date + "." + ext;
            } else {
                fileName = date;
            }
        }

        priv.xin.xd.core.entity.File fileInfo = new priv.xin.xd.core.entity.File();
        fileInfo.setFileName(fileName);
        fileInfo.setFileSuffix(ext.toLowerCase());
        fileInfo.setFileSize(getFileSize(size));

        return fileInfo;
    }

    public static priv.xin.xd.core.entity.File getFileInfo(File file, CodeEnum resourceType) {

        String fileName = file.getName();
        String ext = FilenameUtils.getExtension(fileName);
        long size = file.length();

        if (CodeEnum.RESOURCE_TYPE_SYSTEM.equals(resourceType)) {
            String baseName = FilenameUtils.getBaseName(fileName);
            String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
            if (StrUtil.isNotEmpty(ext)) {
                fileName = date + "." + ext;
            } else {
                fileName = date;
            }
        }

        priv.xin.xd.core.entity.File fileInfo = new priv.xin.xd.core.entity.File();
        fileInfo.setFileName(fileName);
        fileInfo.setFileSuffix(ext.toLowerCase());
        fileInfo.setFileSize(getFileSize(size));

        return fileInfo;
    }

    public static String getFileSize(long size) {
        String[] unitArr = new String[]{"B", "KB", "MB", "GB"};

        String fileSize = size + unitArr[0];

        for (int i = 0; i < unitArr.length; i++) {
            double pow = Math.pow(1024, i + 1);
            if (new BigDecimal(size).compareTo(new BigDecimal(pow)) < 0) {
                BigDecimal number = new BigDecimal(size / Math.pow(1024, i)).setScale(2, BigDecimal.ROUND_HALF_UP);
                fileSize = number.toString() + unitArr[i];
                break;
            }
        }

        return fileSize;
    }

    /**
     * 略缩图路径
     *
     * @param imagePath 图片路径
     * @return
     */
    public static String getFileThumbnailsPath(String imagePath) {
        String fullPath = FilenameUtils.getFullPath(imagePath);
        String baseName = FilenameUtils.getBaseName(imagePath);
        String ext = FilenameUtils.getExtension(imagePath);

        String imageThumbnailsName = fullPath + baseName + CommonConst.IMG_EXT;
        if (StrUtil.isNotEmpty(ext)) {
            imageThumbnailsName = imageThumbnailsName + "." + ext;
        }
        return imageThumbnailsName;
    }

    /**
     * 略缩图名称
     *
     * @param imageName 图片名称
     * @return
     */
    public static String getFileThumbnailsName(String imageName) {
        String baseName = FilenameUtils.getBaseName(imageName);
        String ext = FilenameUtils.getExtension(imageName);

        String imageThumbnailsName = baseName + CommonConst.IMG_EXT;
        if (StrUtil.isNotEmpty(ext)) {
            imageThumbnailsName = imageThumbnailsName + "." + ext;
        }
        return imageThumbnailsName;
    }

    /**
     * 获取文件类型
     *
     * @param fileSuffix
     * @return
     */
    public static CodeEnum getFileType(String fileSuffix) {
        if (StrUtil.isEmpty(fileSuffix)) {
            return CodeEnum.FILE_TYPE_OTHER;
        }
        FileTypeRedis fileTypeRedis = SpringUtil.getBean(FileTypeRedis.class);
        String fileType = fileTypeRedis.getFileType(fileSuffix);
        CodeEnum code = CodeUtil.getCode(CodeEnum.FILE_TYPE_OTHER.getGroup(), fileType);
        if (code == null) {
            return CodeEnum.FILE_TYPE_OTHER;
        }
        return code;
    }

    /**
     * 生成略缩图
     *
     * @param imageStream 图片流
     * @param scale       缩放比例
     * @param path        略缩图存放路径(带文件名) E:/aaa/bbb.jpg
     * @throws IOException
     */
    public static void createThumbnails(InputStream imageStream, double scale, String path) throws IOException {
        Thumbnails.of(imageStream).scale(scale).toFile(path);
    }

    /**
     * 生成略缩图
     *
     * @param imageStream 图片流
     * @param width       略缩图宽度
     * @param height      略缩图高度
     * @param path        略缩图存放路径(带文件名) E:/aaa/bbb.jpg
     * @throws IOException
     */
    public static void createThumbnails(InputStream imageStream, int width, int height, String path) throws IOException {
        Thumbnails.of(imageStream).width(width).height(height).toFile(path);
    }

}
