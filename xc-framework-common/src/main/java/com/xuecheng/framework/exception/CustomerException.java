package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @FileName: CustomerException
 * @Author: DELL
 * @Date: 2019/7/16 16:25
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
public class CustomerException extends RuntimeException {
    private ResultCode resultCode;

    public ResultCode getResultCode () {
        return resultCode;
    }

    public CustomerException (ResultCode resultCode) {
        this.resultCode = resultCode;
    }

}
