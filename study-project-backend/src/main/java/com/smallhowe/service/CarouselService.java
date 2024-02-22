package com.smallhowe.service;

import com.smallhowe.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smallhowe.entity.RestBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
public interface CarouselService {
    RestBean<Object> addCarousel(String title,String content,MultipartFile img);
    List<Carousel> getCarouselList();
}
