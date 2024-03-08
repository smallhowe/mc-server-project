package com.smallhowe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-24
 */
@Data
@TableName("db_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    /**
     * 指定接收人的账户id
     */
    private String accountId;

    /**
     * 是否为所有人接收消息 1=所有人接收 0=非所有人接收
     */
    private Integer isAll;

    private LocalDateTime createTime;
}
