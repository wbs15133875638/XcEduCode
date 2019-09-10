package com.xuecheng.govern.center;

/**
 * @FileName: GovernCenterApplication
 * @Author: DELL
 * @Date: 2019/7/28 19:54
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//标识这是一个Eureka服务
@EnableEurekaServer
@SpringBootApplication
public class GovernCenterApplication {
    public static void main (String[] args) {
        SpringApplication.run(GovernCenterApplication.class, args);
    }
}
