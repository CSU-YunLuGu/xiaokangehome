package com.csuyunlugu.ehome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName AdminArticleDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/13 09:45
 * @Version 1.0
 */
@Data
public class AdminArticleDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private MultipartFile file;
}
