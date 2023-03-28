package com.dlg.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailAccount {
    String appTag;
    //只处理某邮箱 或者开头
    private String receiveFilterEmail;
    String emailAccount;
    String emailPasswd;
    String fileSaveDir;
}