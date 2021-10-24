package priv.xin.xd.expansionService.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring容器初始化完成后启动job定时任务
 * <p>
 * ContextRefreshedEvent 事件会在Spring容器初始化完成会触发该事件
 *
 * @author ：lu
 * @date ：2021/9/28 19:04
 */
@Component
public class QuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private MyQuartzScheduler myQuartzScheduler;

    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //防止重复触发
        if (event.getApplicationContext().getParent() == null) { //root application context 没有parent，他就是老大.
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            try {
                myQuartzScheduler.startJob();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始注入scheduler
     *
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }
}
