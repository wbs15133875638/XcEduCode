package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.ext.CmsPostPageResult;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: CmsPageControllerApi
 * @Author: DELL
 * @Date: 2019/7/12 15:29
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面数据模型的管理、查询接口")
public interface CmsPageControllerApi {

    @ApiOperation("查询页面")
    QueryResponseResult findList(Integer page , Integer size , QueryPageRequest queryPageRequest);

    @ApiOperation("添加页面")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("通过id获得页面")
    CmsPageResult get(String pageId);

    @ApiOperation("修改页面")
    CmsPageResult edit(String pageId, CmsPage cmsPage);

    @ApiOperation("根据id删页面")
    CmsPageResult delete(String pageId);

    @ApiOperation("查询页面类型")
    QueryResponseResult type();

    @ApiOperation("发布页面")
    ResponseResult post(String pageId);

    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("一键发布页面")
    CmsPostPageResult postPageQuick (CmsPage cmsPage);
}
