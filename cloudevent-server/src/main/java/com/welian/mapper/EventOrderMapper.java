package com.welian.mapper;

import com.welian.pojo.EventOrder;
import com.welian.pojo.EventOrderExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventOrderMapper {
    long countByExample(EventOrderExample example);

    int deleteByExample(EventOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventOrder record);

    int insertSelective(EventOrder record);

    List<EventOrder> selectByExample(EventOrderExample example);

    EventOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventOrder record, @Param("example") EventOrderExample example);

    int updateByExample(@Param("record") EventOrder record, @Param("example") EventOrderExample example);

    int updateByPrimaryKeySelective(EventOrder record);

    int updateByPrimaryKey(EventOrder record);
}