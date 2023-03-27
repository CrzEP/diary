package com.dlg.diary.domain.email;

import lombok.Data;

import java.util.Date;

@Data
public class DocInfo {
    private String emailCode;//邮箱消息号
    private String fileDir;//附件目录
    private String filePath;//附件相对路径
    private String fileName;//文件名
    private String senderEmail;//发送人
    private String senderName;
    private Date receiveTime;
    private String receiveMode;
    private Date createTime;
    private String createUser;
}