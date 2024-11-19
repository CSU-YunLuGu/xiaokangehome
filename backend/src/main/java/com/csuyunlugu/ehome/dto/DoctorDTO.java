package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.entity.Doctor;
import lombok.Data;

/**
 * @ClassName DoctorDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/10 13:55
 * @Version 1.0
 */
@Data
public class DoctorDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String major;
    private String job;
    private String certificatePicUrl;
    private String photoUrl;

    public static DoctorDTO convertToDoctorDTO(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setUserId(doctor.getUserId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setMajor(doctor.getMajor());
        doctorDTO.setJob(doctor.getJob());
        doctorDTO.setCertificatePicUrl(doctor.getCertificatePicUrl());
        doctorDTO.setPhotoUrl(doctor.getPhotoUrl());
        return doctorDTO;
    }
}
