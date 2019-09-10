package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CategoryControllerApi
 * @Author: DELL
 * @Date: 2019/7/25 11:07
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程分类接口",description = "提供了课程分类的crud")
public interface CategoryControllerApi {

    @ApiOperation("查询课程分类列表")
    CategoryNode findList();
}
