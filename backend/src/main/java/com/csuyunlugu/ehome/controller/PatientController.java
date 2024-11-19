package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.PatientService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName PatientController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/10 17:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final UserService userService;
    private final PatientService patientService;

    public PatientController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }

    @GetMapping
    public ApiResponse<List<User>> getPatient(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User doctor = userService.findUserByOpenid(openid);
        List<User> patientList = patientService.getPatientList(doctor);
        return ApiResponse.success(patientList);
    }
}
