package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CoursePublishResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FileName: ViewControllerApi
 * @Author: DELL
 * @Date: 2019/7/29 8:49
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程数据视图查询,预览和发布接口", description = "提供课程视图信息的查询,页面的预览和发布")
public interface ViewControllerApi {
    @ApiOperation("课程视图查询")
    CourseView courseview (String id);

    @ApiOperation("预览课程")
    CoursePublishResult preview (String id);

    @ApiOperation("发布课程")
    CoursePublishResult publish (@PathVariable String id);
}
