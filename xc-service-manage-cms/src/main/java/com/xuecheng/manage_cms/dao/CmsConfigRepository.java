package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @FileName: CmsConfigRepostory
 * @Author: DELL
 * @Date: 2019/7/19 14:42
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
