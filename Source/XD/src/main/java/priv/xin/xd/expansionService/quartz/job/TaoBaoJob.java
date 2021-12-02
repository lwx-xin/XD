package priv.xin.xd.expansionService.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import priv.xin.xd.common.util.LoggerUtil;
import priv.xin.xd.common.util.SpringUtil;
import priv.xin.xd.expansionService.crawler.taobao.TaobaoProcessor;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/10/24 12:34
 */
@Component
public class TaoBaoJob extends QuartzJobBean {

    /**
     * 序号 说明   是否必填  允许填写的值			    允许的通配符
     * 1    秒     是        0-59    			, - * /
     * 2    分     是  	    0-59    			, - * /
     * 3    小时   是        0-23    			, - * /
     * 4    日     是        1-31    			, - * ? / L W
     * 5    月     是        1-12 or JAN-DEC   	, - * /
     * 6    周     是        1-7 or SUN-SAT   	, - * ? / L #
     * 7    年     否        empty 或 1970-2099	, - * /
     */
    public static final String jobName = "taobao_job";
    public static final String jobGroupName = "taobao_job_group";
    public static final String triggerName = "taobao_trigger";
    public static final String triggerGroupName = "taobao_trigger_group";
    public static final String cron = "0 19 20 * * ? *"; // 每天3点触发

    @Resource
    private TaobaoProcessor taobaoProcessor;

    private void loadBean() {
        if (taobaoProcessor == null) {
            taobaoProcessor = SpringUtil.getBean(TaobaoProcessor.class);
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        loadBean();
        LoggerUtil.info("开始:" + System.currentTimeMillis(), this.getClass());
        // 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // TODO 业务
        taobaoProcessor.startProcessor();
        LoggerUtil.info("结束:" + System.currentTimeMillis(), this.getClass());
    }
}
