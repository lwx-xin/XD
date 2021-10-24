package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.service.ISysFileTypeService;
import priv.xin.xd.core.entity.FileType;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/10/3 11:57
 */
@RestController
@RequestMapping("sys/fileType")
public class FileTypeController {

    @Resource
    private ISysFileTypeService sysFileTypeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(FileType fileType, Page page) {
        Result result = sysFileTypeService.queryListLimit(fileType, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_TYPE_QUERY_ERROR);
        }
        Object fileTypeList = result.getData("fileTypeList");
//        //总条数
        Object totalNumber = result.getData("count");
        return result.clearData()
                .data("fileTypeList", fileTypeList)
                .data("totalNumber", totalNumber)
                .message(MessageLevel.INFO, Message.FILE_TYPE_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{fileSuffix}", method = RequestMethod.PUT)
    public Result update(@PathVariable("fileSuffix") String fileSuffix, @RequestBody FileType fileType) {
        Result result = sysFileTypeService.updateFileTypeDetail(fileSuffix, fileType);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.FILE_TYPE_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.FILE_TYPE_EDIT_SUCCESS);
    }
}
