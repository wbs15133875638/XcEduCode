package com.xuecheng.framework.domain.learning.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @FileName: GetMediaResult
 * @Author: DELL
 * @Date: 2019/8/22 9:26
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Data
@ToString
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {
    public GetMediaResult (ResultCode resultCode, String fileUrl) {
        super(resultCode);
        this.fileUrl = fileUrl;
    }

    //媒资文件播放地址
    private String fileUrl;
}
