package com.dlg.diary.entity;

import com.dlg.diary.domain.email.FileInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_emailInfo")
public class EmailInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String emailCode;//邮箱消息号
    private String sender;//发件人
    private String title;//主题
    private String receiver;//收件人
    private Date acceptTime;//收件时间
    private String content;
    @Transient
    private List<FileInfo> fileInfos;
}