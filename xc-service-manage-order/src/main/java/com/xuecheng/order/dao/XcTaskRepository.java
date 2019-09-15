package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @FileName: XcTaskRepository
 * @Author: DELL
 * @Date: 2019/9/15 7:27
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface XcTaskRepository extends JpaRepository<XcTask,String> {
    Page<XcTask> findByUpdateTimeBefore(Date updateTime, Pageable pageable);

    @Modifying
    @Query(value = "update xc_task set version=:version+1 where id=:id and version=:version",
            nativeQuery =
            true)
    int updateTaskVersion(@Param(value = "id") String id,@Param(value = "version") int version);
}
