package com.smallhowe.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smallhowe.entity.Account;
import com.smallhowe.mapper.DbAccountMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    private DbAccountMapper mapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        User user = (User) authentication.getPrincipal();
        LambdaQueryWrapper<Account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Account::getUsername, user.getUsername());
        Account account = mapper.selectOne(lqw);
        request.getSession().setAttribute("account",account);

        return true;
    }

}
