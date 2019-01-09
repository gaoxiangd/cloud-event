package com.welian.mapper;

import com.welian.pojo.EventRecordAdRelation;
import com.welian.pojo.EventRecordAdRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRecordAdRelationMapper {
    long countByExample(EventRecordAdRelationExample example);

    int deleteByExample(EventRecordAdRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRecordAdRelation record);

    int insertSelective(EventRecordAdRelation record);

    List<EventRecordAdRelation> selectByExample(EventRecordAdRelationExample example);

    EventRecordAdRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRecordAdRelation record, @Param("example") EventRecordAdRelationExample example);

    int updateByExample(@Param("record") EventRecordAdRelation record, @Param("example") EventRecordAdRelationExample example);

    int updateByPrimaryKeySelective(EventRecordAdRelation record);

    int updateByPrimaryKey(EventRecordAdRelation record);

}