package com.dlg.diary.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lingui
 * @Date 2023/3/28 10:19
 */
public interface DiaryService {

    /**
     * 保存日记
     * @param diary 日记文件
     * @param files 附属文件
     */
    void saveDiary(MultipartFile diary, MultipartFile[] files);
}
