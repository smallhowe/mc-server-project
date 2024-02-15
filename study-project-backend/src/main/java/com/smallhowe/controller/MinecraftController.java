package com.smallhowe.controller;

import com.smallhowe.entity.RestBean;
import com.smallhowe.entity.mc.StatusResponse;
import com.smallhowe.service.MinecraftService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mc")
public class MinecraftController {
    @Resource
    private MinecraftService minecraftService;
    @GetMapping("/status")
    public RestBean<Object> getServerStatus() {
        StatusResponse status = minecraftService.getServerStatus();
        if (status == null) {
            return RestBean.failure(500, "服务器连接失败");
        }
        return RestBean.success("获取成功",status);
    }
}
