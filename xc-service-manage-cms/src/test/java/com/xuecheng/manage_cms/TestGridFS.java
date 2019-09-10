package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @FileName: TestGridFS
 * @Author: DELL
 * @Date: 2019/7/20 7:12
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGridFS {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 把硬盘上的模板文件存入GridFs,存进去会返回一个文件id,已经存过三次了,没事就别测试了
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testGridFs () throws FileNotFoundException {
        //要存储的文件
        File file = new File("d:/index_banner.html");
        //定义输入流
        FileInputStream inputStram = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(inputStram, "轮播图测试文件01", "");
        //得到文件ID
        String fileId = objectId.toString();
        System.out.println(file);
    }

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 根据上个方法产生的文件id,来进行读取模板测试
     *
     * @throws IOException
     */
    @Test
    public void queryFile () throws IOException {
        String fileId = "5d325a0ad45bd90440377e96";
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
    }

    @Test
    public void testStore2 () throws FileNotFoundException {
        File file = new File("F:\\course.ftl");
        FileInputStream inputStream = new FileInputStream(file);
        //保存模版文件内容
        ObjectId gridFSFile = gridFsTemplate.store(inputStream, "课程详情模板文件", "");
        String fileId = gridFSFile.toString();
        System.out.println(fileId);
    }
}
