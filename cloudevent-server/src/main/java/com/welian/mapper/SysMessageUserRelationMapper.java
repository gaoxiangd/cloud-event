package com.welian.mapper;

import com.welian.pojo.SysMessageUserRelation;
import com.welian.pojo.SysMessageUserRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMessageUserRelationMapper {
    long countByExample(SysMessageUserRelationExample example);

    int deleteByExample(SysMessageUserRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysMessageUserRelation record);

    int insertSelective(SysMessageUserRelation record);

    List<SysMessageUserRelation> selectByExample(SysMessageUserRelationExample example);

    SysMessageUserRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysMessageUserRelation record, @Param("example")
            SysMessageUserRelationExample example);

    int updateByExample(@Param("record") SysMessageUserRelation record, @Param("example") SysMessageUserRelationExample example);

    int updateByPrimaryKeySelective(SysMessageUserRelation record);

    int updateByPrimaryKey(SysMessageUserRelation record);

    void batchInsert(List<SysMessageUserRelation> list);

}