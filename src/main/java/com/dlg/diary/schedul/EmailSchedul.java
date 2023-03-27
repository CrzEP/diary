package com.dlg.diary.schedul;

import com.dlg.diary.entity.EmailInfo;
import com.dlg.diary.service.EmailService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lingui
 * @Date 2023/3/27 10:03
 */
@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class EmailSchedul {

    @Resource
    EmailService emailService;

    @Async
    @Scheduled(cron = "* 0/2 * * * ? ")
    public void emailReceive() {
        log.debug("开始收取邮件");
        List<EmailInfo> emailInfos = emailService.receiveEmail();
        log.debug("收取邮件完成");
    }

}
