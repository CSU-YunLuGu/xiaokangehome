package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.DoctorPatientMapper;
import com.csuyunlugu.ehome.dao.UserMapper;
import com.csuyunlugu.ehome.entity.DoctorPatient;
import com.csuyunlugu.ehome.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PatientService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/10 17:42
 * @Version 1.0
 */
@Service
public class PatientService {

    private final DoctorPatientMapper doctorPatientMapper;
    private final UserMapper userMapper;

    public PatientService(DoctorPatientMapper doctorPatientMapper, UserMapper userMapper) {
        this.doctorPatientMapper = doctorPatientMapper;
        this.userMapper = userMapper;
    }

    public List<User> getPatientList(User doctor) {
        return doctorPatientMapper.selectList(new QueryWrapper<DoctorPatient>()
                .eq("doctor_id", doctor.getId()))
                .stream()
                .map(doctorPatient -> {
                    Integer id = doctorPatient.getPatientId();
                    return userMapper.selectById(id);
                }).toList();
    }
}
