package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: CmsSiteRepository
 * @Author: DELL
 * @Date: 2019/7/15 15:02
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
