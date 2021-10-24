package priv.xin.xd.expansionService.crawler.taobao;

import org.springframework.stereotype.Repository;
import priv.xin.xd.common.util.LoggerUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/10/16 15:58
 */
@Repository
public class TaobaoPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
//            LoggerUtil.error(entry.getKey() + ":\t" + entry.getValue(), this.getClass());
            System.err.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}