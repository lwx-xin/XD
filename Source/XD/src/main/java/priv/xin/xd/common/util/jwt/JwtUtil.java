package priv.xin.xd.common.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import priv.xin.xd.common.util.CookieUtil;
import priv.xin.xd.common.util.SpringUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.dao.UrlMapper;
import priv.xin.xd.expansionService.redis.service.JwtTokenRedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author ：lu
 * @date ：2021/11/25 10:08
 */
public class JwtUtil {

    // 过期时间24小时(86400000毫秒)
    public static final long EXPIRE_TIME = 60 * 60 * 24 * 1000;
    // 密钥
    private static final String SECRET = "XD";

    private static JwtTokenRedis jwtTokenRedis;

    public static void loadBean() {
        if (jwtTokenRedis == null) {
            jwtTokenRedis = SpringUtil.getBean(JwtTokenRedis.class);
        }
    }

    /**
     * 生成 token
     */
    public static String createToken(String userId) {
        try {
            Date expireTime = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            String jwtId = StrUtil.getUUID();

            // 附带username信息
            String token = JWT.create()
                    .withJWTId(jwtId)
                    .withClaim("userId", userId)
                    //到期时间
                    .withExpiresAt(expireTime)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);

            loadBean();
            jwtTokenRedis.saveToken(userId, token);
            return token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token, String userId)
            throws JWTVerificationException {
        boolean verify = false;
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            return verify;
        }
        try {

            loadBean();
            String redisToken = jwtTokenRedis.getToken(userId);
            if (StrUtil.isEmpty(redisToken)) {
                throw new TokenExpiredException("令牌失效");
            } else if (!redisToken.equals(token)) {
                throw new JWTVerificationException("账号在其他地方登录，请重新登录");
            }

            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            //验证 token
            verifier.verify(token);
            verify = true;
        } catch (JWTVerificationException e) {
            throw e;
        }
        return verify;
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从request中获取Token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("jwtToken");
        if (StrUtil.isEmpty(jwtToken)) {
            jwtToken = CookieUtil.getCookie("jwtToken", request);
        }
        return jwtToken;
    }

}
