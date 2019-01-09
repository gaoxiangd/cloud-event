package com.welian.mapper;

import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface EventRecordUserMapper {
    long countByExample(EventRecordUserExample example);

    int deleteByExample(EventRecordUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRecordUser record);

    int insertSelective(EventRecordUser record);

    List<EventRecordUser> selectPhone(EventRecordUserExample example);
    List<EventRecordUser> selectByExample(EventRecordUserExample example);

    EventRecordUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRecordUser record, @Param("example") EventRecordUserExample
            example);

    int updateByExample(@Param("record") EventRecordUser record, @Param("example") EventRecordUserExample example);

    int updateByPrimaryKeySelective(EventRecordUser record);

    int updateByPrimaryKey(EventRecordUser record);

    List<EventRecordUser> selectByName(@Param("keyword") String keyword, @Param("orgId") Integer orgId);
}