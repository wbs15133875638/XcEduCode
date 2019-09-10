package com.xuecheng.manage_media.controller;

import com.xuecheng.api.media.MediaFileControllerApi;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName: MediaFileController
 * @Author: DELL
 * @Date: 2019/8/19 14:42
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("media/file")
public class MediaFileController implements MediaFileControllerApi {


    @Autowired
    private MediaFileService mediaFileService;

    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult<MediaFile> findList (
            @PathVariable("page") int page,
            @PathVariable("size") int size,
           QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }
}
