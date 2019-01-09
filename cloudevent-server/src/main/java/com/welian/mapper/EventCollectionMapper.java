package com.welian.mapper;

import com.welian.pojo.EventCollection;
import com.welian.pojo.EventCollectionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventCollectionMapper {
    long countByExample(EventCollectionExample example);

    int deleteByExample(EventCollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventCollection record);

    int insertSelective(EventCollection record);

    List<EventCollection> selectByExample(EventCollectionExample example);

    EventCollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventCollection record, @Param("example") EventCollectionExample example);

    int updateByExample(@Param("record") EventCollection record, @Param("example") EventCollectionExample example);

    int updateByPrimaryKeySelective(EventCollection record);

    int updateByPrimaryKey(EventCollection record);
}