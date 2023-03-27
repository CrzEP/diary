package com.dlg.diary.exception;


import com.dlg.diary.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author lingui
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class DiaryExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ResultException.class)
    public Result handleHxException(ResultException ex) {
        return ex.getResult();
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public Result<String> handleHxException(HttpMessageConversionException ex) {
        Result<String> result = new Result<>();
        result.error("请求参数或参数转换异常。");
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleHxException(Exception ex) {
        Result<String> result = new Result<>();
        result.error("未知异常：" + ex.getMessage());
        return result;
    }

}