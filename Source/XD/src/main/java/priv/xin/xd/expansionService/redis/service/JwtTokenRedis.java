package priv.xin.xd.expansionService.redis.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.common.util.jwt.JwtUtil;
import priv.xin.xd.core.entity.FileType;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ：lu
 * @date ：2021/11/26 16:56
 */
@Component
public class JwtTokenRedis {

    /**
     * redis文件夹
     */
    private static final String FOLDER_NAME = "USER_JWT_TOKEN";

    @Resource
    private RedisTemplate redisTemplate;

    public void saveToken(String userId, String token) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(FOLDER_NAME + ":" + userId, token, JwtUtil.EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public boolean removeToken(String userId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return redisTemplate.delete(FOLDER_NAME + ":" + userId);
    }

    public String getToken(String userId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(FOLDER_NAME + ":" + userId);
        if (o != null) {
            return StrUtil.format(o);
        }
        return "";
    }

}
