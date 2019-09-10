package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: SysDicthinaryControllerApi
 * @Author: DELL
 * @Date: 2019/7/25 15:49
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "数据字典接口",description = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {
    @ApiOperation("根据字典类型查询数据")
    SysDictionary getByType(String type);
}
