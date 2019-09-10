package com.xuecheng.govern.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.govern.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName: LoginFilter
 * @Author: DELL
 * @Date: 2019/9/4 7:36
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Component
public class LoginFilter extends ZuulFilter {

    @Autowired
    private AuthService authService;

    @Override
    public String filterType () {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder () {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter () {
        return true;
    }

    @Override
    public Object run () throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String cookie = authService.getTokenFromCookie(request);
        if (cookie == null) {
            this.access_denied(currentContext);
        }
        String jwtFromHeader = authService.getJwtFromHeader(request);
        if (jwtFromHeader == null) {
            this.access_denied(currentContext);
        }
        long expire = authService.getExpire(cookie);
        if (expire < 0) {
            this.access_denied(currentContext);
        }

        return null;
    }

    private void access_denied (RequestContext currentContext) {
        currentContext.setSendZuulResponse(false);
        //拒绝访问
        //设置响应内容
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        String responseResultString = JSON.toJSONString(responseResult);
        currentContext.setResponseBody(responseResultString);
        //设置状态码
        currentContext.setResponseStatusCode(200);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
    }
}
