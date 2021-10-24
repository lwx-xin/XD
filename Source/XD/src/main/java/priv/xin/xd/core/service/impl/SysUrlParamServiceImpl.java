package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysUrlParamService;
import priv.xin.xd.core.dao.UrlParamMapper;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.ex.UrlParamEx;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：lu
 * @date ：2021/8/25 19:01
 */
@Service
public class SysUrlParamServiceImpl implements ISysUrlParamService {

    @Resource
    private UrlParamMapper urlParamMapper;

    @Override
    public Result queryAllDetail() {
        List<UrlParamEx> urlParams = urlParamMapper.queryAllDetail();

        List<UrlParamEx> urlParamList = urlParams.stream().map(urlParamEx -> {
            UrlParamEx urlParam = new UrlParamEx();
            urlParam.setUrlParamName(urlParamEx.getUrlParamName());
            urlParam.setUrlParamValue(urlParamEx.getUrlParamValue());
            urlParam.setUrlParamRequired(urlParamEx.getUrlParamRequired());
            urlParam.setUrlParamRemark(urlParamEx.getUrlParamRemark());
            urlParam.setUrlParamErrHint(urlParamEx.getUrlParamErrHint());

            Url url = new Url();
            url.setUrlPath(urlParamEx.getUrl().getUrlPath());
            url.setUrlType(urlParamEx.getUrl().getUrlType());

            urlParam.setUrl(url);
            return urlParam;
        }).collect(Collectors.toList());

        return new Result(true).data("urlParamList", urlParamList);
    }
}
