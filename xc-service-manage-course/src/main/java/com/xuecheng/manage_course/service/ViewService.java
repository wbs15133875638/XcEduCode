package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.ext.CmsPostPageResult;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.*;
import com.xuecheng.framework.domain.course.ext.CoursePublishResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @FileName: ViewService
 * @Author: DELL
 * @Date: 2019/7/29 8:53
 * @Description: ${DESCRIPTION}
 * @Since JDK 1.8
 */
@Service
public class ViewService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CmsPageClient cmsPageClient;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoursePubRepository coursePubRepository;

    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    private TeachplanMediaPubRepository teachplanMediaPubRepository;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    //课程视图查询
    public CourseView getCoruseView (String id) {
        CourseView courseView = new CourseView();
        courseView.setCourseBase(this.getCourseBaseById(id));
        courseView.setCourseMarket(this.getCourseMarketById(id));
        courseView.setCoursePic(this.getCoursePicById(id));
        courseView.setTeachplanNode(this.getTeachplanNodeById(id));
        return courseView;

    }

    //查询课程计划信息
    private TeachplanNode getTeachplanNodeById (String id) {
        return teachplanMapper.selectList(id);
    }

    //查询课程图片信息
    private CoursePic getCoursePicById (String id) {
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            return coursePic;
        }
        return new CoursePic();
    }

    //查询课程营销信息
    private CourseMarket getCourseMarketById (String id) {
        Optional<CourseMarket> courseMarketOptional = marketRepository.findById(id);
        if (courseMarketOptional.isPresent()) {
            CourseMarket courseMarket = courseMarketOptional.get();
            return courseMarket;
        }
        return new CourseMarket();
    }

    //查询课程基本信息
    private CourseBase getCourseBaseById (String id) {
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            return courseBase;
        }
        return new CourseBase();
    }

    public CoursePublishResult preview (String courseId) {

        //远程请求cms保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.save(createCmsPage(courseId));
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CmsCode.CMS_PAGE_NOT_FOUND, null);
        }
        //页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //页面url
        String pageUrl = previewUrl + pageId;
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    //课程发布
    @Transactional
    public CoursePublishResult publish (String courseId) {
        //课程信息
        CourseBase one = courseService.getCoursebaseById(courseId);
        //发布课程详情页面
        CmsPostPageResult cmsPostPageResult = publish_page(courseId);
        if (!cmsPostPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //更新课程状态
        CourseBase courseBase = saveCoursePubState(courseId);
        //课程索引...
        //先创建一个coursePub对象
        CoursePub coursePub = this.createCoursePub(courseId);
        //将coursePub对象保存到数据库
        this.saveCoursePub(courseId, coursePub);
        // 课程缓存...
        this.saveTeachplanMediaPub(courseId);
        // 页面url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    private void saveTeachplanMediaPub (String courseId) {
        List<TeachplanMedia> teachplanMediaList = teachplanMediaRepository.findByCourseId(courseId);
        teachplanMediaRepository.deleteByCourseId(courseId);
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (TeachplanMedia teachplanMedia : teachplanMediaList) {
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
    }

    private CoursePub saveCoursePub (String id, CoursePub coursePub) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        coursePubNew = coursePubOptional.isPresent() ? coursePubOptional.get() : new CoursePub();
        BeanUtils.copyProperties(coursePub, coursePubNew);
        //设置主键
        coursePubNew.setId(id);
        //更新时间戳为最新时间
        coursePub.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub.setPubTime(date);
        coursePubRepository.save(coursePub);
        return coursePub;
    }

    private CoursePub createCoursePub (String id) {
        CoursePub coursePub = new CoursePub();
        BeanUtils.copyProperties(this.getCourseBaseById(id), coursePub);
        BeanUtils.copyProperties(this.getCourseMarketById(id), coursePub);
        BeanUtils.copyProperties(this.getCoursePicById(id), coursePub);
        coursePub.setTeachplan(JSON.toJSONString(this.getTeachplanNodeById(id)));

        return coursePub;
    }


    //更新课程发布状态
    private CourseBase saveCoursePubState (String courseId) {
        CourseBase courseBase = courseService.getCoursebaseById(courseId);
        //更新发布状态
        courseBase.setStatus("202002");
        CourseBase save = courseBaseRepository.save(courseBase);
        return save;
    }

    //发布课程正式页面
    public CmsPostPageResult publish_page (String courseId) {
        //发布页面
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(createCmsPage(courseId));
        return cmsPostPageResult;
    }

    private CmsPage createCmsPage (String courseId) {
        CourseBase one = courseService.getCoursebaseById(courseId);
        //发布课程预览页面
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);
        //课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(courseId + ".html");
        //页面别名
        cmsPage.setPageAliase(one.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        return cmsPage;
    }
}


