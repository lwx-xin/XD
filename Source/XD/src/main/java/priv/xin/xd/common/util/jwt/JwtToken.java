package priv.xin.xd.common.util.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 对token进行扩展
 *
 * @author ：lu
 * @date ：2021/11/25 10:54
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
