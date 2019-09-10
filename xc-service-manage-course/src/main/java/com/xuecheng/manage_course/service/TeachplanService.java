package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @FileName: TeachplanService
 * @Author: DELL
 * @Date: 2019/7/24 9:10
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    public TeachplanNode selectList(String courseId){
        return teachplanMapper.selectList(courseId);
    }

    public TeachplanNode findTeachplanList (String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    @Transactional
    public ResponseResult saveTeachPlan (Teachplan teachplan) {
        if (teachplan == null || StringUtils.isBlank(teachplan.getCourseid())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        Teachplan teachplanParent = null;
        //判断有没有选择根节点,如果没选择根节点,就用课程id获取根节点,如果选择了,则用这个根节点来id来得到跟节点对象
        if (StringUtils.isBlank(parentid)) {
            teachplanParent = this.getTeachplanParent(courseid);
        }else{
            teachplanParent = teachplanRepository.findById(parentid).get();
        }
        //获取根节点之后添加节点

        teachplan.setParentid(teachplanParent.getId());
        //比根节点等级+1
        String parentGrade = teachplanParent.getGrade();
        teachplan.setGrade(Integer.parseInt(parentGrade) + 1 + "");
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private Teachplan getTeachplanParent (String courseId) {
        //获取课程对象
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        //如果获取不到说明参数有误
        if (!courseBaseOptional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseBase courseBase = courseBaseOptional.get();
        //根据课程id和parentid为0的来获取根节点对象
        Teachplan teachplanParent =
                teachplanRepository.findByCourseidAndParentid(courseBase.getId(), "0");
        //没有这个根节点对象则原地创建一个根节点并添加,然后以这个根节点为被添加节点的父节点
        if (teachplanParent == null) {
            teachplanParent = new Teachplan();
            teachplanParent.setCourseid(courseBase.getId());
            teachplanParent.setPname(courseBase.getName());
            teachplanParent.setParentid("0");
            teachplanParent.setGrade("1");
            teachplanParent.setStatus("0");
            Teachplan saved = teachplanRepository.save(teachplanParent);
            return saved;
        }
        return teachplanParent;
    }
}
