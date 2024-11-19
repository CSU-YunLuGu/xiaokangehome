package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName MedicineDiary
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 15:46
 * @Version 1.0
 */
@Data
@TableName("medicine_diary")
public class MedicineDiary {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_at")
    private Timestamp createAt;
    @TableField("content")
    private String content;
}
