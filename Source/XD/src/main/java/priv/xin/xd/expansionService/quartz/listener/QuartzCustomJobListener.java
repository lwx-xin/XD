package priv.xin.xd.expansionService.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：lu
 * @date ：2021/10/28 20:19
 */
public class QuartzCustomJobListener implements JobListener {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzCustomJobListener.class);

    @Override//相当于为我们的监听器命名
    public String getName() {
        return "myJobListener";
    }

    /**
     * (1)
     * 任务执行之前执行
     * Called by the Scheduler when a JobDetail is about to be executed (an associated Trigger has occurred).
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        LOG.info(getName() + "触发对" + context.getJobDetail().getJobClass() + "的开始执行的监听工作，这里可以完成任务前的一些资源准备工作或日志记录");
    }

    /**
     * (2)
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * Called by the Scheduler when a JobDetail was about to be executed (an associated Trigger has occurred),
     * but a TriggerListener vetoed it's execution.
     */
    @Override//“否决JobDetail”是在Triiger被其相应的监听器监听时才具备的能力
    public void jobExecutionVetoed(JobExecutionContext context) {
        LOG.info("被否决执行了，可以做些日志记录。");
    }

    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * Called by the Scheduler after a JobDetail has been executed, and be for the associated Trigger's triggered(xx) method has been called.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        LOG.info(getName() + "触发对" + context.getJobDetail().getJobClass() + "结束执行的监听工作，这里可以进行资源销毁工作或做一些新闻扒取结果的统计工作");

    }

}
