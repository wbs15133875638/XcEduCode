package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.springframework.stereotype.Repository;

/**
 * @FileName: CategoryMapper
 * @Author: DELL
 * @Date: 2019/7/25 14:03
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface CategoryMapper {
    CategoryNode selectList ();

}
