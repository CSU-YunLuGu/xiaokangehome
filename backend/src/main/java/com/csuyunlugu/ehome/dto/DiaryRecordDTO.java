package com.csuyunlugu.ehome.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DiaryDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 14:56
 * @Version 1.0
 */
@Data
public class DiaryRecordDTO {
    // 用户的吃药记录
    private List<DiaryDTO> medicineList;
    // 用户的看病记录
    private List<DiaryDTO> consultList;
}
