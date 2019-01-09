package com.welian.mapper;

import com.welian.pojo.EventInvestorGroupRelation;
import com.welian.pojo.EventInvestorGroupRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventInvestorGroupRelationMapper {
    long countByExample(EventInvestorGroupRelationExample example);

    int deleteByExample(EventInvestorGroupRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventInvestorGroupRelation record);

    int insertSelective(EventInvestorGroupRelation record);

    List<EventInvestorGroupRelation> selectByExample(EventInvestorGroupRelationExample example);

    EventInvestorGroupRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventInvestorGroupRelation record, @Param("example") EventInvestorGroupRelationExample example);

    int updateByExample(@Param("record") EventInvestorGroupRelation record, @Param("example") EventInvestorGroupRelationExample example);

    int updateByPrimaryKeySelective(EventInvestorGroupRelation record);

    int updateByPrimaryKey(EventInvestorGroupRelation record);
}