package com.smallhowe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 
 * </p>
 *
 * @author smallhowe
 * @since 2024-02-10
 */
@Data
@TableName("db_carousel")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String imgPath;

    private String imgUrl;

    public Carousel() {
    }

    public Carousel(String title, String content, String imgPath, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }
}
