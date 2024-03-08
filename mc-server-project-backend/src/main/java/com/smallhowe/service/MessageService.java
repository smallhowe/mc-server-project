package com.smallhowe.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-24
 */
public interface MessageService{
    public IPage<Message> getMessageList(Account account, IPage<Message> page);
}
