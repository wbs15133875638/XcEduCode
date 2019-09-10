package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: SysDictionaryRepository
 * @Author: DELL
 * @Date: 2019/7/25 15:57
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findBydType (String type);
}
