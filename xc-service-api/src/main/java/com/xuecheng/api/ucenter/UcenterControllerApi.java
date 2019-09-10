package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: UcenterControllerApi
 * @Author: DELL
 * @Date: 2019/8/27 8:03
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "用户管理接口",description = "用户中心接口")
public interface UcenterControllerApi {

    @ApiOperation("根据用户名来查询用户")
    XcUserExt getXcUserExt(String username);
}
