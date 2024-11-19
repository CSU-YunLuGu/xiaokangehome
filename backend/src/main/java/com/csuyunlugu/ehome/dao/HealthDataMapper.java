package com.csuyunlugu.ehome.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csuyunlugu.ehome.entity.HealthData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HealthDataMapper extends BaseMapper<HealthData> {
    @Select("SELECT * FROM health_data WHERE user_id = #{userId} AND create_at >= NOW() - INTERVAL 7 DAY")
    List<HealthData> getHealthData(Integer userId);
}
