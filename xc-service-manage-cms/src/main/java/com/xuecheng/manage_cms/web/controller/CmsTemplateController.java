package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: CmsTemplateController
 * @Author: DELL
 * @Date: 2019/7/15 14:31
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private TemplateService templateService;

    @GetMapping("list")
    @Override
    public QueryResponseResult findAll () {
        return templateService.findAll();
    }
}
