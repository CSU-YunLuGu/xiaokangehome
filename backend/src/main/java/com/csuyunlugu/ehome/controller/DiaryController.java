package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.DiaryRecordDTO;
import com.csuyunlugu.ehome.dto.DiaryAddDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.DiaryAddService;
import com.csuyunlugu.ehome.service.DiaryRecordService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName DiaryController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/6 14:53
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/medical")
public class DiaryController {
    private final DiaryAddService diaryAddService;
    private final DiaryRecordService diaryRecordService;
    private final UserService userService;

    public DiaryController(DiaryAddService diaryAddService, DiaryRecordService diaryRecordService, UserService userService) {
        this.diaryAddService = diaryAddService;
        this.diaryRecordService = diaryRecordService;
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<DiaryRecordDTO> getDiaryRecord(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        DiaryRecordDTO record = diaryRecordService.getDiaryRecord(user.getId());
        return ApiResponse.success(record);
    }

    @PostMapping
    public ApiResponse<Boolean> addDiary(HttpServletRequest request, @RequestBody @Validated DiaryAddDTO dto) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        String detail = dto.getDetail();
        // 记录类型 吃药 true 看病 false
        Boolean recorded = dto.getRecordType()
                ? diaryAddService.addMedicineDiary(user.getId(), detail)
                : diaryAddService.addConsultDiary(user.getId(), detail);
        return ApiResponse.success(recorded);
    }
}
