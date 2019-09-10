package com.xuecheng.api.media;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @FileName: MediaFileControllerApi
 * @Author: DELL
 * @Date: 2019/8/19 14:35
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "媒资文件管理", description = "媒资文件管理接口")
public interface MediaFileControllerApi {
    @ApiOperation("提供媒资文件的查询")
    QueryResponseResult<MediaFile> findList(int page, int pageSize, QueryMediaFileRequest queryMediaFileRequest);
}
