package com.smallhowe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

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

    private Integer groups;

    private Long exp;

    private String avatarUrl;

    private String avatarPath;

    private LocalDateTime createTime;

}
