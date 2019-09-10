package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName: MarketController
 * @Author: DELL
 * @Date: 2019/7/26 10:16
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course/coursemarket")
public class MarketController implements CourseMarketControllerApi {

    @Autowired
    private MarketService marketService;

    @Override
    @GetMapping("get/{courseId}")
    public CourseMarket getCourseMarketById (@PathVariable(name = "courseId") String courseId) {
        return marketService.findById(courseId);
    }

    @Override
    @PostMapping("update/{courseId}")
    public ResponseResult updateCourseMarket (@PathVariable(name = "courseId") String courseId,
            @RequestBody CourseMarket courseMarket) {
        return marketService.update(courseId , courseMarket);
    }
}
