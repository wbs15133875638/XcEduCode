package com.xuecheng.manage_cms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * @FileName: testRestTemplate
 * @Author: DELL
 * @Date: 2019/7/19 16:08
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试的是调用模板好不好用
     */
    @Test
    public void testRestTemplate () {
        String dataUrl = "http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        System.out.println(forEntity.getBody());
    }

}
