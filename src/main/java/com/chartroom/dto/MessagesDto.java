package com.chartroom.dto;

import com.chartroom.model.Messages;

import java.util.Date;

/**
 * Created by icinfo on 2017-09-22.
 */
public class MessagesDto extends Messages{
    private int onlineCount;
    private int type;//1聊天消息 2在线人数

    public MessagesDto(){}

    public MessagesDto(Messages messages) {
        super(messages.getId(), messages.getName(), messages.getMessage(), messages.getSendTime());
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
