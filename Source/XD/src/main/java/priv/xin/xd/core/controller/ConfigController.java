package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysConfigService;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/8/19 9:56
 */
@RestController
@RequestMapping("sys/config")
public class ConfigController {

    @Resource
    private ISysConfigService sysConfigServicel;

    /**
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list() {
        Result result = sysConfigServicel.queryAll();
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.CONFIG_QUERY_ERROR);
        }
        Object configList = result.getData("configList");
        return result.clearData()
                .data("configList", configList)
                .message(MessageLevel.INFO, Message.CONFIG_QUERY_ERROR);
    }

}
