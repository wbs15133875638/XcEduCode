package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName: CategoryService
 * @Author: DELL
 * @Date: 2019/7/25 14:02
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryNode findList () {
        return categoryMapper.selectList();
    }
}
