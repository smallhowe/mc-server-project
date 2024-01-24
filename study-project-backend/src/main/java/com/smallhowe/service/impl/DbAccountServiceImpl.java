package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Account;
import com.smallhowe.mapper.DbAccountMapper;
import com.smallhowe.service.DbAccountService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-01-20
 */
@Service
public class DbAccountServiceImpl implements DbAccountService {
    @Resource
    private DbAccountMapper mapper;
    @Resource
    private MailSender mailSender;
    @Resource
    private StringRedisTemplate template;
    @Value("${spring.mail.username}")
    private String from;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username==null)
            throw new UsernameNotFoundException("用户名不能为空");

        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getUsername, username)
                .or()
                .eq(Account::getEmail, username);
        Account account = mapper.selectOne(lqw);

        if (account==null)
            throw new UsernameNotFoundException("用户不存在");

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    /**
     * 验证码发送模块
     * 返回值对应：
     * 1=发送 0=已发送 -1=异常
     */
    @Override
    public int sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key,TimeUnit.SECONDS)).orElse(0L);
            if (expire>0){
                return 0;
            }
        }
        Random random = new Random();
        int code = random.nextInt(89999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是："+code);
        try{
            mailSender.send(message);

            template.opsForValue().set(key, String.valueOf(code),3, TimeUnit.MINUTES);
        }catch (MailException e){
            e.printStackTrace();
            return -1;
        }
        return 1;

    }
}
