package com.csuyunlugu.ehome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName DiaryUpdateDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 15:23
 * @Version 1.0
 */
@Data
public class DiaryAddDTO {
    // 记录类型 吃药 true 看病 false
    @NotNull
    private Boolean recordType;
    // 记录内容
    @NotBlank
    private String detail;
}
