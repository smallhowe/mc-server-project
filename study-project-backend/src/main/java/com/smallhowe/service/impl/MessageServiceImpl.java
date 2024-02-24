package com.smallhowe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smallhowe.entity.Account;
import com.smallhowe.entity.Message;
import com.smallhowe.mapper.MessageMapper;
import com.smallhowe.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-24
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public IPage<Message> getMessageList(Account account, IPage<Message> page) {
        LambdaQueryWrapper<Message> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Message::getIsAll,1).or().eq(Message::getAccountId,account.getId());

        return messageMapper.selectPage(page,lqw);
    }
}
