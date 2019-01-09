package com.welian.mapper;

import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCityRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventCityRelationMapper {
    Long countByExample(EventCityRelationExample example);

    int deleteByExample(EventCityRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventCityRelation record);

    int insertSelective(EventCityRelation record);

    List<EventCityRelation> selectByExample(EventCityRelationExample example);

    EventCityRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventCityRelation record, @Param("example") EventCityRelationExample example);

    int updateByExample(@Param("record") EventCityRelation record, @Param("example") EventCityRelationExample example);

    int updateByPrimaryKeySelective(EventCityRelation record);

    int updateByPrimaryKey(EventCityRelation record);

    void insertByBatchSelective(List<EventCityRelation> eventCityList);
}