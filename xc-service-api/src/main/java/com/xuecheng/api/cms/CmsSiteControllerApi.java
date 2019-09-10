package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CmsPageControllerApi
 * @Author: DELL
 * @Date: 2019/7/12 15:29
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "cms站点管理接口", description = "cms站点管理接口，提供模板数据模型的管理、查询接口")
public interface CmsSiteControllerApi {

    @ApiOperation("查询所有站点")
    QueryResponseResult findAll ();

}
