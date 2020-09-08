package edu.fudan.backend.component;

import edu.fudan.backend.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author tyuan@ea.com
 * @Date 8/20/2020 3:42 PM
 */
@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    private ProcessService processService;

    @Scheduled(cron = "0/30 * * * * ? ")   //每5秒执行一次
    public void startProcessTask() {
        log.info("ScheduledTask start process every 30 seconds.");
        try {
            processService.startProcess();
        } catch (Exception e) {
            log.error("start process error!", e);
        }
    }
}
