package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: CoursePubRepository
 * @Author: DELL
 * @Date: 2019/8/5 7:32
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface CoursePubRepository extends JpaRepository<CoursePub,String> {
}
