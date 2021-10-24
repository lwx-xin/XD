package priv.xin.xd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.xin.xd.expansionService.crawler.taobao.TaobaoProcessor;
import priv.xin.xd.expansionService.job.service.RecordLogJob;
import priv.xin.xd.expansionService.redis.service.ShiroLogin;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XdApplicationTests {

    @Test
    public void contextLoads() {
        new TaobaoProcessor().startProcessor();
    }

}
