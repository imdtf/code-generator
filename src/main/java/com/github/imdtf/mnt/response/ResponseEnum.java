package com.github.imdtf.mnt.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2022/10/15 21:12
 * 4
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    /**
     * OK
     */
    OK("0000", "OK"),

    /**
     * 具体显示内容由输入决定
     */
    SHOW_FAIL("0001", ""),

    METHOD_ARGUMENT_INVALID("0002", "参数不正确，请检查后重试"),

    HTTP_MESSAGE_NOT_READABLE("0003", "请求参数格式有误"),

    NO_HANDLER_FOUND("0004", "404 Not Found"),

    EXCEPTION("0005", "服务器出了点小差"),

    DATA_ERROR("0007", "数据异常，请刷新重试"),

    UNAUTHORIZED("0004", "Unauthorized"),

    OTHER("1000", "other");

    private final String code;

    private final String msg;
}
