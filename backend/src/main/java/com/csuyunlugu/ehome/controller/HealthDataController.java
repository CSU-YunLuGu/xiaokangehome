package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.HealthDataDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.HealthDataService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HealthDataController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 09:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/analysis")
public class HealthDataController {
    private final UserService userService;
    private final HealthDataService healthDataService;

    public HealthDataController(UserService userService, HealthDataService healthDataService) {
        this.userService = userService;
        this.healthDataService = healthDataService;
    }

    @GetMapping
    public ApiResponse<List<HealthDataDTO>> getHealthData(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<HealthDataDTO> healthDataDTOList = healthDataService.getHealthData(user.getId());
        return ApiResponse.success(healthDataDTOList);
    }

    @PostMapping
    public ApiResponse<Boolean> addHealthData(@RequestBody @Validated HealthDataDTO healthDataDTO, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        Boolean recorded = healthDataService.addHealthData(user.getId(), healthDataDTO);
        return ApiResponse.success(recorded);
    }

}
