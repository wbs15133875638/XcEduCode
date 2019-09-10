package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @FileName: XcMenuMapper
 * @Author: DELL
 * @Date: 2019/9/5 7:41
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Repository
public interface XcMenuMapper {
    @Select("SELECT * FROM xc_menu WHERE id IN (SELECT menu_id FROM xc_permission p WHERE role_id IN (SELECT ur.role_id FROM xc_user_role ur WHERE ur.user_id = 50 ))")
    List<XcMenu> selectPermissionByUserId(String userId);
}
