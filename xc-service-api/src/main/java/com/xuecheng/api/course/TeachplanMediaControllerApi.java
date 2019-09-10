package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: TeachplanMediaControllerApi
 * @Author: DELL
 * @Date: 2019/8/20 8:13
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程计划和媒资管理接口",description = "提供了课程计划和媒资的关联以及crud操作")
public interface TeachplanMediaControllerApi {
    @ApiOperation("把媒资文件保存到课程计划信息")
    ResponseResult saveMedia(TeachplanMedia teachplanMedia);
}
