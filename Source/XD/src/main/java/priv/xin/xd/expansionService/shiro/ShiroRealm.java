package priv.xin.xd.expansionService.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.util.ListUtil;
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

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserPositionMapper userPositionMapper;

    @Resource
    private AuthMapper authMapper;

    @Override
    public void setName(String name) {
        super.setName("XD_CustomerRealm");
    }

    /**
     * 提供账户信息返回认证信息（用户的角色信息集合）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String userNumber = (String) token.getPrincipal();
        User user = new User();
        user.setUserNumber(userNumber);
        List<User> users = userMapper.queryAll(user);

        if (ListUtil.isEmpty(users)) {
            throw new UnknownAccountException("账号不存在！");
        }

        user = users.get(0);

        // 用户锁定状态
        String userDelFlag = user.getUserDelFlag();
        if (userDelFlag != null && CodeEnum.DEL_FLAG_0.getValue().equals(userDelFlag)) {
            throw new LockedAccountException("帐号已被锁定，禁止登录！");
        }

        // principal参数使用用户Id，方便动态刷新用户权限
        return new SimpleAuthenticationInfo(
                user.getUserId(),
                user.getUserPwd(),
                ByteSource.Util.bytes(userNumber),
                getName()
        );
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限（角色的权限信息集合）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 权限信息对象info,用来存放查出的用户的所有的角色（position）及权限（auth）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String userId = (String) SecurityUtils.getSubject().getPrincipal();

        // 赋予角色
        UserPosition userPosition = new UserPosition();
        userPosition.setUserId(userId);
        List<UserPosition> userPositionList = userPositionMapper.queryAll(userPosition);
        Set<String> positionIdSet = new HashSet<>();
        if (ListUtil.isNotEmpty(userPositionList)){
            positionIdSet.addAll(userPositionList.stream().map(UserPosition::getPositionId).collect(Collectors.toSet()));
        }
        info.setRoles(positionIdSet);

        // 赋予权限
        List<Auth> authList = authMapper.queryAuthByUser(userId);
        Set<String> authIdSet = new HashSet<>();
        if (ListUtil.isNotEmpty(authList)){
            authIdSet.addAll(authList.stream().map(Auth::getAuthId).collect(Collectors.toSet()));
        }
        info.setStringPermissions(authIdSet);

        return info;
    }
}


