package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.UrlParam;

/**
 * @author ：lu
 * @date ：2021/8/25 18:52
 */
public class UrlParamEx extends UrlParam {

    private Url url;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
