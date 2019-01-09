package com.welian.mapper;

import com.welian.pojo.TemplateModuleRelation;
import com.welian.pojo.TemplateModuleRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateModuleRelationMapper {
    long countByExample(TemplateModuleRelationExample example);

    int deleteByExample(TemplateModuleRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TemplateModuleRelation record);

    int insertSelective(TemplateModuleRelation record);

    List<TemplateModuleRelation> selectByExample(TemplateModuleRelationExample example);

    TemplateModuleRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TemplateModuleRelation record, @Param("example") TemplateModuleRelationExample example);

    int updateByExample(@Param("record") TemplateModuleRelation record, @Param("example") TemplateModuleRelationExample example);

    int updateByPrimaryKeySelective(TemplateModuleRelation record);

    int updateByPrimaryKey(TemplateModuleRelation record);
}