package com.smallhowe.service.impl;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.RestBean;
import com.smallhowe.mapper.AccountMapper;
import com.smallhowe.service.UserService;
import com.smallhowe.utils.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Resource
    private AccountMapper accountMapper;
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
}
