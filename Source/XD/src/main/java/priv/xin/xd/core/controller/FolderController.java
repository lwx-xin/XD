package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.entity.FolderOrFile;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/10/1 14:59
 */
@RestController
@RequestMapping("sys/folder")
public class FolderController {

    @Resource
    private ISysFolderService sysFolderService;

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public Result list(FolderOrFile resource, Page page) {
        Result result = sysFolderService.getResources(resource, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.RESOURCE_QUERY_ERROR);
        }
        Object resourceList = result.getData("resourceList");
        return result.clearData()
                .data("resourceList", resourceList)
                .message(MessageLevel.INFO, Message.RESOURCE_QUERY_SUCCESS);
    }

}
