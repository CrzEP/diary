package com.dlg.diary.service.impl;

import com.dlg.diary.config.YmlConfigVal;
import com.dlg.diary.entity.DiaryEntity;
import com.dlg.diary.entity.FileExtEntity;
import com.dlg.diary.repository.DiaryRepository;
import com.dlg.diary.repository.FileExtRepository;
import com.dlg.diary.service.DiaryService;
import com.dlg.diary.util.FilePathUtils;
import com.dlg.diary.util.FileUtis;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lingui
 * @Date 2023/3/28 10:20
 */
@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

    @Resource
    YmlConfigVal configVal;
    @Resource
    FileExtRepository fileExtRepository;
    @Resource
    DiaryRepository diaryRepository;

    @Override
    public void saveDiary(MultipartFile diary, MultipartFile[] files) {
        String name = diary.getName();
        DiaryEntity entity = new DiaryEntity();
        entity.setName(name);
        String baseName = FilenameUtils.getBaseName(name);
        entity.setDiaryName(baseName);
        // 生成地址
        String diaryDir = FilePathUtils.createDiaryDir(configVal.getFileMainTxtDir());
        // 保存文件
        FileUtis.saveMutFile(diary, diaryDir + File.separator + name);
        // 创建额外文件储存地址
        String dir = FilePathUtils.createExtDir(configVal.getFileExtFileDir(), name);
        List<FileExtEntity> fileExts = saveExtFiles(dir, files);
        entity.setFileExts(fileExts);
        // 持久化记录
        saveDiaryEntity(entity);
    }

    @Transactional
    private void saveDiaryEntity(DiaryEntity entity) {
        diaryRepository.save(entity);
        fileExtRepository.saveAll(entity.getFileExts());
    }

    private List<FileExtEntity> saveExtFiles(String dir, MultipartFile[] files) {
        List<FileExtEntity> list = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            String name = file.getName();
            String path = dir + File.separator + name;
            String type = FilenameUtils.getExtension(name);
            FileExtEntity entity = new FileExtEntity();
            entity.setFileName(name);
            entity.setType(type);
            entity.setFilePath(path);
            entity.setCreateTime(new Date());
            list.add(entity);
        }
        return list;
    }

}
