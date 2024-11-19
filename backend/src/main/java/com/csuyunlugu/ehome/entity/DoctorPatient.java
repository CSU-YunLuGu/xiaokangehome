package com.csuyunlugu.ehome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName DoctorPatient
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/10 13:34
 * @Version 1.0
 */
@Data
@TableName("doctor_patient")
public class DoctorPatient {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("patient_id")
    private Integer patientId;
}
