package com.welian.mapper;

import com.welian.pojo.EventSms;
import com.welian.pojo.EventSmsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventSmsMapper {
    long countByExample(EventSmsExample example);

    int deleteByExample(EventSmsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventSms record);

    int insertSelective(EventSms record);

    List<EventSms> selectByExample(EventSmsExample example);

    EventSms selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventSms record, @Param("example") EventSmsExample example);

    int updateByExample(@Param("record") EventSms record, @Param("example") EventSmsExample example);

    int updateByPrimaryKeySelective(EventSms record);

    int updateByPrimaryKey(EventSms record);

    void batchInsert(List<EventSms> list);
}