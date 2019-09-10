package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachplanMediaRepository;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @FileName: TeachplanMediaService
 * @Author: DELL
 * @Date: 2019/8/20 8:46
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class TeachplanMediaService {

    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    private TeachplanRepository teachplanRepository;

    public ResponseResult savemedia (TeachplanMedia teachplanMedia) {
        if (teachplanMedia == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        String teachplanId = teachplanMedia.getTeachplanId();

        Optional<Teachplan> teachplanOptional = teachplanRepository.findById(teachplanId);
        if (!teachplanOptional.isPresent()) {
            ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_NOT_FOUND);
        }

        Teachplan teachplan = teachplanOptional.get();
        if(StringUtils.isBlank(teachplan.getGrade())||!StringUtils.equals("3",
                teachplan.getGrade())){
            ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_GRADE_ERROR);
        }

        //表中的主键是课程计划id,如果有的话则更新,没有的话则添加
        teachplanMediaRepository.save(teachplanMedia);

        return new ResponseResult(CommonCode.SUCCESS);
    }
}
