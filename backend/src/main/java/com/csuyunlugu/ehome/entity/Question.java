package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Question
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 10:50
 * @Version 1.0
 */
@Data
@TableName("question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_at")
    private Timestamp createAt;
    @TableField("question")
    private String question;
}
