package com.smallhowe.controller;

import com.smallhowe.entity.RestBean;
import com.smallhowe.service.DbAccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class DbAccountController {
    private final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9一-龥]+$";
    @Resource
    private DbAccountService accountService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam String email, HttpSession session) {
        int status = accountService.sendValidateEmail(email, session.getId());
        return switch (status) {
            case 0 -> RestBean.success("验证邮件已发送");
            case 1 -> RestBean.success("已发送验证邮件，请注意查收");
            case 2 -> RestBean.failure(400, "邮箱已注册");
            default -> RestBean.failure(400, "发送邮件失败，请联系管理员");
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
            default -> RestBean.failure(400, "注册失败，请联系管理员");
        };
    }
}
