package com.xuecheng.framework.domain.cms.ext;

/**
 * @FileName: CmsPostPageResult
 * @Author: DELL
 * @Date: 2019/7/29 10:38
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//无参构造器注解
public class CmsPostPageResult extends ResponseResult {
    String pageUrl;

    public CmsPostPageResult (ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
