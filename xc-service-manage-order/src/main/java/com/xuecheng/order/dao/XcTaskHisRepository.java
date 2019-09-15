package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTaskHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: XcTaskHisRepository
 * @Author: DELL
 * @Date: 2019/9/15 10:19
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface XcTaskHisRepository extends JpaRepository<XcTaskHis,String> {
}
