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
 * @since 2024-02-10
 */
@Data
@TableName("db_sign_in")
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer accountId;

    /**
     * 用户获得的经验
     */
    private Integer getExp;

    private LocalDateTime dateTime;
}
