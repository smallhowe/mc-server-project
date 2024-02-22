package com.smallhowe.controller;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Carousel;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.CarouselService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@RestController
@RequestMapping("/api/carousel")
public class CarouselController {
    @Resource
    private CarouselService carouselService;
    @PostMapping("/add")
    public RestBean<Object> addCarousel(String title,String content,MultipartFile img, @SessionAttribute("account")Account account){
        if (account.getGroups()!=1) return RestBean.failure(403,"您没有权限添加轮播图");
        return carouselService.addCarousel(title,content,img);
    }

    @GetMapping("/list")
    public RestBean<Object> getCarouselList(){
        List<Carousel> list = carouselService.getCarouselList();
        if (list.isEmpty())
            return RestBean.failure(404, "没有轮播图");
        return RestBean.success(null,list);
    }
}
