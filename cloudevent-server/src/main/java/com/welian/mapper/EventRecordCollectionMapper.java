package com.welian.mapper;

import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordCollectionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRecordCollectionMapper {
    long countByExample(EventRecordCollectionExample example);

    int deleteByExample(EventRecordCollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRecordCollection record);

    int insertSelective(EventRecordCollection record);

    List<EventRecordCollection> selectByExample(EventRecordCollectionExample example);

    EventRecordCollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRecordCollection record, @Param("example") EventRecordCollectionExample example);

    int updateByExample(@Param("record") EventRecordCollection record, @Param("example") EventRecordCollectionExample example);

    int updateByPrimaryKeySelective(EventRecordCollection record);

    int updateByPrimaryKey(EventRecordCollection record);


    void batchInsert(List<EventRecordCollection> collectionList);
}