package priv.xin.xd.expansionService.quartz.listener;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：lu
 * @date ：2021/10/28 19:20
 */
public class QuartzCustomSchedulerListener implements SchedulerListener {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzCustomSchedulerListener.class);

    /**
     * 任务被部署时被执行
     *
     * @param trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被部署", trigger.toString());
    }

    /**
     * 任务被卸载时被执行
     *
     * @param triggerKey
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被卸载", triggerKey.toString());
    }

    /**
     * 任务完成了它的使命，光荣退休时被执行
     *
     * @param trigger
     */
    @Override
    public void triggerFinalized(Trigger trigger) {
        LOG.info("SchedulerListener监听器：任务 [{}] 结束", trigger.toString());
    }

    /**
     * 一个触发器被暂停时被执行
     *
     * @param triggerKey
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被暂停", triggerKey.toString());
    }

    /**
     * 所在组的全部触发器被停止时被执行
     *
     * @param triggerGroup
     */
    @Override
    public void triggersPaused(String triggerGroup) {
        LOG.info("SchedulerListener监听器：任务组 [{}] 被暂停", triggerGroup);

    }

    /**
     * 一个触发器被恢复时被执行
     *
     * @param triggerKey
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被恢复", triggerKey.toString());
    }

    /**
     * 所在组的全部触发器被回复时被执行
     *
     * @param triggerGroup
     */
    @Override
    public void triggersResumed(String triggerGroup) {
        LOG.info("SchedulerListener监听器：任务组 [{}] 被恢复", triggerGroup);
    }

    /**
     * 一个JobDetail被动态添加进来
     *
     * @param jobDetail
     */
    @Override
    public void jobAdded(JobDetail jobDetail) {

    }

    /**
     * 删除时被执行
     *
     * @param jobKey
     */
    @Override
    public void jobDeleted(JobKey jobKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被删除", jobKey.toString());
    }

    /**
     * 暂停时被执行
     *
     * @param jobKey
     */
    @Override
    public void jobPaused(JobKey jobKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被暂停", jobKey.toString());
    }

    /**
     * 一组任务被暂定时执行
     *
     * @param jobGroup
     */
    @Override
    public void jobsPaused(String jobGroup) {
        LOG.info("SchedulerListener监听器：任务组 [{}] 被暂停", jobGroup);
    }

    /**
     * 恢复时被执行
     *
     * @param jobKey
     */
    @Override
    public void jobResumed(JobKey jobKey) {
        LOG.info("SchedulerListener监听器：任务 [{}] 被恢复", jobKey.toString());
    }

    /**
     * 一组被恢复时执行
     *
     * @param triggerGroup
     */
    @Override
    public void jobsResumed(String triggerGroup) {
        LOG.info("SchedulerListener监听器：任务组 [{}] 被恢复", triggerGroup);
    }

    /**
     * 出现异常时执行
     *
     * @param jobGroup
     * @param e
     */
    @Override
    public void schedulerError(String jobGroup, SchedulerException e) {
        LOG.error("SchedulerListener监听器：jobGroup [{}] deal error: {}", jobGroup, getStackTraceMsg(e.getStackTrace()));
    }

    /**
     * scheduler被设为standBy等候模式时被执行
     */
    @Override
    public void schedulerInStandbyMode() {

    }

    /**
     * scheduler启动时被执行
     */
    @Override
    public void schedulerStarted() {

    }

    /**
     * scheduler正在启动时被执行
     */
    @Override
    public void schedulerStarting() {

    }

    /**
     * scheduler关闭时被执行
     */
    @Override
    public void schedulerShutdown() {

    }

    /**
     * scheduler正在关闭时被执行
     */
    @Override
    public void schedulerShuttingdown() {

    }

    /**
     * scheduler中所有数据包括jobs, triggers和calendars都被清空时被执行
     */
    @Override
    public void schedulingDataCleared() {

    }

    /**
     * 日志栈输出
     *
     * @param stackTraceElements
     * @return
     */
    public String getStackTraceMsg(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElem : stackTraceElements) {
            sb.append(stackTraceElem.toString() + "\n");
        }
        return sb.toString();
    }
}
