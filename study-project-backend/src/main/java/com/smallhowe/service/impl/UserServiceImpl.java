package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.Levels;
import com.smallhowe.mapper.AccountMapper;
import com.smallhowe.service.SignInService;
import com.smallhowe.service.UserService;
import com.smallhowe.utils.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
    private static final int MAX_AVATAR_SIZE = 200; //头像最大像素
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private SignInService signInService;
    @Resource
    private ImageUtils imageUtils;
    @Override
    public int uploadAvatar(MultipartFile avatar, Account account) {


        if (!checkAvatarSize(avatar)) return -1;

        Map<String, String> imgSaveInfo = imageUtils.saveAvatar(avatar);

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

    /**
     *
     * @param account 要绑定的账号
     * @param gameId 要绑定的游戏ID
     * @return
     * -1:已经绑定过相同的游戏ID;
     * -2:当前用户ID已经被绑定;
     * 0:绑定失败
     * 1:绑定成功
     */
    @Override
    public int bindGameId(Account account, String gameId) {

        //判断绑定的游戏ID是否与账户本身绑定的相同
        if (account.getGameId()!=null){
            if (account.getGameId().equals(gameId)) return -1;
        }

        //判断当前用户ID是否已经被绑定
        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getGameId, gameId);
        Account selectOne = accountMapper.selectOne(lqw);
        if (selectOne!=null) return -2;

        account.setGameId(gameId);

        return accountMapper.updateById(account);
    }

    @Override
    public int updatePassword(Account account) {
        return accountMapper.updateById(account);
    }

    private boolean checkAvatarSize(MultipartFile avatar) {
        // 将MultipartFile转换为InputStream
        InputStream inputStream = null;
        BufferedImage image = null;
        try {
            inputStream = avatar.getInputStream();
            // 从输入流创建BufferedImage对象
            image = ImageIO.read(inputStream);
            // 获取图片的宽度和高度
            int width = image.getWidth();
            int height = image.getHeight();

            // 现在你可以使用width和height变量了，比如存储到数据库或者验证尺寸是否符合要求
            System.out.println("图片的宽度是: " + width);
            System.out.println("图片的高度是: " + height);

            // 关闭输入流（处理完后记得关闭资源）
            inputStream.close();
            if (width>MAX_AVATAR_SIZE || height>MAX_AVATAR_SIZE) return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
