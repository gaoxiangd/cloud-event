package com.welian.mapper;

import com.welian.pojo.SmsHistory;
import com.welian.pojo.SmsHistoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SmsHistoryMapper {
    long countByExample(SmsHistoryExample example);

    int deleteByExample(SmsHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsHistory record);

    int insertSelective(SmsHistory record);

    List<SmsHistory> selectByExample(SmsHistoryExample example);

    SmsHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsHistory record, @Param("example") SmsHistoryExample example);

    int updateByExample(@Param("record") SmsHistory record, @Param("example") SmsHistoryExample example);

    int updateByPrimaryKeySelective(SmsHistory record);

    int updateByPrimaryKey(SmsHistory record);
}