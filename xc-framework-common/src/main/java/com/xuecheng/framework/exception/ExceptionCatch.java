package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.AccessDeniedException;

/**
 * @FileName: ExceptionCatch
 * @Author: DELL
 * @Date: 2019/7/16 16:37
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        //在这里加入一些基础的异常类型判断
        builder
                .put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM)
                .put(HttpRequestMethodNotSupportedException.class,CommonCode.INVALID_METHOD)
                .put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }



    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public ResponseResult customerException (CustomerException e) {
        log.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        ResultCode resultCode = e.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult exception (Exception e) {
        log.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        final ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        final ResponseResult responseResult;
        if (resultCode != null) {
            responseResult = new ResponseResult(resultCode);
        } else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }
}
