package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @FileName: TesFeign
 * @Author: DELL
 * @Date: 2019/7/28 20:30
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TesFeign {

    @Autowired
    private CmsPageClient cmsPageClient;

    @Test
    public void testFeign(){
        CmsPageResult byId = cmsPageClient.findById("5a754adf6abb500ad05688d9");
        System.out.println("byId = " + byId);
    }
}
