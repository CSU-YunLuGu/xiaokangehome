package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.AdminMapper;
import com.csuyunlugu.ehome.entity.Admin;
import com.csuyunlugu.ehome.entity.Doctor;
import com.csuyunlugu.ehome.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AdminService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/12 16:32
 * @Version 1.0
 */
@Service
public class AdminService {
    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    private String hashPassword(@NotBlank String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Admin getAdmin(HttpServletRequest request) {
        String username = request.getAttribute("subject").toString();
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return adminMapper.selectOne(queryWrapper);
    }

    private Boolean checkPassword(@NotBlank String password, @NotBlank String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String login(@NotBlank String username, @NotBlank String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin == null || !checkPassword(password, admin.getPassword())) {
            return null;
        }
        return JWTUtil.generateToken(admin.getUsername());
    }
}
