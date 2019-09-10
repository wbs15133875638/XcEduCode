package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @FileName: PageService
 * @Author: DELL
 * @Date: 2019/7/22 7:28
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 要生成的页面的id
     * @param pageId
     */
    public void savePageToSeverPath (String pageId) {
        CmsPage cmsPage = this.get(pageId);
        InputStream inputStream = this.getFileById(cmsPage.getHtmlFileId());
        if (inputStream == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        FileOutputStream fileOutputStream = null;
        CmsSite cmsSite = this.getCmsSiteBySiteId(cmsPage.getSiteId());

        File mkdir = new File(cmsSite.getSitePhysicalPath(),cmsPage.getPagePhysicalPath());
        if(!mkdir.exists()){
            mkdir.mkdirs();
        }
        File file = new File(mkdir, cmsPage.getPageName());
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private InputStream getFileById (String htmlFileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CmsSite getCmsSiteBySiteId (String siteId) {
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(siteId);
        if (!cmsSiteOptional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_SITE_NOT_FOUND);
        }
        return cmsSiteOptional.get();
    }

    private CmsPage get (String pageId) {
        Optional<CmsPage> cmsPageResult = cmsPageRepository.findById(pageId);
        if (!cmsPageResult.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_FOUND);
        }
        return cmsPageResult.get();
    }
}
