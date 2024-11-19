package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName User
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:52
 * @Version 1.0
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    private String username;
    @TableField("name")
    private String name;
    @TableField("gender")
    private Boolean gender;
    @TableField("birthday")
    private String birthday;
    @TableField("openid")
    private String openid;
    @TableField("create_at")
    private Timestamp createAt;
    @TableField("avatar")
    private String avatar;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("height")
    private Integer height;
    @TableField("weight")
    private Integer weight;
    @TableField("medical_history")
    private String medicalHistory;
    @TableField("allergy_history")
    private String allergyHistory;
}
