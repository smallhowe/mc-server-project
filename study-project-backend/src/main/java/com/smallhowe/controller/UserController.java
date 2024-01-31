package com.smallhowe.controller;

import com.smallhowe.entity.Account;
import com.smallhowe.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public RestBean<Object> me(@SessionAttribute("account") Account account){
        account.setPassword(null);
        return RestBean.success(null,account);
    }
}
