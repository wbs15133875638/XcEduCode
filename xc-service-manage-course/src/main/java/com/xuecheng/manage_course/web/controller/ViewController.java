package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.ViewControllerApi;
import com.xuecheng.framework.domain.course.ext.CoursePublishResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.manage_course.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName: ViewController
 * @Author: DELL
 * @Date: 2019/7/29 8:51
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course")
public class ViewController implements ViewControllerApi {

    @Autowired
    private ViewService viewService;

    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview (@PathVariable("id") String id) {
        return viewService.getCoruseView(id);
    }

    @Override
    @PostMapping("preview/{courseId}")
    public CoursePublishResult preview (@PathVariable String courseId) {
        return viewService.preview(courseId);
    }

    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult publish (@PathVariable String id) {
        return viewService.publish(id);
    }

}
