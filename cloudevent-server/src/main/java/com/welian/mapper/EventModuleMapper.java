package com.welian.mapper;

import com.welian.pojo.EventModule;
import com.welian.pojo.EventModuleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventModuleMapper {
    long countByExample(EventModuleExample example);

    int deleteByExample(EventModuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventModule record);

    int insertSelective(EventModule record);

    List<EventModule> selectByExample(EventModuleExample example);

    EventModule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventModule record, @Param("example") EventModuleExample example);

    int updateByExample(@Param("record") EventModule record, @Param("example") EventModuleExample example);

    int updateByPrimaryKeySelective(EventModule record);

    int updateByPrimaryKey(EventModule record);
}