package com.smallhowe.controller;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Levels;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.UserService;
import com.smallhowe.service.impl.SignInServiceImpl;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final String MC_ID_REGEX = "^[a-zA-Z_]+$";
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
        int flag = userService.signIn(account);
        Map<String, Object> result = new HashMap<>();
        switch (flag){
            case 0:
                result.put("nextDate", getTomorrowSignInDate());
                return RestBean.failure(400,"今日已签到",result);
            case 1:
                result.put("getExp", 50);
                result.put("nextDate", getTomorrowSignInDate());
                return RestBean.success("签到成功",result);
            case 2:
                return RestBean.success("已达最大等级，无需再签到");
            default:
                return RestBean.failure(400,"未知错误");
        }

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

    //绑定游戏ID
    @PostMapping("/bind")
    public RestBean<String> bindGameId(@SessionAttribute("account") Account account,@Pattern(regexp = MC_ID_REGEX) String gameId){
        int flag=userService.bindGameId(account, gameId);
        return flag>0?RestBean.success("绑定成功"):RestBean.failure(400,"绑定失败");
    }

    public RestBean<String> updatePassword(@SessionAttribute("account") Account account,
                                           @Length(min = 6,max = 16) String oldPassword,
                                           @Length(min = 6,max = 16) String newPassword) {
        if(!account.getPassword().equals(oldPassword)){
            return RestBean.failure(400,"旧密码错误");
        }
        if(newPassword.equals(oldPassword)){
            return RestBean.failure(400,"新密码不能与旧密码相同");
        }
        account.setPassword(newPassword);
        int flag = userService.updatePassword(account);
        return flag>0?RestBean.success("修改成功"):RestBean.failure(400,"修改失败");
    }


    private long getTomorrowSignInDate(){
        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.withHour(SignInServiceImpl.SIGN_IN_HOUR)
                .withMinute(SignInServiceImpl.SIGN_IN_MINUTE)
                .withSecond(0)
                .withNano(0)
                .plusDays(1);
        Instant tomorrow = ldt.atZone(ZoneOffset.systemDefault()).toInstant();
        return tomorrow.toEpochMilli();
    }
}
