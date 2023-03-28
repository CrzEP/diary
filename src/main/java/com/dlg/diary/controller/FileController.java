package com.dlg.diary.controller;

import com.dlg.diary.common.annotation.ApiLog;
import com.dlg.diary.exception.ParamsException;
import com.dlg.diary.service.DiaryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * 文件控制器
 *
 * @author lingui
 * @Date 2023/3/28 9:59
 */
@Slf4j
@RestController
public class FileController {

    @Resource
    DiaryService diaryService;

    @RequestMapping(value = "/multipartform-data", method = {RequestMethod.POST})
    @ApiLog("获取文件")
    public ResponseEntity<FileSystemResource> multipartformdata(String filePath) {
        File file = new File(filePath);
        if (!file.exists()){
            throw ParamsException.paramNoMatch("文件路径不匹配");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition",
                "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        headers.addAll(form);
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream;charset=utf-8"))
                .body(new FileSystemResource(file));
    }

    @RequestMapping(value="/saveDiary",headers = "content-type=multipart/form-data", method= {RequestMethod.POST})
    @ApiLog("保存日记")
    public void uploadFiles(@RequestParam("diary")MultipartFile diary,@RequestParam("files") MultipartFile[] files){
        diaryService.saveDiary(diary,files);
    }

}
