package com.github.imdtf.mnt.response;

import com.github.imdtf.mnt.exception.BizException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

import static com.github.imdtf.mnt.response.ResponseEnum.*;


/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2022/10/9 19:18
 * 4
 */
@Data
@Accessors(chain = true)
@Slf4j
public class Response<T> implements Serializable {

    private String code;

    private String msg;

    private String detail;

    private T data;

    public Response<T> checkResponse() {
        if (!isSuccess()) {
            throw new BizException(msg);
        }

        return this;
    }

    public boolean isSuccess() {
        return Objects.equals(OK.getCode(), this.code);
    }

    public static <T> Response<T> ok() {
        return new Response<>(OK);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(OK.getCode(), OK.getMsg(), data);
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<>(SHOW_FAIL.getCode(), msg);
    }

    public static <T> Response<T> fail(ResponseEnum responseEnum) {
        return new Response<>(responseEnum);
    }

    public static <T> Response<T> fail(ResponseEnum responseEnum, String detail) {
       Response<T> response = new Response<>(responseEnum);
       response.setDetail(detail);
       return response;
    }

    private Response(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }

    @SuppressWarnings("unchecked")
    private Response(String code, String msg) {
        this(code, msg, (T) Collections.EMPTY_MAP);
    }

    private Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
