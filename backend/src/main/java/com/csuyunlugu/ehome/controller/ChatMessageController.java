package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.ApiResponse;
import com.csuyunlugu.ehome.dto.ChatMessageDTO;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.ChatMessageService;
import com.csuyunlugu.ehome.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ChatMessageController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/18 14:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {
    private final UserService userService;
    private final ChatMessageService chatMessageService;

    public ChatMessageController(UserService userService, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public ApiResponse<List<ChatMessageDTO>> getChatMessage(HttpServletRequest request, @RequestParam Boolean identity, @RequestParam Integer id) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<ChatMessageDTO> history = chatMessageService.getHistory(identity, user, id);
        return ApiResponse.success(history);
    }

    @PostMapping
    public ApiResponse<List<ChatMessageDTO>> sendChatMessage(HttpServletRequest request, @RequestBody ChatMessageDTO chatMessageDTO) {
        String openid = request.getAttribute("subject").toString();
        User user = userService.findUserByOpenid(openid);
        List<ChatMessageDTO> list = chatMessageService.save(user, chatMessageDTO);
        return ApiResponse.success(list);
    }
}
