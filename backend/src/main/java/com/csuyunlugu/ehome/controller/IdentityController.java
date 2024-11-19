package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.DoctorIdentityDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.DoctorService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.util.FileUploadUtil;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName IdentityController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 16:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/identity")
public class IdentityController {
    private final UserService userService;
    private final DoctorService doctorService;

    public IdentityController(UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @PostMapping("/certificate")
    public ApiResponse<String> uploadCertificate(@RequestParam("certificate") MultipartFile certificate) {
        String url = FileUploadUtil.storeFile(certificate, "certificate");
        return ApiResponse.success(url);
    }

    @PostMapping("/photo")
    public ApiResponse<String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        String url = FileUploadUtil.storeFile(photo, "photo");
        return ApiResponse.success(url);
    }

    @PutMapping("/submit")
    public ApiResponse<Object> submitInfo(@RequestBody @Validated DoctorIdentityDTO doctorIdentityDTO, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        doctorService.submitInfo(user, doctorIdentityDTO);
        return ApiResponse.success();
    }

    @GetMapping("/info")
    public ApiResponse<DoctorIdentityDTO> getInfo(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        return ApiResponse.success(doctorService.getInfo(user));
    }

    @GetMapping
    public ApiResponse<Boolean> identity(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        return ApiResponse.success(doctorService.identity(user));
    }
}
