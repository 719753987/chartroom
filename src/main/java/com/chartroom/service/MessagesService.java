package com.chartroom.service;

import com.chartroom.dao.MessagesMapper;
import com.chartroom.dto.MessagesDto;
import com.chartroom.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by icinfo on 2017-09-15.
 */
@Service
public class MessagesService {
    @Autowired
    private MessagesMapper messagesMapper;

    public void addMessage(Messages messages){
        this.messagesMapper.insert(messages);
    }

    public List<Messages> getAllRecord(){
        return this.messagesMapper.selectAll();
    }
}
