package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.core.dao.*;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.Code;
import priv.xin.xd.core.entity.Department;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.service.ISysCodeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/7/3 20:21
 */
@Service
public class SysCodeServiceImpl implements ISysCodeService {

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private UrlMapper urlMapper;

    @Resource
    private AuthMapper authMapper;

    @Override
    public Result codeList(List<String> groups) {
        if (ListUtil.isEmpty(groups)) {
            return new Result(false).message(MessageLevel.ERROR, Message.CODE_GROUP_UNKNOWN);
        }

        Map<String, List<Code>> codes = new HashMap<>();

        if (groups.contains("department")) {
            groups.remove("department");
            List<Department> departmentList = departmentMapper.queryAll(null);
            List<Code> departmentCodeList = null;
            if (ListUtil.isNotEmpty(departmentList)) {
                departmentCodeList = new ArrayList<>();
                for (Department department : departmentList) {
                    Code code = new Code();
                    code.setCodeName(department.getDepartmentName());
                    code.setCodeValue(department.getDepartmentId());
                    departmentCodeList.add(code);
                }
            }
            codes.put("department", departmentCodeList);
        }

        if (groups.contains("url")) {
            groups.remove("url");
            List<Url> urlList = urlMapper.queryAllRegexp(null);
            List<Code> urlCodeList = null;
            if (ListUtil.isNotEmpty(urlList)) {
                urlCodeList = new ArrayList<>();
                for (Url url : urlList) {
                    Code code = new Code();
                    code.setCodeName(url.getUrlPath());
                    code.setCodeValue(url.getUrlId());
                    urlCodeList.add(code);
                }
            }
            codes.put("url", urlCodeList);
        }

        if (groups.contains("auth")) {
            groups.remove("auth");
            List<Auth> authList = authMapper.queryAll(null);
            List<Code> authCodeList = null;
            if (ListUtil.isNotEmpty(authList)) {
                authCodeList = new ArrayList<>();
                for (Auth auth : authList) {
                    Code code = new Code();
                    code.setCodeName(auth.getAuthName());
                    code.setCodeValue(auth.getAuthId());
                    authCodeList.add(code);
                }
            }
            codes.put("auth", authCodeList);
        }

        for (String group : groups) {
            Code codeQuery = new Code();
            codeQuery.setCodeGroup(group);
            codes.put(group, codeMapper.queryAll(codeQuery));
        }

        return new Result(true).data("codes", codes);
    }

}
