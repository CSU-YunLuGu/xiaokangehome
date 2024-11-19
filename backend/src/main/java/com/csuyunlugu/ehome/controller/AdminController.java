package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.*;
import com.csuyunlugu.ehome.entity.Admin;
import com.csuyunlugu.ehome.entity.Doctor;
import com.csuyunlugu.ehome.exception.LoginAuthException;
import com.csuyunlugu.ehome.exception.LoginException;
import com.csuyunlugu.ehome.service.AdminService;
import com.csuyunlugu.ehome.service.ArticleService;
import com.csuyunlugu.ehome.service.DoctorService;
import com.csuyunlugu.ehome.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/12 10:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final ArticleService articleService;

    public AdminController(AdminService adminService, UserService userService, DoctorService doctorService, ArticleService articleService) {
        this.adminService = adminService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.articleService = articleService;
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody @Validated AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();
        String token = adminService.login(username, password);
        if (token == null) {
            throw LoginException.loginFailed(401, "用户名或密码错误");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(ApiResponse.success());
    }

    @GetMapping("/user")
    public ApiResponse<List<UserDTO>> userList(HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        List<UserDTO> users = userService.findUsers();
        return ApiResponse.success(users);
    }

    @GetMapping("/audit")
    public ApiResponse<List<Doctor>> audit(HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        List<Doctor> doctorsNotAudit = doctorService.findDoctorsNotAudit();
        return ApiResponse.success(doctorsNotAudit);
    }

    @PostMapping("/audit")
    public ApiResponse<Object> audit(@NotNull Integer doctorId, @NotNull Boolean valid, HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        doctorService.audit(doctorId, valid);
        return ApiResponse.success();
    }

    @PutMapping("/article")
    public ApiResponse<Object> uploadArticle(@Validated @RequestBody AdminArticleDTO adminArticleDTO, HttpServletRequest request) {
        Admin admin = adminService.getAdmin(request);
        if (admin == null) {
            throw LoginAuthException.unauthorized();
        }
        String title = adminArticleDTO.getTitle();
        String content = adminArticleDTO.getContent();
        MultipartFile file = adminArticleDTO.getFile();
        articleService.uploadArticle(admin, title, content, file);
        return ApiResponse.success();
    }

    @GetMapping("/article")
    public ApiResponse<List<ArticleDTO>> articleList(HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        List<ArticleDTO> articles = articleService.findArticles();
        return ApiResponse.success(articles);
    }

    @PostMapping("/article/{id}")
    public ApiResponse<Object> updateArticle(@PathVariable @NotNull Integer id,
                                             @Validated @RequestBody AdminArticleDTO adminArticleDTO,
                                             HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        String title = adminArticleDTO.getTitle();
        String content = adminArticleDTO.getContent();
        MultipartFile file = adminArticleDTO.getFile();
        articleService.updateArticle(id, title, content, file);
        return ApiResponse.success();
    }

    @DeleteMapping("/article/{id}")
    public ApiResponse<Object> deleteArticle(@PathVariable @NotNull Integer id, HttpServletRequest request) {
        if (adminService.getAdmin(request) == null) {
            throw LoginAuthException.unauthorized();
        }
        articleService.deleteArticle(id);
        return ApiResponse.success();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
