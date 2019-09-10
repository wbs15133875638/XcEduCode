package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName: CoursePublishResult
 * @Author: DELL
 * @Date: 2019/7/29 9:51
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePublishResult extends ResponseResult {
    String previewUrl;

    public CoursePublishResult (ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
