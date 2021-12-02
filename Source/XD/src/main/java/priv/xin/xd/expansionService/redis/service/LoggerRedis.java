package priv.xin.xd.expansionService.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import priv.xin.xd.common.util.*;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author ：lu
 * @date ：2021/9/24 16:36
 */
@Component
public class LoggerRedis {

    /**
     * 操作步骤日志
     */
    public static final String OPERATION_STEPS_LOG = "OPERATION_STEPS_";

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 添加当前登录用户的日志信息
     *
     * @param userId 操作者(登录用户)
     * @param log    日志内容
     */
    public void addLog(String userId, String log) {
        if (StrUtil.isNotEmpty(userId)) {
            String key = OPERATION_STEPS_LOG + userId;
            redisTemplate.opsForList().rightPush(key, log);
        }
    }

    /**
     * 获取全部用户的日志记录
     *
     * @return
     */
    public Map<String, List<String>> getLog() {
        Set keys = redisTemplate.keys(OPERATION_STEPS_LOG + "*");
        Map<String, List<String>> logMap = new HashMap<>();
        if (ListUtil.isNotEmpty(keys)) {
            for (Object key : keys) {
                List<String> logList = new ArrayList<>();
                Object log = redisTemplate.opsForList().leftPop(key);
                while (log != null) {
                    logList.add(log.toString());
                    log = redisTemplate.opsForList().leftPop(key);
                }
                String userId = key.toString().replace(OPERATION_STEPS_LOG, "");
                logMap.put(userId, logList);
            }
        }
        return logMap;
    }

    /**
     * 将日志写入文件
     *
     * @param logFilePath
     * @param logList
     * @return
     */
    public boolean writeLog(String logFilePath, List<String> logList) {
        try {
            FileWriter writer = new FileWriter(logFilePath, true);
            for (String log : logList) {
                writer.write("\n");
                writer.write(log.toString());
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
