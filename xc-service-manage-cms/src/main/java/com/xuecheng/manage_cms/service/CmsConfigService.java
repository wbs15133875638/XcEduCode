package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @FileName: CmsConfigService
 * @Author: DELL
 * @Date: 2019/7/19 14:45
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsConfig getModel (String id) {
        Optional<CmsConfig> result = cmsConfigRepository.findById(id);
        if (!result.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_CONFIG_NOT_FOUND);
        }
        return result.get();
    }
}
