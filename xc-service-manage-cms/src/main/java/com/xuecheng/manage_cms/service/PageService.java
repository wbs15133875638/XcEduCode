package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ext.CmsPostPageResult;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList (int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();

        if (StringUtils.isNotBlank(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        if (StringUtils.isNotBlank(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }

        if (StringUtils.isNotBlank(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        if (StringUtils.isNotBlank(queryPageRequest.getPageTypeId())) {
            cmsPage.setPageType(queryPageRequest.getPageTypeId());
        }

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        //为了适应mongodb的接口将页码减1
        page = page - 1;
        if (size <= 0) {
            size = 20;
        }
        //分页对象
        Pageable pageable = new PageRequest(page, size);

        //分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        //返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    public CmsPageResult add (CmsPage cmsPage) {
        CmsPage result =
                cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                        cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (result != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        CmsPage saved = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, saved);
    }

    public CmsPageResult get (String pageId) {
        Optional<CmsPage> result = cmsPageRepository.findById(pageId);
        if (!result.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_FOUND);
        }
        return new CmsPageResult(CommonCode.SUCCESS, result.get());
    }

    public CmsPageResult edit (String pageId, CmsPage cmsPage) {
        CmsPageResult cmsPageResult = this.get(pageId);
        cmsPage.setPageId(pageId);
        CmsPage edited = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, edited);
    }

    public CmsPageResult delete (String pageId) {
        CmsPageResult cmsPageResult = this.get(pageId);
        cmsPageRepository.deleteById(cmsPageResult.getCmsPage().getPageId());
        return new CmsPageResult(CommonCode.SUCCESS, null);
    }

    public QueryResponseResult type () {
        List<CmsPage> all = cmsPageRepository.findAll();
        List<CmsPage> types = new ArrayList<CmsPage>();
        types.add(all.get(0));
        boolean bool = false;
        for (CmsPage cmsPage : all) {
            bool = false;
            for (int i = 0, len = types.size(); i < len; i++) {
                if (types.get(i).getPageType().equals(cmsPage.getPageType())) {
                    bool = true;
                    break;
                }
            }
            if (!bool) {
                types.add(cmsPage);
            }
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(types);
        queryResult.setTotal(types.size());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public String getPageHtml (String pageId) {
        //先根据页面id来获得页面对象,这样的话省的让其他方法再单独调用get来获得页面对象
        CmsPage cmsPageResult = this.get(pageId).getCmsPage();
        //获得数据模型
        Map model = this.getModelByCmsPage(cmsPageResult);
        String templateContent = this.getTemplateByCmsPage(cmsPageResult);
        if (StringUtils.isEmpty(templateContent)) {
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        String html = this.generateHtml(templateContent, model);
        if (StringUtils.isEmpty(html)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    private String generateHtml (String templateContent, Map model) {
        String s = null;
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateContent);
            configuration.setTemplateLoader(stringTemplateLoader);
            Template template = configuration.getTemplate("template");
            s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return s;
    }

    private String getTemplateByCmsPage (CmsPage cmsPage) {
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isBlank(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> templateResult = cmsTemplateRepository.findById(templateId);
        if (!templateResult.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String templateFileId = templateResult.get().getTemplateFileId();
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        if (gridFSFile == null) {
            ExceptionCast.cast(CmsCode.CMS_TEMPLATE_FILE_NOT_FOUND);
        }
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map getModelByCmsPage (CmsPage cmsPage) {
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isBlank(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }

    public ResponseResult postPage (String pageId) {
        String content = this.getPageHtml(pageId);
        if (StringUtils.isBlank(content)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        CmsPage cmsPage = this.saveHtml(pageId, content);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_SAVE_HEMLFILE_ERROR);
        }
        this.sendPostPage(pageId, cmsPage.getSiteId());

        return new ResponseResult(CommonCode.SUCCESS);
    }

    private void sendPostPage (String pageid, String siteId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageId", pageid);
        String msg = JSON.toJSONString(map);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, siteId, msg);
    }

    private CmsPage saveHtml (String pageId, String content) {
        CmsPage cmsPage = get(pageId).getCmsPage();
        String htmlFileId = cmsPage.getHtmlFileId();
        if (StringUtils.isNotBlank(cmsPage.getHtmlFileId())) {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsPage.getHtmlFileId())));
        }
        try {
            InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
            ObjectId storeId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            String s = storeId.toString();
            cmsPage.setHtmlFileId(s);
            cmsPageRepository.save(cmsPage);
            return cmsPage;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(CmsCode.CMS_SAVE_HEMLFILE_ERROR);
            return null;
        }
    }

    //添加页面，如果已存在则更新页面
    public CmsPageResult save (CmsPage cmsPage) {
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            //更新
            return this.edit(cmsPage1.getPageId(), cmsPage);
        } else {
            //添加
            return this.add(cmsPage);
        }
    }

    public CmsPostPageResult postPageQuick (CmsPage cmsPage) {
        //添加页面
        CmsPageResult save = this.save(cmsPage);
        if (!save.isSuccess()) {
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        CmsPage cmsPage1 = save.getCmsPage();
        //要布的页面id
        String pageId = cmsPage1.getPageId();
        //发布页面
        ResponseResult responseResult = this.postPage(pageId);
        if (!responseResult.isSuccess()) {
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        //得到页面的url
        //页面url=站点域名+站点webpath+页面webpath+页面名称
        // 站点id
        String siteId = cmsPage1.getSiteId();
        //查询站点信息
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //站点域名
        String siteDomain = cmsSite.getSiteDomain();
        //站点web路径
        String siteWebPath = cmsSite.getSiteWebPath();
        //页面web路径
        String pageWebPath = cmsPage1.getPageWebPath();
        //页面名称
        String pageName = cmsPage1.getPageName();
        //页面的web访问地址
        String pageUrl = siteDomain + siteWebPath + pageWebPath + pageName;
        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    public CmsSite findCmsSiteById (String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_SITE_NOT_FOUND);
        }
        return optional.get();
    }
}
