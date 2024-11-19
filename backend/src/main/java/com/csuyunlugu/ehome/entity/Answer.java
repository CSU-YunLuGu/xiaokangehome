package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Answer
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 10:55
 * @Version 1.0
 */
@Data
@TableName("answer")
public class Answer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("question_id")
    private Integer questionId;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_at")
    private Timestamp createAt;
    @TableField("answer")
    private String answer;
}
