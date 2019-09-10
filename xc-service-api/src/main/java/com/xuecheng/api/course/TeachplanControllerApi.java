package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: TeachplanControllerApi
 * @Author: DELL
 * @Date: 2019/7/25 16:49
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程计划管理接口",description = "提供课程计划信息的管理和查询")
public interface TeachplanControllerApi {
    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("课程计划添加")
    ResponseResult saveTeachPlan(Teachplan teachplan);
}
