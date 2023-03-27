package com.dlg.diary.service;

import com.dlg.diary.entity.EmailInfo;

import java.util.List;

/**
 *
 * @author lingui
 * @Date 2023/3/24 14:42
 */
public interface EmailService {

    /**
     * 一对一发送邮件
     *
     * @param fromName           发件人姓名
     * @param receiveMailAccount 收件人邮箱
     * @param title              邮件标题
     * @param content            邮件内容
     */
    void sendEmail(String fromName, String receiveMailAccount, String title, String content);

    /**
     * 接受邮件
     */
    List<EmailInfo> receiveEmail();

}
