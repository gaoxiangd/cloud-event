package com.welian.mapper;

import com.welian.pojo.EventAdRelation;
import com.welian.pojo.EventAdRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventAdRelationMapper {
    long countByExample(EventAdRelationExample example);

    int deleteByExample(EventAdRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventAdRelation record);

    int insertSelective(EventAdRelation record);

    List<EventAdRelation> selectByExample(EventAdRelationExample example);

    EventAdRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventAdRelation record, @Param("example") EventAdRelationExample example);

    int updateByExample(@Param("record") EventAdRelation record, @Param("example") EventAdRelationExample example);

    int updateByPrimaryKeySelective(EventAdRelation record);

    int updateByPrimaryKey(EventAdRelation record);
}