package com.github.imdtf.mnt.exception;

import com.github.imdtf.mnt.response.ResponseEnum;
import lombok.Getter;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2023/6/11 13:08
 * 4
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private ResponseEnum responseEnum;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BizException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
    }

    public BizException(ResponseEnum responseEnum, String detail) {
        super(responseEnum.getMsg() + ", " + detail);
        this.responseEnum = responseEnum;
    }
}
