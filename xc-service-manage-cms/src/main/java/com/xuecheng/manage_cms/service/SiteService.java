package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: SiteService
 * @Author: DELL
 * @Date: 2019/7/15 14:50
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class SiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findAll () {
        List<CmsSite> all = cmsSiteRepository.findAll();
        QueryResult<CmsSite> cmsSiteQueryResult = new QueryResult<>();
        cmsSiteQueryResult.setList(all);
        cmsSiteQueryResult.setTotal(all.size());
        return new QueryResponseResult(CommonCode.SUCCESS,cmsSiteQueryResult);
    }
}
