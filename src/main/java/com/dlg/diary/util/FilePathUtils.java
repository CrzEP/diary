package com.dlg.diary.util;

import com.dlg.diary.exception.ProcessException;

import java.io.File;
import java.util.Calendar;

/**
 * @author lingui
 * @Date 2023/3/28 10:37
 */
public class FilePathUtils {

    /**
     * 创建日记目录
     * @param basePath 基本目录
     * @return
     */
    public static String createDiaryDir(String basePath){
        StringBuilder builder = initMonthFilePath(basePath);
        return createDir(builder.toString());
    }

    private static String createDir(String path) {
        File file = new File(path);
        if (file.exists()){
            return path;
        }
        if (!file.mkdirs()){
            throw new ProcessException("创建目录");
        }
        return path;
    }

    public static String createExtDir(String basePath,String name) {
        StringBuilder builder = initMonthFilePath(basePath);
        builder.append(File.separator).append(name);
        return createDir(builder.toString());
    }

    /**
     * 初始化今日的文件地址
     * 每日创建新的目录
     *
     * @param basePath 基础父目录
     * @return 基于父母目录需要创建或者储存文件的地址
     */
    private static StringBuilder initMonthFilePath(String basePath) {
        StringBuilder sb = new StringBuilder(basePath);
        // 判断最后一个字符是否为文件目录分隔符，不是则增加分隔符
        if (basePath.lastIndexOf(File.separator) != basePath.length() - 1) {
            sb.append(File.separator);
        }
        Calendar date = Calendar.getInstance();
        // 年
        int year = date.get(Calendar.YEAR);
        // 月
        int month = date.get(Calendar.MONTH) + 1;
        // 组装目录地址
        sb.append(year).append(File.separator)
                .append(month);
        return sb;
    }

}
