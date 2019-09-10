package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @FileName: CoursePicMapper
 * @Author: DELL
 * @Date: 2019/7/27 10:11
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface CoursePicMapper {
    @Insert("insert into course_pic(courseid,pic) values(#{courseid},#{pic})")
    void save(CoursePic coursePic);

    @Delete("delete from course_pic where pic=#{value}")
    void deleteByPic (String fileUrl);
}
