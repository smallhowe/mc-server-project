package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallhowe.entity.News;
import com.smallhowe.mapper.NewsMapper;
import com.smallhowe.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    @Override
    public IPage<News> getNewsList(Page<News> page) {
        LambdaQueryWrapper<News> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(News::getId);
        IPage<News> newsPage = newsMapper.selectPage(page, lqw);
        return newsPage;
    }
}
