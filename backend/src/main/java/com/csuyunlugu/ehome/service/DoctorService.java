package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.csuyunlugu.ehome.dao.DoctorMapper;
import com.csuyunlugu.ehome.dao.DoctorPatientMapper;
import com.csuyunlugu.ehome.dao.UserMapper;
import com.csuyunlugu.ehome.dto.DoctorDTO;
import com.csuyunlugu.ehome.dto.DoctorIdentityDTO;
import com.csuyunlugu.ehome.entity.Doctor;
import com.csuyunlugu.ehome.entity.DoctorPatient;
import com.csuyunlugu.ehome.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName DoctorService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/8 20:59
 * @Version 1.0
 */
@Service
public class DoctorService {
    private final UserMapper userMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorPatientMapper doctorPatientMapper;

    public DoctorService(UserMapper userMapper, DoctorMapper doctorMapper, DoctorPatientMapper doctorPatientMapper) {
        this.userMapper = userMapper;
        this.doctorMapper = doctorMapper;
        this.doctorPatientMapper = doctorPatientMapper;
    }

    public void submitInfo(User user, DoctorIdentityDTO doctorIdentityDTO) {
        Integer doctorId = user.getDoctorId();
        // 定义好 Doctor 对象 orm 映射
        Doctor doctor = new Doctor();
        doctor.setUserId(user.getId());
        doctor.setName(user.getName());
        doctor.setMajor(doctorIdentityDTO.getMajor());
        doctor.setJob(doctorIdentityDTO.getJob());
        doctor.setCertificatePicUrl(doctorIdentityDTO.getCertificatePicUrl());
        doctor.setPhotoUrl(doctorIdentityDTO.getPhotoUrl());
        if (doctorId == null) {
            doctorMapper.insert(doctor);
            // 查出刚才插入的医生 doctor 的 id
            QueryWrapper<Doctor> queryWrapper = new QueryWrapper<Doctor>()
                    .eq("user_id", user.getId());
            doctorId = doctorMapper.selectOne(queryWrapper).getId();
            // 将 doctor 的 id 插入 user 的 doctor_id 字段中
            user.setDoctorId(doctorId);
            userMapper.updateById(user);
        } else {
            // 医生状态设置为待认证
            UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<Doctor>()
                    .eq("id", doctorId)
                    .set("valid", null);
            doctor.setId(doctorId);
            doctorMapper.update(doctor, updateWrapper);
        }
    }

    public DoctorIdentityDTO getInfo(User user) {
        Integer doctorId = user.getDoctorId();
        return doctorId == null
                ? null
                : DoctorIdentityDTO.convertToDoctorDTO(doctorMapper.selectById(doctorId));
    }

    public Boolean identity(User user) {
        Integer doctorId = user.getDoctorId();
        return doctorId != null
                && Objects.requireNonNullElse(doctorMapper.selectById(doctorId).getValid(), false);
    }

    public List<DoctorDTO> getDoctorListByPatientId(User user) {
        Integer userId = user.getId();
        // 连表查询 拿到 doctor_patient 表的 doctor_id 字段 再查 doctor 表
        return doctorMapper.selectByPatientId(userId)
                .stream()
                .map(DoctorDTO::convertToDoctorDTO)
                .toList();
    }

    public void deleteDoctor(Integer id, User user) {
        doctorPatientMapper.delete(new QueryWrapper<DoctorPatient>()
                .eq("doctor_id", id)
                .eq("patient_id", user.getId()));
    }

    public List<DoctorDTO> getDoctorListByKeyword(User user, String keyword) {
        List<Integer> doctorIdList = doctorPatientMapper.selectList(new QueryWrapper<DoctorPatient>()
                        .eq("patient_id", user.getId()))
                .stream()
                .map(DoctorPatient::getDoctorId)
                .toList();
        // 构建查询条件，排除已关联的医生ID
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<Doctor>()
                .and(wrapper -> wrapper
                        .like("name", keyword)
                        .or()
                        .like("major", keyword)
                        .or()
                        .like("job", keyword));
        // 排除掉 doctor_patient 表中已存在的 doctor_id
        if (!doctorIdList.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper.notIn("id", doctorIdList));
        }
        // 排除掉自己
        queryWrapper.and(wrapper -> wrapper.ne("user_id", user.getId()));
        return doctorMapper.selectList(queryWrapper)
                .stream()
                .map(DoctorDTO::convertToDoctorDTO)
                .toList();
    }

    public void addDoctor(Integer id, User user) {
        DoctorPatient doctorPatient = new DoctorPatient();
        doctorPatient.setDoctorId(id);
        doctorPatient.setPatientId(user.getId());
        doctorPatientMapper.insert(doctorPatient);
    }

    public List<Doctor> findDoctorsNotAudit() {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid", null);
        return doctorMapper.selectList(queryWrapper);
    }

    public void audit(@NotNull Integer doctorId, @NotNull Boolean valid) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", doctorId);
        Doctor doctor = doctorMapper.selectOne(queryWrapper);
        if (doctor == null || doctor.getValid() == null) {
            throw new IllegalArgumentException("医生不存在或已审核");
        }
        UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", doctorId)
                .set("valid", valid);
        doctorMapper.update(doctor, updateWrapper);
    }
}
