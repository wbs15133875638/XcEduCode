package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName: TeachplanController
 * @Author: DELL
 * @Date: 2019/7/25 16:51
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RestController
@RequestMapping("course/teachplan")
public class TeachplanController implements TeachplanControllerApi {

    @Autowired
    private TeachplanService teachplanService;

    @Override
    @GetMapping("list/{courseId}")
    public TeachplanNode findTeachplanList (@PathVariable String courseId) {
        return teachplanService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("add")
    public ResponseResult saveTeachPlan(@RequestBody Teachplan teachplan){
        return teachplanService.saveTeachPlan(teachplan);
    }
}
