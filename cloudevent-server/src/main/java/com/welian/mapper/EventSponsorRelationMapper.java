package com.welian.mapper;

import com.welian.pojo.EventSponsorRelation;
import com.welian.pojo.EventSponsorRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventSponsorRelationMapper {
    long countByExample(EventSponsorRelationExample example);

    int deleteByExample(EventSponsorRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventSponsorRelation record);

    int insertSelective(EventSponsorRelation record);

    List<EventSponsorRelation> selectByExample(EventSponsorRelationExample example);

    EventSponsorRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventSponsorRelation record, @Param("example") EventSponsorRelationExample example);

    int updateByExample(@Param("record") EventSponsorRelation record, @Param("example") EventSponsorRelationExample example);

    int updateByPrimaryKeySelective(EventSponsorRelation record);

    int updateByPrimaryKey(EventSponsorRelation record);

    void insertByBatch(List<EventSponsorRelation> eventSponsorRelationList);

}