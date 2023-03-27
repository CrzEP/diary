package com.dlg.diary.exception;

import com.dlg.diary.common.Result;
import lombok.Getter;

/**
 * @author lingui
 * @Date 2022-08-26 09:52:09
 */
@Getter
public class ResultException extends DiaryException{

    Result result;

    public ResultException(Result result){
        this.result = result;
    }

}
