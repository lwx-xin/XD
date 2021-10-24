package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysUrlParamService;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/2 17:29
 */
@RestController
@RequestMapping("sys/url/param")
public class UrlParamController {

    @Resource
    private ISysUrlParamService sysUrlParamService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result list() {
        Result result = sysUrlParamService.queryAllDetail();
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_PARAM_QUERY_ERROR);
        }
        Object urlParamList = result.getData("urlParamList");
        return result.clearData()
                .data("urlParamList", urlParamList)
                .message(MessageLevel.INFO, Message.URL_PARAM_QUERY_SUCCESS);
    }

}
