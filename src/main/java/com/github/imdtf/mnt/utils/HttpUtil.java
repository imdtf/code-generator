package com.github.imdtf.mnt.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 0 *
 * 1 * @Author: DTF
 * 2 * @email: imdtf@qq.com
 * 3 * @Date: 2023/2/11 15:49
 * 4
 */
public class HttpUtil {

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(getRequestAttributes()).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static HttpServletResponse getResponse() {
        return Optional.ofNullable(getRequestAttributes()).map(ServletRequestAttributes::getResponse).orElse(null);
    }

    public static String getUri() {
        return Optional.ofNullable(getRequest()).map(HttpServletRequest::getRequestURI).orElse("unknown");
    }
}
