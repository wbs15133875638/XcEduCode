package com.xuecheng.filesystem.component;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @FileName: StorageClientUtils
 * @Author: 柏松
 * @Date: 2019/7/26 20:55
 * @Description: 封装了fastDfs-client的方法和storageClient的初始化
 * @Since JDK 1.8
 */
@Component
public class MyFastDfsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFastDfsClient.class);

    @Value("${fastdfs.tracker_servers}")
    private String tracker_servers;

    @Value("${fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;

    @Value("${fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;

    @Value("${fastdfs.charset}")
    private String charset;

    private TrackerClient trackerClient;

    private TrackerServer trackerServer;

    private StorageServer storageServer;

    private StorageClient1 storageClient1;

    @PostConstruct
    public void init() {
        //加载fdfs的配置
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
            //创建tracker client
            trackerClient = new TrackerClient();
            //获取trackerServer
             trackerServer = trackerClient.getConnection();
            //获取storage
            storageServer = trackerClient.getStoreStorage(trackerServer);
            //创建storage client
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (IOException e) {
            LOGGER.error("初始化信息失败,请检查您的配置文件是否和本类@Value注解配置一致");
            e.printStackTrace();
        } catch (MyException e) {
            LOGGER.error("初始化成员变量失败,请您检查是否打开fastdfs和tracker的地址配置");
            e.printStackTrace();
        }
        LOGGER.info("StorageClient初始化成功");
    }

    public String quickUpload (MultipartFile file) {
        try {
            // 文件字节
            byte[] bytes = file.getBytes();
            //文件原始名称
            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            //文件id
            String filePath = null;
            try {
                filePath = storageClient1.upload_file1(bytes, extName, null);
            } catch (MyException e) {
                LOGGER.error("文件上传到storage过程中发生异常");
                e.printStackTrace();
            }
            return filePath;
        } catch (IOException e) {
            LOGGER.error("获得文件的io流时发生异常");
            e.printStackTrace();
            return null;
        }
    }

//依赖
//      <dependency>
//         <groupId>net.oschina.zcx7878</groupId>
//         <artifactId>fastdfs-client-java</artifactId>
//      </dependency>

//配置文件
//    xuecheng:
//      fastdfs:
//        connect_timeout_in_seconds: 5
//        network_timeout_in_seconds: 30
//        charset: UTF-8
//        tracker_servers: 192.168.181.132:22122 #多个 trackerServer中间以逗号分隔
}
