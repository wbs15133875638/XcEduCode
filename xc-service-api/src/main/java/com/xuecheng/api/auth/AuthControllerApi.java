package com.xuecheng.api.auth;

import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: AuthControllerApi
 * @Author: DELL
 * @Date: 2019/8/26 16:06
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "用户认证接口",description = "保存了用户的操作信息")
public interface AuthControllerApi {

    @ApiOperation("用户登录")
    LoginResult login(LoginRequest loginRequest);


    @ApiOperation("用户登出")
    ResponseResult logout();

    JwtResult userJwt();
}
