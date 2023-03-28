package com.dlg.diary.schedul;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author lingui
 * @Date 2023/3/27 10:03
 */
@Slf4j
@Component
public class EmailSchedul {
    @Scheduled(cron = "* 0/2 * * * ? ")
    public void emailReceive() {
        log.debug("cron");
    }

}
