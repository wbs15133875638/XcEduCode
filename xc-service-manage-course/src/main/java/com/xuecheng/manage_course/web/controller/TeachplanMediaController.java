package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.TeachplanMediaControllerApi;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachplanMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName: TeachplanMediaController
 * @Author: DELL
 * @Date: 2019/8/20 8:12
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course")
public class TeachplanMediaController implements TeachplanMediaControllerApi {

    @Autowired
    private TeachplanMediaService teachplanMediaService;

    @Override
    @PostMapping("savemedia")
    public ResponseResult saveMedia (@RequestBody TeachplanMedia teachplanMedia) {
        return teachplanMediaService.savemedia(teachplanMedia);
    }
}
