package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @FileName: FileSystemControllerApi
 * @Author: DELL
 * @Date: 2019/7/26 17:57
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "文件上传api" , description = "提供了文件的上传")
public interface FileSystemControllerApi {
    /**
     * 上传文件
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey 业务key
     * @param metedata 元信息,json格式
     * @return
     */
    @ApiOperation("文件上传接口")
    UploadFileResult upload (
            MultipartFile multipartFile,
            String filetag,
            String businesskey,
            String metadata);
}
