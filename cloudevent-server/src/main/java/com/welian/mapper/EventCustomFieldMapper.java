package com.welian.mapper;

import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventCustomFieldMapper {
    long countByExample(EventCustomFieldExample example);

    int deleteByExample(EventCustomFieldExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventCustomField record);

    int insertSelective(EventCustomField record);

    List<EventCustomField> selectByExample(EventCustomFieldExample example);

    EventCustomField selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventCustomField record, @Param("example") EventCustomFieldExample example);

    int updateByExample(@Param("record") EventCustomField record, @Param("example") EventCustomFieldExample example);

    int updateByPrimaryKeySelective(EventCustomField record);

    int updateByPrimaryKey(EventCustomField record);

    void insertByBatch(List<EventCustomField> eventCustomFieldList);
}