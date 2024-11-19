package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.ConsultDiaryMapper;
import com.csuyunlugu.ehome.dao.MedicineDiaryMapper;
import com.csuyunlugu.ehome.dto.DiaryDTO;
import com.csuyunlugu.ehome.dto.DiaryRecordDTO;
import com.csuyunlugu.ehome.entity.ConsultDiary;
import com.csuyunlugu.ehome.entity.MedicineDiary;
import com.csuyunlugu.ehome.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @ClassName DiaryRecordService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 17:16
 * @Version 1.0
 */
@Service
public class DiaryRecordService {
    private final MedicineDiaryMapper medicineDiaryMapper;
    private final ConsultDiaryMapper consultDiaryMapper;

    public DiaryRecordService(MedicineDiaryMapper medicineDiaryMapper, ConsultDiaryMapper consultDiaryMapper) {
        this.medicineDiaryMapper = medicineDiaryMapper;
        this.consultDiaryMapper = consultDiaryMapper;
    }

    public DiaryRecordDTO getDiaryRecord(Integer userId) {
        // 根据用户 id 查询药品记录
        List<DiaryDTO> medicineList = medicineDiaryMapper.selectList(new QueryWrapper<MedicineDiary>()
                        .eq("user_id", userId)
                        .orderByDesc("create_at"))
                .stream()
                .map(medicineDiary -> DiaryDTO.convertToDiaryDTO(medicineDiary.getCreateAt(), medicineDiary.getContent()))
                .toList();
        // 根据用户 id 查询咨询记录
        List<DiaryDTO> consultList = consultDiaryMapper.selectList(new QueryWrapper<ConsultDiary>()
                        .eq("user_id", userId)
                        .orderByDesc("create_at"))
                .stream()
                .map(consultDiary -> DiaryDTO.convertToDiaryDTO(consultDiary.getCreateAt(), consultDiary.getContent()))
                .toList();
        // 封装返回值
        DiaryRecordDTO diaryRecordDTO = new DiaryRecordDTO();
        diaryRecordDTO.setMedicineList(medicineList);
        diaryRecordDTO.setConsultList(consultList);
        return diaryRecordDTO;
    }
}
