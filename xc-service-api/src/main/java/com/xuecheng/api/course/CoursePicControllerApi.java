package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @FileName: CoursePicControllerApi
 * @Author: DELL
 * @Date: 2019/7/27 9:00
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Api(value = "课程图片管理接口",description = "实现课程图片的管理")
public interface CoursePicControllerApi {
    @ApiOperation("根据课程id获得图片对象")
    List<CoursePic> findById(String courseId);

    @ApiOperation("图片上传成功之后把图片地址存入数据库")
    ResponseResult add(String courseId , String pic);

    @ApiOperation("根据图片的保存路径来删除图片")
    ResponseResult delete(String fileUrl);
}
