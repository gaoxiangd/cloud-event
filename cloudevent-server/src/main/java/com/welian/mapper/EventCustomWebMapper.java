package com.welian.mapper;

import com.welian.pojo.EventCustomWeb;
import com.welian.pojo.EventCustomWebExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventCustomWebMapper {
    long countByExample(EventCustomWebExample example);

    int deleteByExample(EventCustomWebExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventCustomWeb record);

    int insertSelective(EventCustomWeb record);

    List<EventCustomWeb> selectByExampleWithBLOBs(EventCustomWebExample example);

    List<EventCustomWeb> selectByExample(EventCustomWebExample example);

    EventCustomWeb selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventCustomWeb record, @Param("example") EventCustomWebExample example);

    int updateByExampleWithBLOBs(@Param("record") EventCustomWeb record, @Param("example") EventCustomWebExample example);

    int updateByExample(@Param("record") EventCustomWeb record, @Param("example") EventCustomWebExample example);

    int updateByPrimaryKeySelective(EventCustomWeb record);

    int updateByPrimaryKeyWithBLOBs(EventCustomWeb record);

    int updateByPrimaryKey(EventCustomWeb record);
}