package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CourseMarketControllerApi
 * @Author: DELL
 * @Date: 2019/7/25 20:17
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程营销管理接口", description = "提供课程营销信息的管理和查询")
public interface CourseMarketControllerApi {

    @ApiOperation("根据课程id获取课程营销信息")
    CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("更新课程营销信息")
    ResponseResult updateCourseMarket(String courseId , CourseMarket courseMarket);
}
