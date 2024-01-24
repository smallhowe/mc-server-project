package com.smallhowe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author smallhowe
 * @since 2024-01-20
 */
@Data
@TableName("db_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String email;

    private String username;

    private String password;
}
