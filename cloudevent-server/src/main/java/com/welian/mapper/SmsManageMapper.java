package com.welian.mapper;

import com.welian.pojo.SmsManage;
import com.welian.pojo.SmsManageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SmsManageMapper {
    long countByExample(SmsManageExample example);

    int deleteByExample(SmsManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsManage record);

    int insertSelective(SmsManage record);

    List<SmsManage> selectByExample(SmsManageExample example);

    SmsManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsManage record, @Param("example") SmsManageExample example);

    int updateByExample(@Param("record") SmsManage record, @Param("example") SmsManageExample example);

    int updateByPrimaryKeySelective(SmsManage record);

    int updateByPrimaryKey(SmsManage record);
}