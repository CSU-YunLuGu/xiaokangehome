package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.QuestionDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.QuestionService;
import com.csuyunlugu.ehome.service.UserService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName QuestionController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 10:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final UserService userService;
    private final QuestionService questionService;

    public QuestionController(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    @GetMapping("/record")
    public ApiResponse<List<QuestionDTO>> getQuestionRecord(HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<QuestionDTO> questionRecord = questionService.getQuestionRecord(user.getId());
        return ApiResponse.success(questionRecord);
    }

    @PostMapping("/add")
    public ApiResponse<List<QuestionDTO>> addQuestion(@RequestBody @NotBlank String question, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        questionService.addQuestion(question, user);
        List<QuestionDTO> questionRecord = questionService.getQuestionRecord(user.getId());
        return ApiResponse.success(questionRecord);
    }

    @GetMapping("/record/{id}")
    public ApiResponse<QuestionDTO> getQuestionRecord(@PathVariable @NotNull Integer id) {
        QuestionDTO questionRecord = questionService.getQuestionRecordById(id);
        return ApiResponse.success(questionRecord);
    }

    @PostMapping("/answer/{id}")
    public ApiResponse<QuestionDTO> answerQuestion(@PathVariable @NotNull Integer id, @RequestBody @NotBlank String answer, HttpServletRequest request) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        QuestionDTO questionRecord = questionService.answerQuestion(id, user, answer);
        return ApiResponse.success(questionRecord);
    }

    @GetMapping("/search")
    public ApiResponse<List<QuestionDTO>> searchQuestion(@NotBlank String keyword) {
        List<QuestionDTO> questionRecord = questionService.searchQuestion(keyword);
        return ApiResponse.success(questionRecord);
    }
}
