package priv.xin.xd.expansionService.quartz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.expansionService.quartz.service.QuartzService;
import priv.xin.xd.expansionService.quartz.job.TaoBaoJob;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/10/27 21:04
 */
@RestController
@RequestMapping("job")
public class JobController {
    @Resource
    private QuartzService quartzService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start() {
        quartzService.addJob(TaoBaoJob.class, TaoBaoJob.jobName, TaoBaoJob.jobGroupName, 60, 1, null);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAll() {
        return JsonUtil.toJson(quartzService.queryAllJob());
    }

    @RequestMapping(value = "/getAllRun", method = RequestMethod.GET)
    public String getAllRun() {
        return JsonUtil.toJson(quartzService.queryRunJob());
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void stop() {
        quartzService.pauseJob(TaoBaoJob.jobName, TaoBaoJob.jobGroupName);
    }

    @RequestMapping(value = "/restart", method = RequestMethod.GET)
    public void restart() {
        quartzService.resumeJob(TaoBaoJob.jobName, TaoBaoJob.jobGroupName);
    }

}
