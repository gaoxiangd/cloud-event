package com.welian.mapper;

import com.welian.pojo.EventSignAuth;
import com.welian.pojo.EventSignAuthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventSignAuthMapper {
    long countByExample(EventSignAuthExample example);

    int deleteByExample(EventSignAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventSignAuth record);

    int insertSelective(EventSignAuth record);

    List<EventSignAuth> selectByExample(EventSignAuthExample example);

    EventSignAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventSignAuth record, @Param("example") EventSignAuthExample example);

    int updateByExample(@Param("record") EventSignAuth record, @Param("example") EventSignAuthExample example);

    int updateByPrimaryKeySelective(EventSignAuth record);

    int updateByPrimaryKey(EventSignAuth record);
}