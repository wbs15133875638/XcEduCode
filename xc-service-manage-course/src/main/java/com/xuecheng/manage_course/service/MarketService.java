package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @FileName: MarketService
 * @Author: DELL
 * @Date: 2019/7/26 10:17
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    public CourseMarket findById (String courseId) {

        return this.get(courseId);
    }

    public ResponseResult update (String courseId, CourseMarket courseMarket) {
        marketRepository.save(courseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private CourseMarket get(String courseId){
        Optional<CourseMarket> marketOptional = marketRepository.findById(courseId);
        if(!marketOptional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_MARKET_NOT_FOUND);
        }
        return marketOptional.get();
    }
}
