package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.csuyunlugu.ehome.dao.DoctorMapper;
import com.csuyunlugu.ehome.dto.UserDTO;
import com.csuyunlugu.ehome.entity.Doctor;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.dao.UserMapper;
import com.csuyunlugu.ehome.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:49
 * @Version 1.0
 */
@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }

    public User findUserByOpenid(String openid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        return userMapper.selectOne(queryWrapper);
    }

    public List<UserDTO> findUsers() {
        return userMapper.selectList(null)
                .stream()
                .map(UserDTO::convertToUserDTO)
                .toList();
    }

    public Boolean updateUser(User user, UserDTO userDTO) {
        String name = userDTO.getName();
        User oldUser = userMapper.selectById(user.getId());
        Integer doctorId = user.getDoctorId();
        if (name != null && doctorId != null && !name.equals(oldUser.getName())) {
            // 已经注册过医生 不能修改医生姓名
            return false;
        }
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setGender(userDTO.getGender());
        user.setBirthday(userDTO.getBirthday());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setMedicalHistory(userDTO.getMedicalHistory());
        user.setAllergyHistory(userDTO.getAllergyHistory());
        userMapper.updateById(user);
        return true;
    }

    public String updateAvatar(Integer id, MultipartFile avatar) {
        String avatarUrl = FileUploadUtil.storeFile(avatar, "avatar");
        User user = new User();
        user.setAvatar(avatarUrl);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        userMapper.update(user, updateWrapper);
        return avatarUrl;
    }
}
