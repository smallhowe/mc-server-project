package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Carousel;
import com.smallhowe.entity.RestBean;
import com.smallhowe.mapper.CarouselMapper;
import com.smallhowe.service.CarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallhowe.utils.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;
    @Resource
    private ImageUtils imageUtils;
    @Override
    public RestBean<Object> addCarousel(String title,String content,MultipartFile img) {
        String filePath = System.getProperty("user.dir")+"\\study-project-backend\\src\\main\\resources\\static\\images\\";
        Map<String, String> map = imageUtils.saveImgToImages(img);
        Carousel carousel = new Carousel(title,content,map.get("path"),map.get("url"));

        int flag=carouselMapper.insert(carousel);
        if (flag<1)return RestBean.failure(500,"添加失败");

        return RestBean.success("添加成功");
    }

    @Override
    public List<Carousel> getCarouselList() {
        LambdaQueryWrapper<Carousel> lqw = new LambdaQueryWrapper<>();
        lqw.select(
                Carousel::getId,
                Carousel::getTitle,
                Carousel::getContent,
                Carousel::getImgUrl
        );
        return carouselMapper.selectList(lqw);
    }
}
