package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CoursePicMapper;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: CoursePicService
 * @Author: DELL
 * @Date: 2019/7/27 9:04
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class CoursePicService {

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CoursePicMapper coursePicMapper;

    public List<CoursePic> findById (String courseId) {
        List<CoursePic> picList = coursePicRepository.findByCourseid(courseId);
        if (picList == null || picList.size() == 0) {
            ExceptionCast.cast(CourseCode.COURSE_INFO_NOT_FOUND_ERROR);
        }
        return picList;
    }

    /**
     * 因为CoursePic这个表实体类在设计的时候是以CourseId为主键,每个课程最多一张表
     * 但是我把这张表的主键去掉了,但是CoursePic这个实体类怎么改,我不会,所以用了一下mybatis
     * @param courseId
     * @param pic
     * @return
     */
    public ResponseResult save (String courseId, String pic) {
        CoursePic coursePic = new CoursePic();
        coursePic.setPic(pic);
        coursePic.setCourseid(courseId);
        coursePicMapper.save(coursePic);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult delete (String fileUrl) {
        coursePicMapper.deleteByPic(fileUrl);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
