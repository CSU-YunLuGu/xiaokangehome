package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName UserDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:58
 * @Version 1.0
 */
@Data
public class UserDTO {
    private String username;
    private Integer id;
    private String name;
    private String avatar;
    private Boolean gender;
    private String birthday;
    private Integer height;
    private Integer weight;
    private String medicalHistory;
    private String allergyHistory;

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
