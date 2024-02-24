package com.smallhowe.mapper;

import com.smallhowe.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-24
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
