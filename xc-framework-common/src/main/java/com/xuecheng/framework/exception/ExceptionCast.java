package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @FileName: ExceptionCast
 * @Author: DELL
 * @Date: 2019/7/16 16:28
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
public class ExceptionCast {
    public static void cast (ResultCode resultCode) {
        throw new CustomerException(resultCode);
    }
}
