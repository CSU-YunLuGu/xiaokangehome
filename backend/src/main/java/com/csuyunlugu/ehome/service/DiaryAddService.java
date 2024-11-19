package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.ConsultDiaryMapper;
import com.csuyunlugu.ehome.dao.MedicineDiaryMapper;
import com.csuyunlugu.ehome.entity.ConsultDiary;
import com.csuyunlugu.ehome.entity.MedicineDiary;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.util.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName DiaryService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 15:36
 * @Version 1.0
 */
@Service
public class DiaryAddService {
    private final MedicineDiaryMapper medicineDiaryMapper;
    private final ConsultDiaryMapper consultDiaryMapper;

    @Value("${spring.datasource.server-time-zone}")
    private String timeZone;

    public DiaryAddService(MedicineDiaryMapper medicineDiaryMapper, ConsultDiaryMapper consultDiaryMapper) {
        this.medicineDiaryMapper = medicineDiaryMapper;
        this.consultDiaryMapper = consultDiaryMapper;
    }

    public Boolean addMedicineDiary(Integer userId, String detail) {
//        QueryWrapper<MedicineDiary> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId)
//                .orderByDesc("id")
//                .last("LIMIT 1");
//        MedicineDiary diary = medicineDiaryMapper.selectOne(queryWrapper);
//        if (diary != null && TimeUtil.daysCount(diary.getCreateAt()) == 0) {
//            return true;
//        }
        MedicineDiary medicineDiary = new MedicineDiary();
        medicineDiary.setUserId(userId);
        medicineDiary.setContent(detail);
        medicineDiaryMapper.insert(medicineDiary);
        return false;
    }

    public Boolean addConsultDiary(Integer userId, String detail) {
//        QueryWrapper<ConsultDiary> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId)
//                .orderByDesc("id")
//                .last("LIMIT 1");
//        ConsultDiary diary = consultDiaryMapper.selectOne(queryWrapper);
//        if (diary != null && TimeUtil.daysCount(diary.getCreateAt()) == 0) {
//            return true;
//        }
        ConsultDiary consultDiary = new ConsultDiary();
        consultDiary.setUserId(userId);
        consultDiary.setContent(detail);
        consultDiaryMapper.insert(consultDiary);
        return false;
    }

}
