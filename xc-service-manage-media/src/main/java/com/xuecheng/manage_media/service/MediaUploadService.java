package com.xuecheng.manage_media.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.domain.media.response.MediaCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.config.RabbitMQConfig;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @FileName: MediaUploadService
 * @Author: DELL
 * @Date: 2019/8/13 18:14
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
@Slf4j
public class MediaUploadService {
    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private MediaFileRepository MediaFileRepository;

    @Value("${xc-service-manage-media.upload-location}")
    private String uploadPath;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${xc-service-manage-media.mq.routingkey-media-video}")
    String routingkey_media_video;

    //得到文件的相对路径(相对于文件存放目录)
    private String getFileFolderRelativePath (String fileMd5) {
        return StringUtils.substring(fileMd5, 0, 1) + "/" + StringUtils.substring(fileMd5, 1
                , 2) + "/" + fileMd5 + "/";
    }

    //得到文件所在目录
    private String getFileFolderPath (String fileMd5) {
        return uploadPath + this.getFileFolderRelativePath(fileMd5);
    }

    //得到文件绝对路径
    private String getFilePath (String fileMd5, String fileExt) {
        return this.getFileFolderPath(fileMd5) + fileMd5 + "." + fileExt;
    }

    //创建文件目录
    private boolean createFileFolder (String fileMd5) {
        String fileFolderPath = this.getFileFolderPath(fileMd5);
        File mkdir = new File(fileFolderPath);
        if (!mkdir.exists()) {
            return mkdir.mkdirs();
        }
        return true;

    }

    public ResponseResult register (String fileMd5, String fileName, Long fileSize,
                                    String mimetype, String fileExt) {
        //根据文件的md5值来得到文件
        String filePath = this.getFilePath(fileMd5, fileExt);
        File file = new File(filePath);

        //如果mongodb中有并且文件确实存在,则证明此文件已经上传
        Optional<MediaFile> fileOptional = mediaFileRepository.findById(fileMd5);
        if (fileOptional.isPresent() && file.exists()) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        //如果文件不存在,那么就把文件的md5的值传过去,创建一个文件的目录
        if (!this.createFileFolder(fileMd5)) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //得到文件分块的路径
    private String getChunkFileFolderPath (String fileMd5) {
        return this.getFileFolderPath(fileMd5) + "/" + "chunks" + "/";
    }

    /**
     * 根据filemd5找到文件分块路径并加上chunk之后判断文件是否存在,如果存在,则返回文件存在的结果
     *
     * @param fileMd5
     * @param chunk
     * @return
     */
    public CheckChunkResult checkChunk (String fileMd5, String chunk) {

        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        File file = new File(chunkFileFolderPath + chunk);
        if (file.exists()) {
            return new CheckChunkResult(MediaCode.CHUNK_FILE_EXIST_CHECK, true);
        }
        return new CheckChunkResult(MediaCode.CHUNK_FILE_EXIST_CHECK, false);

    }

    private boolean createChunkFileFolder (String fileMd5) {
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        File chunkFileFolder = new File(chunkFileFolderPath);
        if (!chunkFileFolder.exists()) {
            //创建文件夹
            boolean mkdirs = chunkFileFolder.mkdirs();
            return mkdirs;
        }
        return true;
    }

    /**
     * 由于文件不存在,所以要根据filemd5和chunk在服务器端生成分块文件,然后把file用输入流输入进去
     *
     * @param file
     * @param fileMd5
     * @param chunk
     * @return
     */
    public ResponseResult uploadChunk (MultipartFile file, String fileMd5, String chunk) {
        if (file == null) {
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_ISNULL);
        }
        boolean fileFolder = this.createChunkFileFolder(fileMd5);
        File chunkFile = new File(getChunkFileFolderPath(fileMd5) + chunk);


        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(chunkFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(MediaCode.CHUNK_FILE_UPLOAD_FAIL);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult mergeChunks (String fileMd5, String fileName, Long fileSize,
                                       String mimetype, String fileExt) {
        /**
         * 得到文件分块路径,如果没有分块路径则创建
         */
        String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
        File chunkfileFolder = new File(chunkFileFolderPath);
        if (!chunkfileFolder.exists()) {
            chunkfileFolder.mkdirs();
        }
        //得到合并文件路径
        File mergeFile = new File(this.getFilePath(fileMd5, fileExt));
        //创建合并文件
        //合并文件存在先删除再创建
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        boolean newFile = false;
        try {
            newFile = mergeFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("mergechunks..create mergeFile fail:{}", e.getMessage());
        }
        if (!newFile) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CREATEFAIL);
        }
        //获取块文件，此列表是已经排好序的列表
        List<File> chunkFiles = getChunkFiles(chunkfileFolder);
        //合并文件
        mergeFile = this.mergeFile(mergeFile, chunkFiles);
        if (mergeFile == null) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }
        //校验文件
        boolean checkResult = this.checkFileMd5(mergeFile, fileMd5);
        if (!checkResult) {
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        //将文件信息保存到数据库
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileName(fileMd5 + "." + fileExt);
        mediaFile.setFileOriginalName(fileName);
        //文件路径保存相对路径
        mediaFile.setFilePath(this.getFileFolderRelativePath(fileMd5));
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileType(fileExt);
        //状态为上传成功
        mediaFile.setFileStatus("301002");
        MediaFile save = MediaFileRepository.save(mediaFile);
        this.sendProcessVideoMsg(save.getFileId());

        return new ResponseResult(CommonCode.SUCCESS);
    }

