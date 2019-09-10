package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.component.MyFastDfsClient;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);


    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Autowired
    private MyFastDfsClient myFastDfsClient;

    //上传文件
    public UploadFileResult upload (MultipartFile file, String filetag, String businesskey, String metadata) {
        //校验文件是否为空
        try {
            if (file == null|| ImageIO.read(file.getInputStream())==null){
                ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        //通过我们的组件来进行文件上传
        String storePath = myFastDfsClient.quickUpload(file);
        //fileSystem实例持久化
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(storePath);
        fileSystem.setFilePath(storePath);
        fileSystem.setFiletag(filetag);
        fileSystem.setFileName(file.getName());
        fileSystem.setFileSize(file.getSize());
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileType(file.getContentType());
        if(StringUtils.isNotBlank(metadata)){
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }
}