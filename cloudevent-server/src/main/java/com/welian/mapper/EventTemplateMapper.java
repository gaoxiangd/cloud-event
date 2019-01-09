package com.welian.mapper;

import com.welian.pojo.EventTemplate;
import com.welian.pojo.EventTemplateExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventTemplateMapper {
    long countByExample(EventTemplateExample example);

    int deleteByExample(EventTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventTemplate record);

    int insertSelective(EventTemplate record);

    List<EventTemplate> selectByExample(EventTemplateExample example);

    EventTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventTemplate record, @Param("example") EventTemplateExample example);

    int updateByExample(@Param("record") EventTemplate record, @Param("example") EventTemplateExample example);

    int updateByPrimaryKeySelective(EventTemplate record);

    int updateByPrimaryKey(EventTemplate record);
}