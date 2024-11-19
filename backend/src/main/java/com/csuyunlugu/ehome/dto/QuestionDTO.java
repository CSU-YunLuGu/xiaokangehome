package com.csuyunlugu.ehome.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName QuestionDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 10:43
 * @Version 1.0
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String username;
    private String time;
    private String question;
    private List<AnswerDTO> answer;
}
