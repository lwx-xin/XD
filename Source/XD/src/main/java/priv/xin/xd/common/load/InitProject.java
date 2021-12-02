package priv.xin.xd.common.load;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import priv.xin.xd.core.service.ISysInitProjectService;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/4 13:26
 */
@Component
public class InitProject implements ApplicationRunner {

    @Resource
    private ISysInitProjectService initProjectService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化项目mast数据
//        initProjectService.addCodeDB();
        initProjectService.addRequestUrl();
//        initProjectService.addUser();

        // 缓存数据
        initProjectService.addRedis_fileType();
    }
}
