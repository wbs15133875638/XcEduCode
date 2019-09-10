package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @FileName: MediaFileService
 * @Author: DELL
 * @Date: 2019/8/19 14:59
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
@Slf4j
public class MediaFileService {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    public QueryResponseResult<MediaFile> findList (int page, int size, QueryMediaFileRequest queryMediaFileRequest) {

        MediaFile mediaFile = new MediaFile();
        if (queryMediaFileRequest == null) {
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fileOriginalName",
                        ExampleMatcher.GenericPropertyMatchers.contains());
        if (StringUtils.isNotBlank(queryMediaFileRequest.getTag())) {
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if (StringUtils.isNotBlank(queryMediaFileRequest.getFileOriginalName())) {
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotBlank(queryMediaFileRequest.getProcessStatus())) {
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }

        Example<MediaFile> example = Example.of(mediaFile, matcher);

        if (page <= 0) {
            page = 1;
        }
        page -= 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageRequest);
        QueryResult<MediaFile> queryResult = new QueryResult<>();
        queryResult.setTotal(all.getSize());
        queryResult.setList(all.getContent());
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }
}
