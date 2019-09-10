package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CoursePicControllerApi;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName: CoursePic
 * @Author: DELL
 * @Date: 2019/7/27 8:59
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course/coursepic")
public class CoursePicController implements CoursePicControllerApi {

    @Autowired
    private CoursePicService coursePicService;

    @Override
    @GetMapping("list/{courseId}")
    public List<CoursePic> findById (@PathVariable String courseId) {
        return coursePicService.findById(courseId);
    }

    @Override
    @PostMapping("add")
    public ResponseResult add (String courseId, String pic) {
        return coursePicService.save(courseId,pic);
        //TODO 这有一个不是restfull风格的方法
    }

    /**
     * 由于传过来的参数是一个fastDfs的文件存储地址,有/作为分隔符,所以此方法不能用restfull风格架构
     * @param fileUrl
     * @return
     */
    @Override
    @DeleteMapping("delete")
    public ResponseResult delete (String fileUrl) {
        return coursePicService.delete(fileUrl);
    }
}
