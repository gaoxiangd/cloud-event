package com.welian.mapper;

import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventStateCustomMapper {
    long countByExample(EventStateCustomExample example);

    int deleteByExample(EventStateCustomExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventStateCustom record);

    int insertSelective(EventStateCustom record);

    List<EventStateCustom> selectByExample(EventStateCustomExample example);

    EventStateCustom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventStateCustom record, @Param("example") EventStateCustomExample
            example);

    int updateByExample(@Param("record") EventStateCustom record, @Param("example") EventStateCustomExample example);

    int updateByPrimaryKeySelective(EventStateCustom record);

    int updateByPrimaryKey(EventStateCustom record);

    int updateRecordCount(@Param("eventId") Integer eventId, @Param("count") Integer count);
}