package com.welian.mapper;

import com.welian.pojo.EventUaInfo;
import com.welian.pojo.EventUaInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventUaInfoMapper {
    long countByExample(EventUaInfoExample example);

    int deleteByExample(EventUaInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventUaInfo record);

    int insertSelective(EventUaInfo record);

    List<EventUaInfo> selectByExample(EventUaInfoExample example);

    EventUaInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventUaInfo record, @Param("example") EventUaInfoExample example);

    int updateByExample(@Param("record") EventUaInfo record, @Param("example") EventUaInfoExample example);

    int updateByPrimaryKeySelective(EventUaInfo record);

    int updateByPrimaryKey(EventUaInfo record);
}