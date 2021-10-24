package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/2 17:41
 */
public class UserEx extends User implements Serializable {
    private static final long serialVersionUID = 5696297069190310382L;

    /**
     * 删除标识名称
     */
    private String userDelFlagName;
    /**
     * 平台名称
     */
    private String userPlatformName;

    /**
     * 职位
     */
    private List<PositionEx> positionExList;

    /**
     * 职位 例如：xx部-员工,xx部-经理
     */
    private String positionDetailName;

    public String getUserDelFlagName() {
        return userDelFlagName;
    }

    public void setUserDelFlagName(String userDelFlagName) {
        this.userDelFlagName = userDelFlagName;
    }

    public List<PositionEx> getPositionExList() {
        return positionExList;
    }

    public void setPositionExList(List<PositionEx> positionExList) {
        this.positionExList = positionExList;
    }

    public String getPositionDetailName() {
        return positionDetailName;
    }

    public void setPositionDetailName(String positionDetailName) {
        this.positionDetailName = positionDetailName;
    }

    public String getUserPlatformName() {
        return userPlatformName;
    }

    public void setUserPlatformName(String userPlatformName) {
        this.userPlatformName = userPlatformName;
    }

}
