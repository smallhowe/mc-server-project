package com.smallhowe.controller;

import com.smallhowe.entity.RestBean;
import com.smallhowe.service.DbAccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
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
    @Resource
    private DbAccountService accountService;
    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam String email, HttpSession session) {
        int status = accountService.sendValidateEmail(email, session.getId());
        return switch (status) {
            case 0 -> RestBean.success("验证邮件已发送");
            case 1 -> RestBean.success("已发送验证邮件，请注意查收");
            default -> RestBean.failure(400, "发送邮件失败");
        };
    }

}
