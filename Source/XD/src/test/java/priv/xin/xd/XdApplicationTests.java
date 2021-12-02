package priv.xin.xd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.expansionService.excel.dao.ExcelMapper;
import priv.xin.xd.expansionService.excel.entity.TableInfo;
import priv.xin.xd.expansionService.redis.service.JwtTokenRedis;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XdApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
//        Long expire = redisTemplate.opsForValue().getOperations().getExpire("USER_JWT_TOKEN:");
        Set<String> keySet = redisTemplate.keys("jwt_token:*");
        for (String key : keySet) {
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.MINUTES);
            System.err.println(key + "-" + expire);
        }
    }


}
