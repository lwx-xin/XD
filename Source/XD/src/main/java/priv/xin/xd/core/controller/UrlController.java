package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysUrlService;
import priv.xin.xd.core.entity.ex.UrlEx;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/2 17:29
 */
@RestController
@RequestMapping("sys/url")
public class UrlController {

    @Resource
    private ISysUrlService sysUrlService;

    /**
     * @param urlEx 查询条件+
     * @param page  分页/排序信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(UrlEx urlEx, Page page) {
        Result result = sysUrlService.queryListLimit(urlEx, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_QUERY_ERROR);
        }
        Object urlList = result.getData("urlList");
        //总条数
        Object totalNumber = result.getData("count");
        return result.clearData()
                .data("urlList", urlList)
                .data("totalNumber", totalNumber)
                .message(MessageLevel.INFO, Message.URL_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{urlId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("urlId") String urlId) {
        Result result = sysUrlService.queryDetail(urlId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_QUERY_ERROR);
        }
        Object urlEx = result.getData("urlDetail");
        return result.clearData()
                .data("urlDetail", urlEx)
                .message(MessageLevel.INFO, Message.URL_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{urlId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("urlId") String urlId, @RequestBody UrlEx urlEx) {
        Result result = sysUrlService.updateUrlDetail(urlId, urlEx);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.URL_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(@RequestBody UrlEx urlEx) {
        Result result = sysUrlService.insertUrlDetail(urlEx);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.URL_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{urlId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("urlId") String urlId) {
        Result result = sysUrlService.deleteByUrlId(urlId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.URL_DELETE_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.URL_DELETE_SUCCESS);
    }

}
