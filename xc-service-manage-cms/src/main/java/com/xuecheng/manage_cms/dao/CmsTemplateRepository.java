package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @FileName: CmsTemplateRepository
 * @Author: DELL
 * @Date: 2019/7/15 14:40
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {
}
