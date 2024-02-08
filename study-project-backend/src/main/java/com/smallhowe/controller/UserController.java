package com.smallhowe.controller;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/me")
    public RestBean<Object> me(@SessionAttribute("account") Account account){
        account.setPassword(null);
        return RestBean.success(null,account);
    }

    @PostMapping("/upload/avatar")
    public RestBean<String> uploadAvatar(MultipartFile avatar, @SessionAttribute("account") Account account){
        if(avatar.isEmpty()){
            return RestBean.failure(400,"文件为空");
        }
        int flag = userService.uploadAvatar(avatar,account);


        return switch (flag) {
            case 0 -> RestBean.failure(400, "上传失败");
            case 1 -> RestBean.success("上传成功");
            default -> RestBean.failure(400, "未知错误");
        };
    }

}
