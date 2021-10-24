package priv.xin.xd.expansionService.job.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import priv.xin.xd.common.entity.FolderPath;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.service.ISysFileService;
import priv.xin.xd.core.service.ISysFolderService;
import priv.xin.xd.core.dao.FolderMapper;
import priv.xin.xd.core.entity.Folder;
import priv.xin.xd.expansionService.crawler.taobao.TaobaoProcessor;
import priv.xin.xd.expansionService.redis.service.ServiceLogger;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 记录日志-将redis中的日志保存到log表
 *
 * @author ：lu
 * @date ：2021/9/28 19:06
 */
public class RecordLogJob implements Job {

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
//    public static final String cron = "0 30 15 * * ? *"; // 每天3点触发
    public static final String cron = "0 14 22 * * ? *"; // 每天3点触发

    private ServiceLogger serviceLogger;
    private ISysFolderService sysFolderService;
    private PathProperties pathProperties;
    private ISysFileService sysFileService;

    private FolderMapper folderMapper;

    private void loadBean() {
        if (serviceLogger == null) {
            serviceLogger = SpringUtil.getBean(ServiceLogger.class);
        }
        if (sysFolderService == null) {
            sysFolderService = SpringUtil.getBean(ISysFolderService.class);
        }
        if (pathProperties == null) {
            pathProperties = SpringUtil.getBean(PathProperties.class);
        }
        if (sysFileService == null) {
            sysFileService = SpringUtil.getBean(ISysFileService.class);
        }

        if (folderMapper == null) {
            folderMapper = SpringUtil.getBean(FolderMapper.class);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        loadBean();
        LoggerUtil.info("开始:" + System.currentTimeMillis(), this.getClass());
        // TODO 业务
        recordLog();
        LoggerUtil.info("结束:" + System.currentTimeMillis(), this.getClass());
    }

    private void recordLog() {
        String fileSaveRealPath = pathProperties.getFileSaveRealPath();
        String ymd = DateUtil.format(System.currentTimeMillis(), "yyyy-MM-dd");

        Map<String, List<String>> userLogsMap = serviceLogger.getLog();
        if (userLogsMap != null && userLogsMap.size() > 0) {
            for (Map.Entry<String, List<String>> userLogInfo : userLogsMap.entrySet()) {
                String userId = userLogInfo.getKey();
                List<String> logList = userLogInfo.getValue();
                if (ListUtil.isNotEmpty(logList)) {
                    String userLogFolderPath = String.format(FolderPath.USER_LOG_FOLDER_PATH, userId);
                    String filePath = userLogFolderPath + "/" + ymd + ".log";
                    String logFilePath = fileSaveRealPath + filePath;

                    // 去除第一个斜杠
//                    String filePath = "";
//                    if (userLogFolderPath.startsWith("/")) {
//                        filePath = userLogFolderPath.substring(1);
//                    }

                    Folder folderQuery = new Folder();
                    folderQuery.setFolderPath(userLogFolderPath);
                    folderQuery.setFolderOwner(userId);
                    List<Folder> folders = folderMapper.queryAll(folderQuery);
                    if (ListUtil.isEmpty(folders)) {
                        continue;
                    }
                    String folderId = folders.get(0).getFolderId();

                    if (serviceLogger.writeLog(logFilePath, logList)) {
                        File logFile = new File(logFilePath);
                        String fileSize = "未知";
                        if (logFile.exists()) {
                            fileSize = FileUtil.getFileSize(logFile.length());
                        }
                        Result result = sysFileService.saveLogFile(userId, folderId, filePath, fileSize);
                    }
                }
            }
        }
    }

}
