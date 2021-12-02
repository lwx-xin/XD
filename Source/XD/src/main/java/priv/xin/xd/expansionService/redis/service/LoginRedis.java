package priv.xin.xd.expansionService.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import priv.xin.xd.common.util.NumberUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ：lu
 * @date ：2021/9/26 13:27
 */
@Component
public class LoginRedis {

    // 用户登录记录
    public static final String SHIRO_LOGIN = "SHIRO_LOGIN_";
    // 用户登录记录-登录次数
    public static final String SHIRO_LOGIN_COUNT = "COUNT";
    // 用户登录记录-登录失败账号锁定的时间(毫秒)
    public static final String SHIRO_LOGIN_LOCK_TIME = "LOCK_TIME";

    // 最多重试次数
    public static final int LOGIN_MAX_COUNT = 5;
    // 锁定的时长(毫秒)-1h
    public static final long LOCK_TIME = 60 * 60 * 1000;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 记录登录失败的次数以及锁定账号的时间
     *
     * @param userId
     * @return 剩余的次数
     */
    public int loginError(String userId) {
        String key = SHIRO_LOGIN + userId;

        // 失败次数
        Long nowCount = redisTemplate.opsForHash().increment(key, SHIRO_LOGIN_COUNT, 1);
        // 剩余次数
        BigDecimal remainingTimes = NumberUtil.sub(LOGIN_MAX_COUNT, nowCount);
        if (remainingTimes.compareTo(BigDecimal.ZERO) <= 0) {
            redisTemplate.opsForHash().put(key, SHIRO_LOGIN_LOCK_TIME, System.currentTimeMillis());
        }

        return remainingTimes.intValue();
    }

    /**
     * 登录成功
     *
     * @param userId
     */
    public void loginSuccess(String userId) {
        String key = SHIRO_LOGIN + userId;
        redisTemplate.delete(key);
    }

    /**
     * 判断当前用户是否被锁定
     *
     * @param userId
     * @return 解除锁定的时间
     */
    public Long isLock(String userId) {
        String key = SHIRO_LOGIN + userId;
        Object lockTime = redisTemplate.opsForHash().get(key, SHIRO_LOGIN_LOCK_TIME);
        if (lockTime == null) {
            return 0L;
        } else {
            // 解除锁定的时间
            BigDecimal time = NumberUtil.add(lockTime, LOCK_TIME);
            if (time.compareTo(new BigDecimal(System.currentTimeMillis())) >= 0) {
                return time.longValue();
            } else {
                return 0L;
            }
        }
    }
}
