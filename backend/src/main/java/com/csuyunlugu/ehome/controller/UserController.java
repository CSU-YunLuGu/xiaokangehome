package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.dto.UserDTO;
import com.csuyunlugu.ehome.service.UserCreateService;
import com.csuyunlugu.ehome.service.UserLoginService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import com.csuyunlugu.ehome.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserLoginService userLoginService;
    private final UserCreateService userCreateService;

    public UserController(UserService userService, UserLoginService userLoginService, UserCreateService userCreateService) {
        this.userService = userService;
        this.userLoginService = userLoginService;
        this.userCreateService = userCreateService;
    }

    @PostMapping("/wxLogin")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByCode(@RequestParam @NotNull @NotBlank String code) {
        String openid = userLoginService.getOpenIdByCode(code);
        String token = JWTUtil.generateToken(openid);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        User user = userService.findUserByOpenid(openid);
        UserDTO userDTO = (user == null)
                ? userCreateService.createUser(openid)
                : UserDTO.convertToUserDTO(user);
        return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponse.success(userDTO));
    }

    @GetMapping
    public ApiResponse<UserDTO> getUser(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        UserDTO userDTO = UserDTO.convertToUserDTO(user);
        return ApiResponse.success(userDTO);
    }

    @GetMapping("/{id}")
    public ApiResponse<List<UserDTO>> getBothUser(HttpServletRequest request, @PathVariable Integer id) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        UserDTO userDTO = UserDTO.convertToUserDTO(user);
        User anotherUser = userService.findUserById(id);
        UserDTO anotherUserDTO = UserDTO.convertToUserDTO(anotherUser);
        return ApiResponse.success(List.of(userDTO, anotherUserDTO));
    }

    @PutMapping
    public ApiResponse<Boolean> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        Boolean success = userService.updateUser(user, userDTO);
        return ApiResponse.success(success);
    }

    @PostMapping
    public ApiResponse<String> updateAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        String url = userService.updateAvatar(user.getId(), avatar);
        return ApiResponse.success(url);
    }

    @PatchMapping
    public ApiResponse<Boolean> isBasicInfoCompleted(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        return ApiResponse.success(user.getName() != null && !user.getName().isEmpty());
    }
}