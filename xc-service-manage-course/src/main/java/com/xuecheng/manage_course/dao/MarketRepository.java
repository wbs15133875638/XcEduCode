package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: MarketRepository
 * @Author: DELL
 * @Date: 2019/7/26 10:18
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface MarketRepository extends JpaRepository<CourseMarket,String> {
}
