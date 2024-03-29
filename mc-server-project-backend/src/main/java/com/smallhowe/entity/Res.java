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
 * @since 2024-03-23
 */
@Data
@TableName("db_res")
public class Res implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名
     */
    private String title;

    /**
     * 文件大小
     */
    private String size;

    private String url;

    private String path;

    private String version;

    private String content;

    private LocalDateTime updateTime;

    private String operator;
}
