package com.dlg.diary.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lingui
 * @Date 2023/3/28 14:35
 */
public interface FileService {

    String saveExtFile(MultipartFile file);
}
