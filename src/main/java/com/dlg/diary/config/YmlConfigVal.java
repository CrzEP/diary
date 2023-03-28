package com.dlg.diary.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * yml 配置值
 *
 * @author lingui
 * @Date 2022-09-15 17:55:16
 */
@Component
@Getter
@Slf4j
public class YmlConfigVal {

    @Value("${personal.app-tag}")
    String appTag;
    @Value("${personal.email-account}")
    String emailAccount;
    @Value("${personal.email-password}")
    String emailPassword;
    @Value("${personal.email-enable}")
    Boolean emailEnable;
    @Value("${personal.file-dir}")
    String fileDir;
    @Value("${personal.file-main-txt-dir}")
    String fileMainTxtDir;
    @Value("${personal.file-ext-file-dir}")
    String fileExtFileDir;


}
