package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @FileName: XcCompanyUserRepository
 * @Author: DELL
 * @Date: 2019/8/27 8:07
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser,String> {
    XcCompanyUser findByUserId (String userId);
}
