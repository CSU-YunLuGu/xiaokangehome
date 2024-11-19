package com.csuyunlugu.ehome.config;

import com.corundumstudio.socketio.*;
import com.csuyunlugu.ehome.dto.ChatMessageDTO;
import com.csuyunlugu.ehome.entity.ChatMessage;
import com.csuyunlugu.ehome.entity.User;
import com.csuyunlugu.ehome.service.ChatMessageService;
import com.csuyunlugu.ehome.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketConfig {

//    private final SocketIOServer socketIOServer;
//    private final ChatMessageService chatMessageService;
//    private final Map<Integer, SocketIOClient> clients = new ConcurrentHashMap<>();
//    private final UserService userService;
//
//    public WebSocketConfig(ChatMessageService chatMessageService, UserService userService) {
//        this.chatMessageService = chatMessageService;
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setHostname("0.0.0.0"); // 监听所有网络接口
//        config.setPort(8087); // 设置服务器端口
//        this.socketIOServer = new SocketIOServer(config);
//        this.userService = userService;
//    }
//
//    @PostConstruct
//    private void startServer() {
//        // 监听聊天消息事件
//        socketIOServer.addEventListener("chatMessage", ChatMessageDTO.class, (client, message, ackRequest) -> {
//            // 消息持久化 并查询聊天记录 以接收者的身份获取聊天记录
//            List<ChatMessage> history = chatMessageService.save(message);
//            // 转发消息给接收者 包括接收记录
//            forwardMessageToSpecificClient(message.getReceiver(), history);
//        });
//
//        // 监听请求聊天历史事件
//        socketIOServer.addEventListener("requestHistory", ChatMessageDTO.class, (client, message, ackRequest) -> {
//            // 解析请求数据
//            Integer sender = message.getSender();
//            Integer receiver = message.getReceiver();
//            // 查询聊天记录
//            List<ChatMessage> history = chatMessageService.getHistory(sender, receiver);
//            // 发送聊天记录给请求的客户端
//            client.sendEvent("history", history);
//        });
//
//        // 监听客户端连接事件
//        socketIOServer.addEventListener("connect", String.class, (client, openid, ackRequest) -> {
//            // 客户端发送连接请求 解析 openid
//            Integer userId = userService.findUserByOpenid(openid).getId();
//            clients.put(userId, client);
//            System.out.println("Client connected: " + client.getSessionId());
//        });
//
//        // 监听客户端断开连接事件
//        socketIOServer.addEventListener("disconnect", String.class, (client, data, ackRequest) -> {
//            // 移除断开连接的客户端
//            clients.entrySet().removeIf(entry -> entry.getValue().getSessionId().equals(client.getSessionId()));
//            System.out.println("Client disconnected: " + client.getSessionId());
//        });
//
//        // 启动Socket.IO服务器
//        socketIOServer.start();
//    }
//
//    // 转发消息给特定客户端
//    private void forwardMessageToSpecificClient(Integer receiverId, List<ChatMessage> message) {
//        SocketIOClient receiverClient = clients.get(receiverId);
//        if (receiverClient != null) {
//            receiverClient.sendEvent("message", message);
//        }
//    }
//
//    @PreDestroy
//    private void stopServer() {
//        socketIOServer.stop();
//    }
//
//    @Bean
//    public SpringAnnotationScanner springAnnotationScanner() {
//        return new SpringAnnotationScanner(socketIOServer);
//    }
}
