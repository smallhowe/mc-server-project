package com.smallhowe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.Levels;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/me")
    public RestBean<Object> me(@SessionAttribute("account") Account account){
        account.setPassword(null);
        account.setAvatarPath(null);
        List<Levels> initLevels = Levels.initLevels();
        account.setLevelList(initLevels);

        Integer maxLevel = initLevels.get(initLevels.size() - 1).getLevel();

        for (int i = initLevels.size() - 1; i >= 0; i--) {
            if (account.getExp() >= initLevels.get(i).getExp()) {
                account.setLevel(initLevels.get(i).getLevel());
                if (account.getLevel().equals(maxLevel)){
                    account.setNextExp(0L);
                }else{
                    account.setNextExp(initLevels.get(i+1).getExp());
                }
                break;
            }
        }


        return RestBean.success(null,account);
    }
    //签到
    @GetMapping("/sign")
    public RestBean<Object> signIn(@SessionAttribute("account") Account account){
        boolean flag = userService.signIn(account);
        if (!flag) return RestBean.failure(400,"今日已签到");
        Map<String, Integer> result = new HashMap<>();
        result.put("getExp", 50);
        return RestBean.success("签到成功",result);
    }

    //上传头像
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
