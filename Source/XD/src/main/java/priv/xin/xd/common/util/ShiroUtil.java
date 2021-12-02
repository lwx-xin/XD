package priv.xin.xd.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import priv.xin.xd.common.entity.SessionConst;
import priv.xin.xd.common.properties.DataProperties;
import priv.xin.xd.common.util.jwt.JwtUtil;
import priv.xin.xd.core.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：lu
 * @date ：2021/5/31 19:13
 */
public class ShiroUtil {

    private static DataProperties dataProperties;

    public static String getUserId() {
        HttpServletRequest request = SpringUtil.getRequest();
        if (request != null) {
            String token = JwtUtil.getToken(request);
            String userId = JwtUtil.getUserId(token);
            return userId;
        }
        return null;
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
