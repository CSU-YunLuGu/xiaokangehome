package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName Doctor
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/8 19:41
 * @Version 1.0
 */
@Data
@TableName("doctor")
public class Doctor {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("name")
    private String name;
    @TableField("major")
    private String major;
    @TableField("job")
    private String job;
    @TableField("certificate_pic_url")
    private String certificatePicUrl;
    @TableField("photo_url")
    private String photoUrl;
    @TableField("valid")
    private Boolean valid;
}
