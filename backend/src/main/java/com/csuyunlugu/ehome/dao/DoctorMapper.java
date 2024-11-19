package com.csuyunlugu.ehome.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuyunlugu.ehome.entity.Doctor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

public interface DoctorMapper extends BaseMapper<Doctor> {
    @Select("select d.* from doctor d " +
            "inner join doctor_patient dp " +
            "on d.id = dp.doctor_id " +
            "where dp.patient_id = #{patientId}")
    List<Doctor> selectByPatientId(Integer patientId);

    @Select("SELECT d.* FROM doctor d " +
            "INNER JOIN user u ON d.user_id = u.id " +
            "WHERE (u.name LIKE #{keyword} " +
            "OR d.major LIKE #{keyword} " +
            "OR d.job LIKE #{keyword}) " +
            "AND d.id NOT IN " +
            "<foreach item='item' collection='doctorIdList' open='(' separator=',' close=')'> " +
            "#{item} " +
            "</foreach>")
    List<Doctor> selectDoctorsByKeywordAndExcludeIds(
            @Param("keyword") String keyword,
            @Param("doctorIdList") List<Integer> doctorIdList);
}
