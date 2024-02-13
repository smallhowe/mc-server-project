package com.smallhowe.service.impl;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.Levels;
import com.smallhowe.mapper.AccountMapper;
import com.smallhowe.service.SignInService;
import com.smallhowe.service.UserService;
import com.smallhowe.utils.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-06
 */
@Service
public class UserServiceImpl implements UserService {
    public static final int SIGN_IN_EXP = 50;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private SignInService signInService;
    @Resource
    private ImageUtils imageUtils;
    @Override
    public int uploadAvatar(MultipartFile avatar, Account account) {
        if(avatar.isEmpty()) return 0;


        //上传文件
        String filePath = System.getProperty("user.dir")+"\\study-project-backend\\src\\main\\resources\\static\\images\\user_avatar\\";
        Map<String, String> imgSaveInfo = imageUtils.saveAvatar(avatar, filePath);
        System.out.println(imgSaveInfo.get("url"));
        System.out.println(imgSaveInfo.get("path"));
        if (imgSaveInfo.isEmpty()) return 0;

        //判断用户是否有头像,如果有则删除
        if (account.getAvatarPath() != null) {
            imageUtils.deleteImage(account.getAvatarPath());
        }

        String url = imgSaveInfo.get("url");
        String path = imgSaveInfo.get("path");
        Account accountAvatar = new Account();
        accountAvatar.setId(account.getId());
        accountAvatar.setAvatarPath(path);
        accountAvatar.setAvatarUrl(url);
        accountMapper.updateById(accountAvatar);

        return 1;
    }

    @Override
    public int signIn(Account account) {

        //今日有签到记录则返回false表示已签到
        if (signInService.isSignIn(account)) return 0;

        //判断用户是否达到最大等级
        Levels maxLevel = Levels.getMaxLevel();
        if (account.getExp()>= maxLevel.getExp()) {
            return 2;
        }

        //为用户设置签到经验值
        Account signInAccount = new Account();
        signInAccount.setId(account.getId());
        signInAccount.setExp(account.getExp() + SIGN_IN_EXP);
        accountMapper.updateById(signInAccount);

        //保存签到记录
        signInService.saveSignIn(signInAccount.getId(), SIGN_IN_EXP);


        return 1;
    }
}
