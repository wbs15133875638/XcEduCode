package com.xuecheng.auth.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @FileName: UserFeignClient
 * @Author: DELL
 * @Date: 2019/8/30 9:29
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@FeignClient(XcServiceList.XC_SERVICE_UCENTER)
public interface UserFeignClient {
    @GetMapping("/ucenter/getuserext")
    XcUserExt getXcUserExt (@RequestParam(value = "username",defaultValue = "") String username);
}
