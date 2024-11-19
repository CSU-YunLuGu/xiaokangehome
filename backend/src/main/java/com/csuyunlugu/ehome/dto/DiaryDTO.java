package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName DiaryDetailDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 15:05
 * @Version 1.0
 */
@Data
public class DiaryDTO {
    // 提交记录的时间
    private String date;
    // 提交记录的详情
    private String detail;

    public static DiaryDTO convertToDiaryDTO(Timestamp createAt, String content) {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDate(TimeUtil.formatTime(createAt));
        diaryDTO.setDetail(content);
        return diaryDTO;
    }
}
