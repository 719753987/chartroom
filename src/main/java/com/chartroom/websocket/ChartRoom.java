package com.chartroom.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chartroom.dto.MessagesDto;
import com.chartroom.model.Messages;
import com.chartroom.service.MessagesService;
import com.chartroom.util.StringUtil;
import com.chartroom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChartRoom extends AbstractWebSocketHandler {

    @Autowired
    private MessagesService messagesService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketSession> webSocketSet = new CopyOnWriteArraySet<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSet.add(session);//加入map中
        //获取历史聊天记录
        List<Messages> messagesList = this.messagesService.getAllRecord();
        MessagesDto temp;
        for (Messages messages : messagesList) {
            try {
                JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
                temp = new MessagesDto(messages);
                temp.setType(1);
                String jsonString = JSON.toJSONString(temp,
                        SerializerFeature.WriteDateUseDateFormat);
                session.sendMessage(new TextMessage(jsonString));
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        addOnlineCount();
        updateOnlineCount();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> map = session.getAttributes();
        User user = (User) map.get(StringUtil.USERNAME_KEY);

        //将消息存表
        Messages messages = new Messages();
        messages.setId(StringUtil.getUuid());
        messages.setMessage(message.getPayload());
        messages.setName(user.getName());
        messages.setSendTime(new Date());
        this.messagesService.addMessage(messages);

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        MessagesDto temp = new MessagesDto(messages);
        temp.setType(1);
        String jsonString = JSON.toJSONString(temp,
                SerializerFeature.WriteDateUseDateFormat);
        this.sendMessageToAll(jsonString);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSet.remove(session);
        subOnlineCount();           //在线数减1
        updateOnlineCount();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        if (session.isOpen()) {
            webSocketSet.remove(session);
            updateOnlineCount();
            session.close();
        }
    }

    public void updateOnlineCount(){
        //更新在线人数
        MessagesDto temp = new MessagesDto();
        temp.setType(2);
        temp.setOnlineCount(getOnlineCount());
        String jsonString = JSON.toJSONString(temp);
        this.sendMessageToAll(jsonString);
    }

    public void sendMessageToAll(String message) {
        //群发消息
        for (WebSocketSession socketSession : this.webSocketSet) {
            try {
                socketSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public int getOnlineCount() {
        return Integer.parseInt(redisTemplate.opsForValue().get("onlineCount"));
    }

    public void addOnlineCount() {
        redisTemplate.opsForValue().increment("onlineCount", 1);
    }

    public void subOnlineCount() {
        redisTemplate.opsForValue().increment("onlineCount", -1);
    }
}