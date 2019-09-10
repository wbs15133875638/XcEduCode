package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @FileName: TeachplanMediaRepository
 * @Author: DELL
 * @Date: 2019/8/20 8:08
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia,String> {
    List<TeachplanMedia> findByCourseId (String courseId);

    void deleteByCourseId (String courseId);
}
