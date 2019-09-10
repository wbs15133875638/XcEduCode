package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.ext.CmsPostPageResult;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    private PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList (@PathVariable("page") Integer page, @PathVariable("size")
            Integer size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add (@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @Override
    @GetMapping("get/{pageId}")
    public CmsPageResult get (@PathVariable String pageId) {
        return pageService.get(pageId);
    }

    @Override
    @PutMapping("edit/{pageId}")
    public CmsPageResult edit (@PathVariable String pageId, @RequestBody CmsPage cmsPage) {
        return pageService.edit(pageId, cmsPage);
    }

    @Override
    @DeleteMapping("delete/{pageId}")
    public CmsPageResult delete (@PathVariable String pageId) {
        return pageService.delete(pageId);
    }

    @Override
    @GetMapping("type")
    public QueryResponseResult type () {
        return pageService.type();
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post (@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save (@RequestBody CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }

    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick (@RequestBody CmsPage cmsPage) {
        return pageService.postPageQuick(cmsPage);
    }

}