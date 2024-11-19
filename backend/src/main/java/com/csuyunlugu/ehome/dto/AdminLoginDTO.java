package com.csuyunlugu.ehome.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @ClassName AdminLoginDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/13 09:42
 * @Version 1.0
 */
@Data
public class AdminLoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
