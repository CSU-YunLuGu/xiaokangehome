package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.DoctorDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.DoctorService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName DoctorController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/10 13:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final UserService userService;

    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public ApiResponse<List<DoctorDTO>> list(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<DoctorDTO> list = doctorService.getDoctorListByPatientId(user);
        return ApiResponse.success(list);
    }

    @GetMapping("/delete")
    public ApiResponse<List<DoctorDTO>> delete(HttpServletRequest request, @NotNull Integer id) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        doctorService.deleteDoctor(id, user);
        List<DoctorDTO> list = doctorService.getDoctorListByPatientId(user);
        return ApiResponse.success(list);
    }

    @GetMapping("/search")
    public ApiResponse<List<DoctorDTO>> search(HttpServletRequest request, @NotBlank String keyword) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<DoctorDTO> list = doctorService.getDoctorListByKeyword(user, keyword);
        return ApiResponse.success(list);
    }

    @GetMapping("/add")
    public ApiResponse<Boolean> add(HttpServletRequest request, @NotNull Integer id) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        doctorService.addDoctor(id, user);
        return ApiResponse.success(true);
    }
}
