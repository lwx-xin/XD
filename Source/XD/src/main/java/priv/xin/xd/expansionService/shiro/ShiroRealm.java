package priv.xin.xd.expansionService.shiro;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.common.util.jwt.JwtToken;
import priv.xin.xd.common.util.jwt.JwtUtil;
import priv.xin.xd.core.dao.AuthMapper;
import priv.xin.xd.core.dao.UserMapper;
import priv.xin.xd.core.dao.UserPositionMapper;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.User;
import priv.xin.xd.core.entity.UserPosition;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserPositionMapper userPositionMapper;

    @Resource
    private AuthMapper authMapper;

    @Override
    public void setName(String name) {
        super.setName("XD_ShiroRealm");
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
//        return super.supports(token);
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwtToken = StrUtil.format(token.getCredentials());

        // 解密获得userId，用于和数据库进行对比
        String userId = JwtUtil.getUserId(jwtToken);
        if (StrUtil.isEmpty(userId)) {
            throw new AuthenticationException("token认证失败！");
        } else {
            boolean verify = false;
            try {
                verify = JwtUtil.verify(jwtToken, userId);
            } catch (JWTVerificationException e) {
                throw e;
            }
            if (!verify) {
                throw new AuthenticationException("token认证失败！");
            }
        }

        User user = userMapper.selectByPrimaryKey(userId);
        // 用户锁定状态
        String userDelFlag = user.getUserDelFlag();
        if (userDelFlag != null && CodeEnum.DEL_FLAG_0.getValue().equals(userDelFlag)) {
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }

        return new SimpleAuthenticationInfo(jwtToken, jwtToken, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        logger.info("======= 权限认证开始 =======");
        String userId = JwtUtil.getUserId(principals.toString());

        // 权限信息对象info,用来存放查出的用户的所有的角色（position）及权限（auth）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 赋予角色
        UserPosition userPosition = new UserPosition();
        userPosition.setUserId(userId);
        List<UserPosition> userPositionList = userPositionMapper.queryAll(userPosition);
        Set<String> positionIdSet = new HashSet<>();
        if (ListUtil.isNotEmpty(userPositionList)) {
            positionIdSet.addAll(userPositionList.stream().map(UserPosition::getPositionId).collect(Collectors.toSet()));
        }
        info.setRoles(positionIdSet);

        // 赋予权限
        List<Auth> authList = authMapper.queryAuthByUser(userId);
        Set<String> authIdSet = new HashSet<>();
        if (ListUtil.isNotEmpty(authList)) {
            authIdSet.addAll(authList.stream().map(Auth::getAuthId).collect(Collectors.toSet()));
        }
        info.setStringPermissions(authIdSet);

//        logger.info("======= 权限认证结束 =======");
        return info;
    }

}


