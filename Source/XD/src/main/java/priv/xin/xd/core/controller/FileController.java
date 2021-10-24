package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysFileService;
import priv.xin.xd.core.entity.File;
import priv.xin.xd.core.entity.ex.FileEx;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：lu
 * @date ：2021/9/24 14:33
 */
@RestController
@RequestMapping("sys/file")
public class FileController {

    @Resource
    private ISysFileService sysFileService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(FileEx fileEx, Page page) {
        Result result = sysFileService.queryListLimit(fileEx, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_QUERY_ERROR);
        }
        Object fileList = result.getData("fileList");
//        //总条数
        Object totalNumber = result.getData("count");
        return result.clearData()
                .data("fileList", fileList)
                .data("totalNumber", totalNumber)
                .message(MessageLevel.INFO, Message.FILE_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("fileId") String fileId) {
        Result result = sysFileService.queryDetail(fileId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_QUERY_ERROR);
        }
        Object fileDetail = result.getData("fileDetail");
        return result.clearData()
                .data("fileDetail", fileDetail)
                .message(MessageLevel.INFO, Message.FILE_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{fileId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("fileId") String fileId, @RequestBody File file) {
        Result result = sysFileService.updateFileDetail(fileId, file);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.FILE_EDIT_SUCCESS);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("files[]") MultipartFile[] files) {
        Result result = sysFileService.uploadFile(files);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_UPLOAD_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.FILE_UPLOAD_SUCCESS);
    }

    @RequestMapping(value = "/{fileId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("fileId") String fileId) {
        Result result = sysFileService.deleteByFileId(fileId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_DELETE_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.FILE_DELETE_SUCCESS);
    }

    /**
     * 获取图片文件内容
     */
    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        sysFileService.downloadFile(fileId, response);
    }

    /**
     * 获取图片文件内容
     */
    @RequestMapping(value = "/image/content/{fileId}", method = RequestMethod.GET)
    public void getImageFileContent(@PathVariable("fileId") String fileId, String thumbnails, HttpServletRequest request, HttpServletResponse response) {
        boolean isThumbnails = false;
        if (StrUtil.isNotEmpty(thumbnails)) {
            isThumbnails = true;
        }
        sysFileService.getImageContent(fileId, isThumbnails, response);
    }

    /**
     * 获取视频流
     *
     * @param request
     * @param response
     */
    @SuppressWarnings("resource")
    @RequestMapping(value = "/video/content/{fileId}", method = RequestMethod.GET)
    public void getVideo(@PathVariable("fileId") String fileId, String thumbnails,
                         HttpServletRequest request, HttpServletResponse response) {
        sysFileService.getVideoContent(fileId, request, response);
    }

}
