package com.csuyunlugu.ehome.dto;

import lombok.Data;

/**
 * @ClassName ApiResponse
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:53
 * @Version 1.0
 */
@Data
public class ApiResponse<T> {
    private Integer code;
    private String message = "";
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(0);
        apiResponse.setMessage("success");
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        return apiResponse;
    }
}
