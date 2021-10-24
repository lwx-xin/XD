package priv.xin.xd.common.util;

import org.apache.shiro.SecurityUtils;
import priv.xin.xd.common.properties.DataProperties;

/**
 * @author ：lu
 * @date ：2021/5/31 19:13
 */
public class ShiroUtil {

    private static DataProperties dataProperties;

    /**
     * 獲取登录的用戶id
     *
     * @return 用戶id
     */
    public static String getUserId() {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if (StrUtil.isEmpty(userId)) {
            throw new RuntimeException("用户未登录");
        }
        return userId;
    }

    public static boolean isAdmin() {
        if (dataProperties == null) {
            dataProperties = SpringUtil.getBean(DataProperties.class);
        }
        String adminAuthId = dataProperties.getAdminAuthId();
        if (StrUtil.isEmpty(adminAuthId)) {
            return false;
        }
        return SecurityUtils.getSubject().isPermitted(adminAuthId);
    }

}
