package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.XcOauth2Util;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName: TeachplanController
 * @Author: DELL
 * @Date: 2019/7/24 9:12
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course/courseBase")
public class CourseController extends BaseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    @Override
    @PostMapping("add")
    public AddCourseResult add (@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    @PreAuthorize("hasAuthority('course_get_baseinfo')")
    @Override
    @GetMapping("get/{courseId}")
    public CourseBase getCourseBaseById (@PathVariable("courseId") String courseId) throws RuntimeException {
        return courseService.getCoursebaseById(courseId);
    }

    @Override
    @PutMapping("update/{id}")
    public ResponseResult updateCourseBase (@PathVariable("id") String id, @RequestBody CourseBase courseBase) {
        return courseService.updateCoursebase(id, courseBase);
    }

    @PreAuthorize("hasAuthority('course_get_base222info')")
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findCourseList (
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            CourseListRequest courseListRequest) {
        XcOauth2Util xcOauth2Util = new XcOauth2Util();
        XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
        if (userJwt == null) {
            ExceptionCast.cast(CommonCode.UNAUTHENTICATED);
        }
        String companyId = userJwt.getCompanyId();
        return courseService.findCourseList(companyId,page, size, courseListRequest);
    }
}

