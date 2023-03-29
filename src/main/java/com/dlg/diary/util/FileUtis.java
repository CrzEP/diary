package com.dlg.diary.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lingui
 * @Date 2023/3/28 10:55
 */
@Slf4j
public class FileUtis extends FileUtils {

    public static void saveMutFile(MultipartFile file,String destPath){
        File f=new File(destPath);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(f);
            IOUtils.copy(file.getInputStream(),outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOUtils.closeQuietly(outputStream);
        log.debug("已保存文件： {}",destPath);
    }

}
