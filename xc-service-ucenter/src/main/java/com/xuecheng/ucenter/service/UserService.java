package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName: UserService
 * @Author: DELL
 * @Date: 2019/8/28 19:56
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class UserService {

    @Autowired
    private XcCompanyUserRepository xcCompanyUserRepository;

    @Autowired
    private XcUserRepository xcUserRepository;

    @Autowired
    private XcMenuMapper xcMenuMapper;

    public XcUser findXcUserByUsername(String username){
        return xcUserRepository.findByUsername(username);
    }

    public XcUserExt getUserExt(String username){
        XcUser user = this.findXcUserByUsername(username);
        if (user == null) {
            return null;
        }
        //用户id
        String userId = user.getId();
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(user,xcUserExt);
        //根据用户id查询用户所属公司id
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userId);
        //取到用户的公司id
        String companyId = null;
        if(xcCompanyUser!=null){
            companyId = xcCompanyUser.getCompanyId();
        }
        xcUserExt.setCompanyId(companyId);

        //根据userId查找user权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userId);
        xcUserExt.setPermissions(xcMenus);
        return xcUserExt;
    }
}
