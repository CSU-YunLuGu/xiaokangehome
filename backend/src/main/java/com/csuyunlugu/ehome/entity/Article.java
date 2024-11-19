package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Article
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/8 15:41
 * @Version 1.0
 */
@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("time")
    private Timestamp time;
    @TableField("title")
    private String title;
    @TableField("content")
    private String content;
    @TableField("author")
    private String author;
    @TableField("picture_url")
    private String pictureUrl;
}
