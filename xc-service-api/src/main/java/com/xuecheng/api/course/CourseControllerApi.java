package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CourseControllerApi
 * @Author: DELL
 * @Date: 2019/7/23 9:55
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程查询接口", description = "课程查询接口,提供课程信息的管理和查询")
public interface CourseControllerApi {

    @ApiOperation("课程添加方法")
    AddCourseResult add (CourseBase courseBase);

    @ApiOperation("获取课程基础信息")
    CourseBase getCourseBaseById (String courseId) throws RuntimeException;

    @ApiOperation("更新课程基础信息")
    ResponseResult updateCourseBase (String id, CourseBase courseBase);

    @ApiOperation("查询我的课程列表")
    QueryResponseResult findCourseList (int page, int size, CourseListRequest courseListRequest);
}
