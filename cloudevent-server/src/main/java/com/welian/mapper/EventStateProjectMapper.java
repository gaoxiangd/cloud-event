package com.welian.mapper;

import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventStateProjectMapper {
    long countByExample(EventStateProjectExample example);

    int deleteByExample(EventStateProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventStateProject record);

    int insertSelective(EventStateProject record);

    List<EventStateProject> selectByExample(EventStateProjectExample example);

    EventStateProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventStateProject record, @Param("example") EventStateProjectExample example);

    int updateByExample(@Param("record") EventStateProject record, @Param("example") EventStateProjectExample example);

    int updateByPrimaryKeySelective(EventStateProject record);

    int updateByPrimaryKey(EventStateProject record);

    int updateRecordCount(@Param("eventId") Integer eventId, @Param("count") Integer count);
}