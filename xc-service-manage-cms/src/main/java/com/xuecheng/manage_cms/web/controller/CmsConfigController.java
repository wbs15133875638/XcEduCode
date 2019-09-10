package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: CmsConfigController
 * @Author: DELL
 * @Date: 2019/7/19 14:43
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;

    @Override
    @GetMapping("getModel/{id}")
    public CmsConfig getModel (@PathVariable  String id) {
        return cmsConfigService.getModel(id);
    }
}
