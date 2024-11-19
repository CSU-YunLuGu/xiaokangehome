package com.csuyunlugu.ehome.service;

import com.csuyunlugu.ehome.dao.UserMapper;
import com.csuyunlugu.ehome.dto.UserDTO;
import com.csuyunlugu.ehome.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @ClassName UserCreateService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 09:47
 * @Version 1.0
 */
@Service
public class UserCreateService {
    private final UserMapper userMapper;

    @Value("${user.default.username}")
    private String defaultUsername;
    @Value("${user.default.avatar}")
    private String defaultAvatar;

    public UserCreateService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDTO createUser(String openid) {
        User user = new User();
        user.setId(null);
        user.setOpenid(openid);
        user.setUsername(generateDefaultUsername());
        user.setAvatar(defaultAvatar);
        System.out.println("create user: " + user);
        userMapper.insert(user);
        return UserDTO.convertToUserDTO(user);
    }

    private String generateDefaultUsername() {
        Random random = new Random();
        // 生成一个介于 10000 到 99999 之间的随机数
        int number = random.nextInt(90000) + 10000;
        return defaultUsername + number;
    }

}
