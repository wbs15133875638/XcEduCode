package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @FileName: TeachplanRepository
 * @Author: DELL
 * @Date: 2019/7/24 10:20
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    Teachplan findByCourseidAndParentid(String courseId , String parentId);
}
