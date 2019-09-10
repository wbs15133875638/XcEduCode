package com.xuecheng.learning.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FileName: CourseSearchClient
 * @Author: DELL
 * @Date: 2019/8/22 9:33
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@FeignClient(XcServiceList.XC_SERVICE_SEARCH)
public interface CourseSearchClient {
    @GetMapping(value = "/search/course/getmedia/{teachplanId}")
    TeachplanMediaPub getmedia (@PathVariable("teachplanId") String teachplanId);
}
