package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: TemplateService
 * @Author: DELL
 * @Date: 2019/7/15 14:35
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class TemplateService {

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    public QueryResponseResult findAll () {

        List<CmsTemplate> all = cmsTemplateRepository.findAll();
        QueryResult<CmsTemplate> cmsTemplateQueryResult = new QueryResult<CmsTemplate>();
        cmsTemplateQueryResult.setList(all);
        cmsTemplateQueryResult.setTotal(all.size());
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsTemplateQueryResult);
    }
}
