package priv.xin.xd.core.service;

import priv.xin.xd.common.result.Result;

import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/3 20:20
 */
public interface ISysCodeService {

    /**
     * 根据分组获取code列表
     * @param groups
     * @return data: codes
     */
    public Result codeList(List<String> groups);

}
