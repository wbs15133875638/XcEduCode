package com.xuecheng.api.learning;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CourseLearningControllerApi
 * @Author: DELL
 * @Date: 2019/8/22 9:30
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "录播课程学习",description = "录播课程学习接口")
public interface CourseLearningControllerApi {
    @ApiOperation("获取课程学习地址")
    GetMediaResult getmedia (String courseId, String teachplanId);
}
