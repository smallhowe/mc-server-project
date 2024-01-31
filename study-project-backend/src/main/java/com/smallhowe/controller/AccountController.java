package com.smallhowe.controller;

import com.smallhowe.entity.RestBean;
import com.smallhowe.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author smallhowe
 * @since 2024-01-20
 */
@RestController
@Validated
@RequestMapping("/api/auth")
public class AccountController {
    private final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9一-龥]+$";
    @Resource
    private AccountService accountService;
    @Resource
    private StringRedisTemplate template;

    @PostMapping("/valid-email")
    public RestBean<Object> validateRegisterEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam String email, HttpSession session) {
        int status = accountService.sendValidateEmail(email, session.getId(),false);
        Long expire = Optional.ofNullable(template.getExpire("email:" + session.getId() + ":" + email + ":false")).orElse(0L);
        if (expire <= 120L) expire = 0L;
        Map<String, Object> map = new HashMap<>();
        map.put("expire",expire);

        return switch (status) {
            case 0 -> RestBean.failure(401, "邮件已发送,请勿频繁发送！", map);
            case 1 -> RestBean.success("已发送验证邮件，请注意查收");
            case 2 -> RestBean.failure(400, "邮箱已注册");
            case 3 -> RestBean.failure(400, "邮箱未注册");
            default -> RestBean.failure(500, "发送邮件失败，请联系管理员");
        };
    }
    @PostMapping("/valid-reset-email")
    public RestBean<Object> validateResetEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam String email, HttpSession session) {
        int status = accountService.sendValidateEmail(email, session.getId(),true);
        Long expire = Optional.ofNullable(template.getExpire("email:" + session.getId() + ":" + email + ":true")).orElse(0L);
        if (expire <= 120L) expire = 0L;
        Map<String, Object> map = new HashMap<>();
        map.put("expire",expire);


        return switch (status) {
            case 0 -> RestBean.failure(401, "邮件已发送,请勿频繁发送！",map);
            case 1 -> RestBean.success("已发送验证邮件，请注意查收");
            case 2 -> RestBean.failure(400, "邮箱已注册");
            case 3 -> RestBean.failure(400, "邮箱未注册");
            default -> RestBean.failure(500, "发送邮件失败，请联系管理员");
        };
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 2, max = 10) @RequestParam String username,
                                         @Length(min = 6, max = 16) @RequestParam String password,
                                         @Pattern(regexp = EMAIL_REGEX) @RequestParam String email,
                                         @Length(min = 6,max = 6) @RequestParam String code,
                                         HttpSession session) {
        int status = accountService.validateAndRegister(username, password, email, code,session.getId());
        return switch (status){
            case 0 -> RestBean.failure(400, "该邮箱已被注册");
            case 1 -> RestBean.success("注册成功");
            case 2 -> RestBean.failure(400, "请先获取验证码后再注册");
            case 3 -> RestBean.failure(400, "验证码错误,请重试");
            case 4 -> RestBean.failure(401, "用户名已被占用");
            default -> RestBean.failure(500, "注册失败，请联系管理员");
        };
    }

    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EMAIL_REGEX) @RequestParam String email,
                                               @Length(min = 6,max = 6) @RequestParam String code,
                                               HttpSession session) {
        String s = accountService.validateOnly(email, code, session.getId());
        if (s == null) {
            session.setAttribute("reset-password", email);
            return RestBean.success("验证成功");
        }else{
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Length(min = 6,max = 16) @RequestParam String password,
                                          HttpSession session){
        String email = "";
        try{
            email = session.getAttribute("reset-password").toString();
        }catch(Exception e){
            return RestBean.failure(500, "请重新验证邮箱");
        }
        if (email == null) {
            return RestBean.failure(401, "请先完成邮箱验证");
        }else if (accountService.resetPassword(password,email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        }else {
            return RestBean.failure(500, "密码重置失败，请联系管理员");
        }
    }
}
