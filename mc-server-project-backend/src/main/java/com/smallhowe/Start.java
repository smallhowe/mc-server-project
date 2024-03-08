package com.smallhowe;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Carousel;
import com.smallhowe.mapper.AccountMapper;
import com.smallhowe.service.AccountService;
import com.smallhowe.service.CarouselService;
import com.smallhowe.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Start implements ApplicationRunner {
    @Value("${server.port}")
    private String port;
    @Value("${my.address}")
    private String address;
    @Value("${my.update-image-url}")
    private boolean  updateTable;

    @Resource
    private AccountService accountService;
    @Resource
    private CarouselService carouselService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (updateTable) {
            updateTabs();
        }
    }
    public void updateTabs(){
        String serverIp = address + ":" + port;

        //获取以下代码运行时间
        System.out.println("开始更新数据库表内图片字段的服务器地址");
        List<Account> accounts = accountService.list();
        long startTime = System.currentTimeMillis();
        for (Account account : accounts) {
            if (account.getAvatarUrl() == null) {
                continue;
            }
            int startIndex = account.getAvatarUrl().indexOf("/img");
            int endIndex = account.getAvatarUrl().length();
            String newImgUrl = serverIp + account.getAvatarUrl().substring(startIndex, endIndex);
            account.setAvatarUrl(newImgUrl);
        }
        accountService.updateBatchById(accounts);

        List<Carousel> carousels = carouselService.getCarouselList();
        for (Carousel carousel : carousels) {
            if (carousel.getImgUrl() == null) {
                continue;
            }
            int startIndex = carousel.getImgUrl().indexOf("/img");
            int endIndex = carousel.getImgUrl().length();
            String newImgUrl = serverIp + carousel.getImgUrl().substring(startIndex, endIndex);
            carousel.setImgUrl(newImgUrl);
        }
        carouselService.updateBatchById(carousels);

        System.out.println("更新数据库表内图片字段的服务器地址完成");
        System.out.println("运行时间：" + (System.currentTimeMillis() - startTime) + "ms");


    }
}
