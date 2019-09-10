package com.xuecheng.api.cms;

/**
 * @FileName: CmsConfigControllerApi
 * @Author: DELL
 * @Date: 2019/7/19 14:40
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms配置管理接口", description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
    public CmsConfig getModel (String id);
}
