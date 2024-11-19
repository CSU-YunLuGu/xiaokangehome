package com.csuyunlugu.ehome.dto;

import com.csuyunlugu.ehome.entity.ChatMessage;
import com.csuyunlugu.ehome.util.TimeUtil;
import lombok.Data;

/**
 * @ClassName ChatMessageDTO
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/18 12:34
 * @Version 1.0
 */
@Data
public class ChatMessageDTO {
    private Boolean identity;
    private Integer otherId;
    private Integer sender;
    private Integer receiver;
    private String content;
    private String time;

    public static ChatMessageDTO convertToChatMessageDTO(ChatMessage chatMessage) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSender(chatMessage.getSender());
        chatMessageDTO.setReceiver(chatMessage.getReceiver());
        chatMessageDTO.setContent(chatMessage.getContent());
        chatMessageDTO.setTime(TimeUtil.formatTime(chatMessage.getTimestamp()));
        return chatMessageDTO;
    }
}
