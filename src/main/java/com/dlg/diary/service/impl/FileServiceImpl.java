package com.dlg.diary.service.impl;

import com.dlg.diary.common.Const;
import com.dlg.diary.config.YmlConfigVal;
import com.dlg.diary.entity.FileExtEntity;
import com.dlg.diary.repository.FileExtRepository;
import com.dlg.diary.service.FileService;
import com.dlg.diary.util.FilePathUtils;
import com.dlg.diary.util.FileUtis;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author lingui
 * @Date 2023/3/28 14:35
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    FileExtRepository repository;
    @Resource
    YmlConfigVal configVal;

    @Override
    public String saveExtFile(MultipartFile file) {
        String name = file.getName();
        String extension = FilenameUtils.getExtension(name);
        // 设置属性
        FileExtEntity fileExt = new FileExtEntity();
        fileExt.setFileName(name);
        fileExt.setType(extension);
        fileExt.setCreateTime(new Date());
        // 创建储存位置
        String extDir = FilePathUtils.createExtDir(configVal.getFileExtFileDir(), Const.NO_MAIN_EXT);
        String filePath = extDir + File.separator + name;
        fileExt.setFilePath(filePath);
        // 保存
        FileUtis.saveMutFile(file,filePath);
        repository.save(fileExt);
        return filePath;
    }
}
