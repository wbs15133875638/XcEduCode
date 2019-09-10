package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.course.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: SysDichinaryController
 * @Author: DELL
 * @Date: 2019/7/25 15:52
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("sys/dictionary")
public class SysDichinaryController implements SysDicthinaryControllerApi {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Override
    @GetMapping("get/{type}")
    public SysDictionary getByType (@PathVariable String type) {
        return sysDictionaryService.getByType(type);
    }
}