package com.dlg.diary.exception;

/**
 * @author lingui
 * @Date 2023/3/28 11:10
 */
public class ParamsException extends DiaryException{

    public static ParamsException paramNoMatch(String msg){
        return new ParamsException(msg);
    }

    public ParamsException(String msg) {
        super();
    }
}
