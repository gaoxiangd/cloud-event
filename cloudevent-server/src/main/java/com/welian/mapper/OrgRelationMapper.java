package com.welian.mapper;

import com.welian.pojo.OrgRelation;
import com.welian.pojo.OrgRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrgRelationMapper {
    long countByExample(OrgRelationExample example);

    int deleteByExample(OrgRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrgRelation record);

    int insertSelective(OrgRelation record);

    List<OrgRelation> selectByExample(OrgRelationExample example);

    OrgRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrgRelation record, @Param("example") OrgRelationExample example);

    int updateByExample(@Param("record") OrgRelation record, @Param("example") OrgRelationExample example);

    int updateByPrimaryKeySelective(OrgRelation record);

    int updateByPrimaryKey(OrgRelation record);
}