package priv.xin.xd.expansionService.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：lu
 * @date ：2021/10/28 20:13
 */
public class QuartzCustomTriggerListener implements TriggerListener {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzCustomTriggerListener.class);

    @Override
    public String getName() {
        return "QuartzCustomTriggerListener";
    }

    /**
     * (1)
     * Trigger被激发 它关联的job即将被运行
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed.
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        LOG.info(" Trigger 被触发了，此时Job 上的 execute() 方法将要被执行");
    }

    /**
     * (2)
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
//        LOG.info("发现此次Job的相关资源准备存在问题，不便展开任务，返回true表示否决此次任务执行");
        return false;
    }

    /**
     * (3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     * 那么有的触发器就有可能超时，错过这一轮的触发。
     * Called by the Scheduler when a Trigger has misfired.
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        LOG.info("当前Trigger触发错过了");
    }

    /**
     * (4) 任务完成时触发
     * Called by the Scheduler when a Trigger has fired, it's associated JobDetail has been executed
     * and it's triggered(xx) method has been called.
     */
    @Override//这是2.+版本的配置，差别在于将triggerInstructionCode从整型改成了枚举类型
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        LOG.info("Trigger 被触发并且完成了 Job 的执行,此方法被调用");
    }

}
