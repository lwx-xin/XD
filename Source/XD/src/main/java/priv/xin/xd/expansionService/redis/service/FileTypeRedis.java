package priv.xin.xd.expansionService.redis.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.core.entity.FileType;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存文件类型-FileType
 *
 * @author ：lu
 * @date ：2021/10/4 15:48
 */
@Component
public class FileTypeRedis {

    /**
     * 操作步骤日志
     */
    public static final String FILE_TYPE_TABLE = "S_FILE_TYPE";

    @Resource
    private RedisTemplate redisTemplate;

    public void initFileType(List<FileType> fileTypeList) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        for (FileType fileType : fileTypeList) {
            String fileSuffix = fileType.getFileSuffix();
            hashOperations.put(FILE_TYPE_TABLE, fileSuffix, JsonUtil.toJson(fileType));
        }
    }

    public String getFileType(String fileSuffix) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object o = hashOperations.get(FILE_TYPE_TABLE, fileSuffix);
        if (o != null) {
            FileType fileType = JsonUtil.toEntity(o.toString(), FileType.class);
            return fileType.getFileType();
        }
        return "";
    }
}
