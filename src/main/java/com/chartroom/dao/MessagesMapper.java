package com.chartroom.dao;

import com.chartroom.model.Messages;
import com.chartroom.model.MessagesExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessagesMapper {
    int countByExample(MessagesExample example);

    int deleteByExample(MessagesExample example);

    int deleteByPrimaryKey(String id);

    int insert(Messages record);

    int insertSelective(Messages record);

    List<Messages> selectByExample(MessagesExample example);

    Messages selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Messages record, @Param("example") MessagesExample example);

    int updateByExample(@Param("record") Messages record, @Param("example") MessagesExample example);

    int updateByPrimaryKeySelective(Messages record);

    int updateByPrimaryKey(Messages record);

    List<Messages> selectAll();
}