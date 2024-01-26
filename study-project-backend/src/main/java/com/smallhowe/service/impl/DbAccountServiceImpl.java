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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Value("${spring.mail.username}")
    private String from;

    public boolean checkRedisCodeExpired(String pattern){
        Set<String> keys = null;
        keys = template.keys(pattern);
        if (keys.size() > 0) {
            boolean flag=false;
            for (String k : keys){
                Long expire = Optional.ofNullable(template.getExpire(k)).orElse(0L);
                //如果过期时间<=120s则表示通过验证，可以发送邮件
                if (expire<=120){
                    flag = true;
                    break;
                }
            }
            return flag;
        }
        return true;
    }
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
     * 3=未注册
     * 2=已注册
     * 1=发送
     * 0=已发送
     * -1=异常
     */
    @Override
    public int sendValidateEmail(String email, String sessionId, boolean hasAccount) {

        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;
        //判断是否已获取验证码
        String pattern = "email:" + sessionId + ":*";
        //判断是否本机已发起过
        if (!checkRedisCodeExpired(pattern))
            return 0;
        //判断该邮箱是否获取过
        pattern = "email:*:" + email;
        if (!checkRedisCodeExpired(pattern))
            return 0;

        //判断邮箱是否已经注册,已注册则返回2,未注册返回3
        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getEmail, email);
        Account account = mapper.selectOne(lqw);
        if (hasAccount && account == null) {
            return 3;
        } else if (!hasAccount && account != null) {
            return 2;
        }


        Random random = new Random();
        int code = random.nextInt(89999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是：" + code);
        try {

            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
        } catch (MailException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;

    }

    /**
     * 注册模块
     * 返回值对应:
     * 3=验证码错误
     * 2=未获取验证码
     * 1=注册成功
     * 0=已被注册
     * -1=异常
     */
    @Override
    public int validateAndRegister(String username, String password, String email, String code, String sessionId) {

        //判断该邮箱是否获取过验证码
        Set<String> keys = null;
        keys = template.keys("email:*:" + email + ":false");
        if (keys.size() < 1) {
            return 2;
        }

        //判断邮箱是否被注册
        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getEmail, email);
        Account account = mapper.selectOne(lqw);
        if (account!=null)
            return 0;

        /*
           检查输入的验证码与Redis存储的邮箱验证码是否一致
           如果都不一致则flagCode会=null
           如果一致则flagCode会=code
         */
        String flagCode = null;
        for (String k : keys){
            flagCode=template.opsForValue().get(k);
            if (!flagCode.equals(code)){
                flagCode = null;
            }else{
                template.delete(k);
                break;
            }
        }
        //判断flagCode是否为null,如果为null则表示验证码错误
        if (flagCode == null) {
            return 3;
        }

        //所有验证通过后注册
        password = passwordEncoder.encode(password);
        account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(password);
        int status = mapper.insert(account);
        return status > 0 ? 1 : -1;
    }

    @Override
    public String validateOnly(String email, String code,String sessionId) {
        String key="email:"+sessionId+":"+email+":true";

        //判断该邮箱是否获取过验证码
        Set<String> keys = null;
        keys = template.keys("email:*:" + email + ":true");
        if (keys.size() < 1) {
            return "请先获取验证码";
        }

        /*
           检查输入的验证码与Redis存储的邮箱验证码是否一致
           如果都不一致则flagCode会=null
           如果一致则flagCode会=code
         */
        String flagCode = null;
        for (String k : keys){
            flagCode=template.opsForValue().get(k);
            if (!flagCode.equals(code)){
                flagCode = null;
            }else{
                template.delete(k);
                break;
            }
        }
        //判断flagCode是否为null,如果为null则表示验证码错误
        if (flagCode == null) {
            return "验证码错误";
        }
        return null;

    }

    @Override
    public boolean resetPassword(String password, String email) {
        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getEmail, email);
        Account account = new Account();
        password = passwordEncoder.encode(password);
        account.setPassword(password);
        return mapper.update(account, lqw) > 0;
    }
}
