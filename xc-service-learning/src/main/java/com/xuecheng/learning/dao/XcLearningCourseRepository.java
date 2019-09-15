package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @FileName: XcLearningCourseRepository
 * @Author: DELL
 * @Date: 2019/9/15 8:26
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse,String> {
    XcLearningCourse findByUserIdAndCourseId (String userId, String courseId);
}
