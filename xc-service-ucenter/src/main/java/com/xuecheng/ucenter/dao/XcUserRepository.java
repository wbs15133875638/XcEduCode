package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: XcUserRepository
 * @Author: DELL
 * @Date: 2019/8/27 8:06
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface XcUserRepository extends JpaRepository<XcUser,String> {
    XcUser findByUsername (String username);
}
