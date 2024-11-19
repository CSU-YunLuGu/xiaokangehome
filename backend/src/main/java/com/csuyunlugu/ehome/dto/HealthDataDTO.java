package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.entity.HealthData;
import com.csuyunlugu.ehome.util.TimeUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName HealthDataDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 09:48
 * @Version 1.0
 */
@Data
public class HealthDataDTO {
    @NotNull @Range(min = 0, max = 1440)
    private Integer exerciseDuration;
    @NotNull @Range(min = 0, max = 999)
    private Integer heartRate;
    @NotNull @Range(min = 0, max = 24)
    private Integer sleepDuration;
    @NotNull @Range(min = 0, max = 999)
    private Integer systolicPressure;
    @NotNull @Range(min = 0, max = 999)
    private Integer diastolicPressure;
    private String date;
}
