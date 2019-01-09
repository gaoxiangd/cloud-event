package com.welian.mapper;

import com.welian.pojo.EventUidFavoriteRelation;
import com.welian.pojo.EventUidFavoriteRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventUidFavoriteRelationMapper {
    long countByExample(EventUidFavoriteRelationExample example);

    int deleteByExample(EventUidFavoriteRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventUidFavoriteRelation record);

    int insertSelective(EventUidFavoriteRelation record);

    List<EventUidFavoriteRelation> selectByExample(EventUidFavoriteRelationExample example);

    EventUidFavoriteRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventUidFavoriteRelation record, @Param("example") EventUidFavoriteRelationExample example);

    int updateByExample(@Param("record") EventUidFavoriteRelation record, @Param("example") EventUidFavoriteRelationExample example);

    int updateByPrimaryKeySelective(EventUidFavoriteRelation record);

    int updateByPrimaryKey(EventUidFavoriteRelation record);

    void batchInsert(List<EventUidFavoriteRelation> list);


}