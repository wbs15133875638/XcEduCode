package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.springframework.stereotype.Repository;

/**
 * @FileName: TeachplanMapper
 * @Author: DELL
 * @Date: 2019/7/24 9:07
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface TeachplanMapper {
    TeachplanNode selectList(String courseId);
}
