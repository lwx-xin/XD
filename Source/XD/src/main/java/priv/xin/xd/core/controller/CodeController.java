package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.core.entity.Code;
import priv.xin.xd.core.service.ISysCodeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/7/3 18:49
 */
@RestController
@RequestMapping("sys/code")
public class CodeController {

    @Resource
    private ISysCodeService sysCodeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result code(String groups) {
        List<String> groupList = JsonUtil.toList(groups, String.class);
        Result result = sysCodeService.codeList(groupList);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.CODE_QUERY_ERROR);
        }
        Map<String, List<Code>> codes = (Map<String, List<Code>>) result.getData("codes");
        return result.clearData()
                .codes(codes)
                .message(MessageLevel.INFO, Message.CODE_QUERY_SUCCESS);
    }
}
