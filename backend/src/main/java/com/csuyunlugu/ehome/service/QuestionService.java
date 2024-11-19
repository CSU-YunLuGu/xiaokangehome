package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.AnswerMapper;
import com.csuyunlugu.ehome.dao.QuestionMapper;
import com.csuyunlugu.ehome.dto.AnswerDTO;
import com.csuyunlugu.ehome.dto.QuestionDTO;
import com.csuyunlugu.ehome.entity.Answer;
import com.csuyunlugu.ehome.entity.Question;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName QuestionService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/9 10:49
 * @Version 1.0
 */
@Service
public class QuestionService {
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final UserService userService;

    public QuestionService(QuestionMapper questionMapper, AnswerMapper answerMapper, UserService userService) {
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.userService = userService;
    }

    public List<QuestionDTO> getQuestionRecord(Integer id) {
        return questionMapper.selectList(new QueryWrapper<Question>()
                .eq("user_id", id))
                .stream()
                .map(this::getQuestionDTO)
                .toList();
    }

    public void addQuestion(String question, User user) {
        Question questionEntity = new Question();
        questionEntity.setUserId(user.getId());
        questionEntity.setQuestion(question);
        questionMapper.insert(questionEntity);
    }

    public QuestionDTO getQuestionRecordById(Integer id) {
        Question question = questionMapper.selectById(id);
        if (question != null) {
            return getQuestionDTO(question);
        }
        return null;
    }

    private QuestionDTO getQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setId(question.getId());
        User user = userService.findUserById(question.getUserId());
        questionDTO.setUsername(user.getUsername());
        questionDTO.setTime(TimeUtil.formatTime(question.getCreateAt()));
        List<AnswerDTO> answers = answerMapper.selectList(new QueryWrapper<Answer>()
                        .eq("question_id", question.getId()))
                .stream()
                .map(answer -> {
                    AnswerDTO answerDTO = new AnswerDTO();
                    answerDTO.setAnswer(answer.getAnswer());
                    User answerUser = userService.findUserById(answer.getUserId());
                    answerDTO.setUser(answerUser.getUsername());
                    answerDTO.setTime(TimeUtil.formatTime(answer.getCreateAt()));
                    return answerDTO;
                })
                .toList();
        questionDTO.setAnswer(answers);
        return questionDTO;
    }

    public QuestionDTO answerQuestion(Integer id, User user, String ans) {
        Question question = questionMapper.selectById(id);
        if (question != null) {
            Answer answer = new Answer();
            answer.setUserId(user.getId());
            answer.setAnswer(ans);
            answer.setQuestionId(question.getId());
            answerMapper.insert(answer);
            return getQuestionDTO(question);
        }
        return null;
    }

    public List<QuestionDTO> searchQuestion(String keyword) {
        return questionMapper.selectList(new QueryWrapper<Question>()
                .like("question", keyword))
                .stream()
                .map(this::getQuestionDTO)
                .toList();
    }
}
