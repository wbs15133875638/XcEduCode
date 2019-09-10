package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @FileName: CoursePicRepository
 * @Author: DELL
 * @Date: 2019/7/27 9:05
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    List<CoursePic> findByCourseid(String courseId);

    CoursePic findByPic(String pic);
}
