package com.dlg.diary.exception;

/**
 * 处理中断异常
 *
 * @author lingui
 * @Date 2023/3/24 14:55
 */
public class ProcessException extends DiaryException{

    public ProcessException(String msg) {
        super();
    }

    /**
     * 处理中断
     * @return 异常
     */
    public static ProcessException processInterrupt(String msg){
        return new ProcessException(msg+" 处理失败");
    }
}
