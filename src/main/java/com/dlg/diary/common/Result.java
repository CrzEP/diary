package com.dlg.diary.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回封装
 *
 * @author lingui
 * @Date 2022/3/15
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int SUCC = 0;

    /**
     * 编码：0表示成功，其他值表示失败
     */
    private int code = SUCC;
    /**
     * 消息内容
     */
    private String msg = "success";
    /**
     * 响应数据
     */
    private T data;

    public Result<T> succ(T data) {
        this.setData(data);
        return this;
    }

    public boolean success(){
        return code == SUCC;
    }

    public Result<T> error() {
        this.code = 500;
        this.msg = "服务器内部异常";
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public static Result<String> ok(){
        return new Result<>();
    }
}
