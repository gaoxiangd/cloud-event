package com.welian.mapper;

import com.welian.pojo.EventModuleRelation;
import com.welian.pojo.EventModuleRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventModuleRelationMapper {
    long countByExample(EventModuleRelationExample example);

    int deleteByExample(EventModuleRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventModuleRelation record);

    int insertSelective(EventModuleRelation record);

    List<EventModuleRelation> selectByExample(EventModuleRelationExample example);

    EventModuleRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventModuleRelation record, @Param("example") EventModuleRelationExample example);

    int updateByExample(@Param("record") EventModuleRelation record, @Param("example") EventModuleRelationExample example);

    int updateByPrimaryKeySelective(EventModuleRelation record);

    int updateByPrimaryKey(EventModuleRelation record);
}