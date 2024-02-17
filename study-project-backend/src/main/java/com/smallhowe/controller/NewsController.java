package com.smallhowe.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallhowe.entity.News;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @GetMapping("/list")
    public RestBean<Object> getNewsList(Long current){
        Page<News> page = new Page<>(current, 8);
        IPage<News> newsList = newsService.getNewsList(page);
        if (newsList.getTotal() == 0) return RestBean.failure(400,"当前没有公告");
        return RestBean.success("获取成功",newsList);
    }

}
