package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Map;

/**
 * @FileName: EsCourseControllerApi
 * @Author: DELL
 * @Date: 2019/8/5 16:44
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程搜索", description = "课程搜索", tags = {"课程搜索"})
public interface EsCourseControllerApi {
    @ApiOperation("课程搜索")
    QueryResponseResult<CoursePub> list (int page, int size, CourseSearchParam courseSearchParam) throws IOException;

    @ApiOperation("根据id查询课程信息")
    Map<String, CoursePub> getall (String id);

    @ApiOperation("根据课程计划查询媒资信息")
    TeachplanMediaPub getmedia (String teachplanId);
}
