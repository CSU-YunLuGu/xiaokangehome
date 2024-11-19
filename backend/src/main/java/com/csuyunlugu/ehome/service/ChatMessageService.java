package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.ChatMessageMapper;
import com.csuyunlugu.ehome.dao.DoctorPatientMapper;
import com.csuyunlugu.ehome.dao.UserMapper;
import com.csuyunlugu.ehome.dto.ChatMessageDTO;
import com.csuyunlugu.ehome.entity.ChatMessage;
import com.csuyunlugu.ehome.entity.DoctorPatient;
import com.csuyunlugu.ehome.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ChatMessageService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/18 12:19
 * @Version 1.0
 */
@Service
public class ChatMessageService {
    private final ChatMessageMapper chatMessageMapper;
    private final DoctorPatientMapper doctorPatientMapper;
    private final UserMapper userMapper;

    public ChatMessageService(ChatMessageMapper chatMessageMapper, DoctorPatientMapper doctorPatientMapper, UserMapper userMapper) {
        this.chatMessageMapper = chatMessageMapper;
        this.doctorPatientMapper = doctorPatientMapper;
        this.userMapper = userMapper;
    }
    
    private void checkPermission(Boolean identity, User sender, Integer otherId) {
        QueryWrapper<DoctorPatient> queryWrapper = new QueryWrapper<>();
        if (!identity) {
            Integer id = sender.getId();
            queryWrapper.eq("patient_id", id);
            // sender 非医生用户 otherId 必须为医生用户
            Integer doctorId = userMapper.selectById(otherId).getDoctorId();
            queryWrapper.eq("doctor_id", doctorId);
        } else {
            // sender 为医生用户 otherId 必须为患者用户
            Integer doctorId = sender.getDoctorId();
            queryWrapper.eq("doctor_id", doctorId);
            queryWrapper.eq("patient_id", otherId);
        }
        DoctorPatient doctorPatient = doctorPatientMapper.selectOne(queryWrapper);
        if (doctorPatient == null) {
            throw new RuntimeException("没有找到对应的医生与患者关系");
        }
    }

    public List<ChatMessageDTO> getHistory(Boolean identity, User user, Integer otherId) {
        checkPermission(identity, user, otherId);
        return chatMessageMapper.selectList(
                new QueryWrapper<ChatMessage>()
                        .or(wrapper -> wrapper.eq("sender", user.getId()).eq("receiver", otherId))
                        .or(wrapper -> wrapper.eq("sender", otherId).eq("receiver", user.getId()))
                        .orderByAsc("timestamp")
        ).stream().map(ChatMessageDTO::convertToChatMessageDTO).toList();
    }
    
    public List<ChatMessageDTO> save(User user, ChatMessageDTO chatMessageDTO) {
        Integer otherId = chatMessageDTO.getOtherId();
        Boolean identity = chatMessageDTO.getIdentity();
        checkPermission(identity, user, otherId);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(user.getId());
        chatMessage.setReceiver(otherId);
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessageMapper.insert(chatMessage);
        return getHistory(identity, user, otherId);
//        return getHistory(receiver, sender);
    }
}
