package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: TeachplanMediaPubRepository
 * @Author: DELL
 * @Date: 2019/8/20 18:55
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub,String> {
}
