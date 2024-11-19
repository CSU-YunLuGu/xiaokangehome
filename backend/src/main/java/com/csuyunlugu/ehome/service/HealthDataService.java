package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.HealthDataMapper;
import com.csuyunlugu.ehome.dto.HealthDataDTO;
import com.csuyunlugu.ehome.entity.HealthData;
import com.csuyunlugu.ehome.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HealthDataService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 09:51
 * @Version 1.0
 */
@Service
public class HealthDataService {
    private final HealthDataMapper healthDataMapper;

    public HealthDataService(HealthDataMapper healthDataMapper) {
        this.healthDataMapper = healthDataMapper;
    }

    public List<HealthDataDTO> getHealthData(Integer id) {
        List<HealthDataDTO> healthDataDTOList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            HealthDataDTO healthDataDTO = new HealthDataDTO();
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(i));
            String formatDate = TimeUtil.formatDate(timestamp);
            healthDataDTO.setDate(formatDate);
            healthDataDTOList.add(healthDataDTO);
        }
        List<HealthData> healthDataList = healthDataMapper.getHealthData(id);
        for (HealthData healthData : healthDataList) {
            Integer days = TimeUtil.daysCount(healthData.getCreateAt());
            if (days >= 0 && days < healthDataDTOList.size()) {
                HealthDataDTO healthDataDTO = healthDataDTOList.get(days);
                healthDataDTO.setExerciseDuration(healthData.getExerciseDuration());
                healthDataDTO.setHeartRate(healthData.getHeartRate());
                healthDataDTO.setSleepDuration(healthData.getSleepDuration());
                healthDataDTO.setSystolicPressure(healthData.getSystolicPressure());
                healthDataDTO.setDiastolicPressure(healthData.getDiastolicPressure());
            }
        }
        return healthDataDTOList;
    }

    public Boolean addHealthData(Integer id, HealthDataDTO healthDataDTO) {
        // 查询今天是否已经有数据
        QueryWrapper<HealthData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id)
                .orderByDesc("id")
                .last("LIMIT 1");
        {
            HealthData healthData = healthDataMapper.selectOne(queryWrapper);
            if (healthData != null && TimeUtil.daysCount(healthData.getCreateAt()) == 0) {
                return true;
            }
        }
        HealthData healthData = new HealthData();
        healthData.setUserId(id);
        healthData.setExerciseDuration(healthDataDTO.getExerciseDuration());
        healthData.setHeartRate(healthDataDTO.getHeartRate());
        healthData.setSleepDuration(healthDataDTO.getSleepDuration());
        healthData.setSystolicPressure(healthDataDTO.getSystolicPressure());
        healthData.setDiastolicPressure(healthDataDTO.getDiastolicPressure());
        healthData.setCreateAt(new Timestamp(System.currentTimeMillis()));
        healthDataMapper.insert(healthData);
        return false;
    }
}
