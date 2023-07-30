package com.github.imdtf.mnt.exception;

import com.github.imdtf.mnt.constant.StrConstant;
import com.github.imdtf.mnt.response.Response;
import com.github.imdtf.mnt.response.ResponseEnum;
import com.github.imdtf.mnt.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.imdtf.mnt.response.ResponseEnum.*;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2022/10/15 22:08
 * 4
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandlerConfig {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return handleBindException(e);
    }

    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException e) {
        logError(e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return Response.fail(METHOD_ARGUMENT_INVALID);
        }

        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError error : fieldErrors) {
            defaultMessages.add(error.getField() + getErrMsg(error));
        }

        return Response.fail(METHOD_ARGUMENT_INVALID, defaultMessages.toString());
    }

    @SuppressWarnings("unchecked")
    private String getErrMsg(FieldError error) {
        for (Object argument : Objects.requireNonNull(error.getArguments())) {
            if (argument instanceof Class && ((Class<?>) argument).isEnum()) {
                return " available: " + Arrays.stream(((Class<Enum<?>>) argument).getEnumConstants())
                        .map(e -> String.valueOf(e.ordinal())).collect(Collectors.joining(StrConstant.COMMA));
            }
        }

        return StrConstant.COLON + error.getDefaultMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object methodArgumentNotValidExceptionHandler(HttpMessageNotReadableException e) {
        logError(e);
        return Response.fail(HTTP_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        logError(e);
        return Response.fail(NO_HANDLER_FOUND);
    }

    @ExceptionHandler(BizException.class)
    public Object bizExceptionHandler(BizException e) {
        logError(e);
        ResponseEnum responseEnum = e.getResponseEnum();
        if (SHOW_FAIL == responseEnum) {
            return Response.fail(e.getMessage());
        }
        return Response.fail(responseEnum);
    }

    @ExceptionHandler({Exception.class})
    public Object exceptionHandler(Exception e) {
        logError(e);
        return Response.fail(EXCEPTION);
    }

    private void logError(Exception e) {
        log.error("handle url [{}] error", HttpUtil.getUri(), e);
    }
}