    private void sendProcessVideoMsg (String fileId) {
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("mediaId", fileId);
        //发送的消息
        String msg = JSON.toJSONString(msgMap);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK,routingkey_media_video,msg);
    }

    //校验文件的md5值
    private boolean checkFileMd5 (File mergeFile, String md5) {
        if (mergeFile == null || StringUtils.isEmpty(md5)) {
            return false;
        }
        //进行md5校验
        FileInputStream mergeFileInputstream = null;
        try {
            mergeFileInputstream = new FileInputStream(mergeFile);
            //得到文件的md5
            String mergeFileMd5 = DigestUtils.md5Hex(mergeFileInputstream);
            //比较md5
            if (md5.equalsIgnoreCase(mergeFileMd5)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("checkFileMd5 error,file is:{},md5 is: {}", mergeFile.getAbsoluteFile(), md5);
        } finally {
            try {
                mergeFileInputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //获取所有块文件
    private List<File> getChunkFiles (File chunkfileFolder) {
        //获取路径下的所有块文件
        File[] chunkFiles = chunkfileFolder.listFiles();
        //将文件数组转成list，并排序
        List<File> chunkFileList = new ArrayList<File>();
        chunkFileList.addAll(Arrays.asList(chunkFiles));
        //排序
        Collections.sort(chunkFileList, new Comparator<File>() {
            @Override
            public int compare (File o1, File o2) {
                if (Integer.parseInt(o1.getName()) > Integer.parseInt(o2.getName())) {
                    return 1;
                }
                return -1;
            }
        });
        return chunkFileList;
    }

    //合并文件
    private File mergeFile (File mergeFile, List<File> chunkFiles) {
        try {
            //创建写文件对象
            RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
            //遍历分块文件开始合并
            // 读取文件缓冲区
            byte[] b = new byte[1024];
            for (File chunkFile : chunkFiles) {
                RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "r");
                int len = -1;
                //读取分块文件
                while ((len = raf_read.read(b)) != -1) {
                    //向合并文件中写数据
                    raf_write.write(b, 0, len);
                }
                raf_read.close();
            }
            raf_write.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("merge file error:{}", e.getMessage());
            return null;
        }
        return mergeFile;
    }

}


