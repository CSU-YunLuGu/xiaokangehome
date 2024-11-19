package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName HealthData
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 09:56
 * @Version 1.0
 */
@Data
@TableName("health_data")
public class HealthData {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_at")
    private Timestamp createAt;
    @TableField("exercise_duration")
    private Integer exerciseDuration;
    @TableField("heart_rate")
    private Integer heartRate;
    @TableField("sleep_duration")
    private Integer sleepDuration;
    @TableField("systolic_pressure")
    private Integer systolicPressure;
    @TableField("diastolic_pressure")
    private Integer diastolicPressure;
}
