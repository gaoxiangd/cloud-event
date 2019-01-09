package com.welian.mapper;

import com.welian.pojo.EventSysMessage;
import com.welian.pojo.EventSysMessageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventSysMessageMapper {
    long countByExample(EventSysMessageExample example);

    int deleteByExample(EventSysMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventSysMessage record);

    int insertSelective(EventSysMessage record);

    List<EventSysMessage> selectByExample(EventSysMessageExample example);

    EventSysMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventSysMessage record, @Param("example") EventSysMessageExample example);

    int updateByExample(@Param("record") EventSysMessage record, @Param("example") EventSysMessageExample example);

    int updateByPrimaryKeySelective(EventSysMessage record);

    int updateByPrimaryKey(EventSysMessage record);

    int batchUpdate(@Param("state") Byte state, @Param("list") String list);

}