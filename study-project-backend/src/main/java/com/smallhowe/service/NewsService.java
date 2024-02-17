package com.smallhowe.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallhowe.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
public interface NewsService  {
    IPage<News> getNewsList(Page<News> page);
}
