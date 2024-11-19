package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.entity.Doctor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @ClassName DoctorDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 09:13
 * @Version 1.0
 */
@Data
public class DoctorIdentityDTO {
    @NotBlank
    private String major;
    @NotBlank
    private String job;
    @NotBlank
    private String certificatePicUrl;
    @NotBlank
    private String photoUrl;
    private Boolean valid;

    public static DoctorIdentityDTO convertToDoctorDTO(Doctor doctor) {
        DoctorIdentityDTO doctorIdentityDTO = new DoctorIdentityDTO();
        doctorIdentityDTO.setMajor(doctor.getMajor());
        doctorIdentityDTO.setJob(doctor.getJob());
        doctorIdentityDTO.setCertificatePicUrl(doctor.getCertificatePicUrl());
        doctorIdentityDTO.setPhotoUrl(doctor.getPhotoUrl());
        doctorIdentityDTO.setValid(doctor.getValid());
        return doctorIdentityDTO;
    }
}
