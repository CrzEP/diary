package com.dlg.diary.service.impl;

import com.dlg.diary.config.YmlConfigVal;
import com.dlg.diary.domain.email.EmailAccount;
import com.dlg.diary.entity.EmailInfo;
import com.dlg.diary.exception.ProcessException;
import com.dlg.diary.service.EmailService;
import com.dlg.diary.util.EmailReceiveUtil;
import com.dlg.diary.util.EmailSendUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lingui
 * @Date 2023/3/24 14:44
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    YmlConfigVal configVal;

    // 邮件身份信息
    EmailAccount account;

    @PostConstruct
    void init(){
        account = new EmailAccount(
                configVal.getAppTag(), "lingui1080@163.com", configVal.getEmailAccount(),
                configVal.getEmailPassword(), configVal.getEmailFileDir());
    }


    @Override
    public void sendEmail(String fromName, String receiveMailAccount, String title, String content) {
        if (!EmailSendUtil.sendEmail(account, fromName, receiveMailAccount, title, content)) {
            throw ProcessException.processInterrupt("发送邮件");
        }
    }

    @Override
    public List<EmailInfo> receiveEmail() {
        return EmailReceiveUtil.receiveEmail(account);
    }
}
