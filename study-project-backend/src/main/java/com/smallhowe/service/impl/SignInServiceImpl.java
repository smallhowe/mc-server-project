package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.SignIn;
import com.smallhowe.mapper.SignInMapper;
import com.smallhowe.service.SignInService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@Service
public class SignInServiceImpl implements SignInService {
    //用户刷新签到的时间
    public static final int SIGN_IN_HOUR = 0;
    public static final int SIGN_IN_MINUTE = 0;


    @Resource
    private SignInMapper signInMapper;

    /**
     * 判断用户今日是否已经签到
     * @param account 用户
     * @return 今日签到过返回True，今日未签到返回False
     */
    @Override
    public boolean isSignIn(Account account) {
        LocalDateTime today = LocalDateTime.now();
        today = today.withHour(SIGN_IN_HOUR).withMinute(SIGN_IN_MINUTE).withSecond(0).withNano(0);
        LocalDateTime tomorrow = today.plusDays(1);

        LambdaQueryWrapper<SignIn> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SignIn::getAccountId, account.getId())
                .between(SignIn::getDateTime, today, tomorrow);
//        System.out.println("查询从" + today + "至" + tomorrow + "的签到记录");
        SignIn signIn = signInMapper.selectOne(lqw);

        return signIn != null;
    }

    @Override
    public void saveSignIn(Integer accountId, Integer getExp) {
        SignIn signIn = new SignIn();
        signIn.setAccountId(accountId);
        signIn.setGetExp(getExp);
        signInMapper.insert(signIn);
    }
}
