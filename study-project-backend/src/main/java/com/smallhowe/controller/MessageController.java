package com.smallhowe.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.Message;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Resource
    private MessageService messageService;
    @GetMapping("/list")
    public RestBean<Object> getUserMessageList(@SessionAttribute("account")Account account, Long current){
        Page<Message> page = new Page<>(current,10);
        IPage<Message> msgPage=messageService.getMessageList(account,page);
        if (msgPage.getTotal()==0) return RestBean.failure(404, "暂时没有消息");
        return RestBean.success(null,msgPage);
    }
}
