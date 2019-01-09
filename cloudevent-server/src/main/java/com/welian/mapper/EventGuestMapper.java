package com.welian.mapper;

import com.welian.pojo.EventGuest;
import com.welian.pojo.EventGuestExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventGuestMapper {
    long countByExample(EventGuestExample example);

    int deleteByExample(EventGuestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventGuest record);

    int insertSelective(EventGuest record);

    List<EventGuest> selectByExample(EventGuestExample example);

    EventGuest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventGuest record, @Param("example") EventGuestExample example);

    int updateByExample(@Param("record") EventGuest record, @Param("example") EventGuestExample example);

    int updateByPrimaryKeySelective(EventGuest record);

    int updateByPrimaryKey(EventGuest record);

    void insertByBatch(List<EventGuest> eventGuestList);
}