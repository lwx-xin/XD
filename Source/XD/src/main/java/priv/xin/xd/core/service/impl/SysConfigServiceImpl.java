package priv.xin.xd.core.service.impl;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.stereotype.Service;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.core.service.ISysConfigService;
import priv.xin.xd.core.dao.ConfigMapper;
import priv.xin.xd.core.entity.Config;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/8/21 12:37
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public Result queryAll() {
        List<Config> configs = configMapper.queryAll();
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Config.class, "configId", "configKey", "configValue");
        List<Map> configList = JsonUtil.toList(configs, Map.class, filter);
        return new Result(true).data("configList", configList);
    }
}
