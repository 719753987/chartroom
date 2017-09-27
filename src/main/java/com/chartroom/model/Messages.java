package com.chartroom.model;

import java.util.Date;

public class Messages {
    private String id;

    private String name;

    private String message;

    private Date sendTime;

    public Messages(){}

    public Messages(String id, String name, String message, Date sendTime) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.sendTime = sendTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}