package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName: SysDictionaryService
 * @Author: DELL
 * @Date: 2019/7/25 15:55
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary getByType (String type) {
        return sysDictionaryRepository.findBydType(type);
    }
}
