package com.welian.mapper;

import com.welian.pojo.SmsManageOrder;
import com.welian.pojo.SmsManageOrderExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SmsManageOrderMapper {
    long countByExample(SmsManageOrderExample example);

    int deleteByExample(SmsManageOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsManageOrder record);

    int insertSelective(SmsManageOrder record);

    List<SmsManageOrder> selectByExample(SmsManageOrderExample example);

    SmsManageOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsManageOrder record, @Param("example") SmsManageOrderExample example);

    int updateByExample(@Param("record") SmsManageOrder record, @Param("example") SmsManageOrderExample example);

    int updateByPrimaryKeySelective(SmsManageOrder record);

    int updateByPrimaryKey(SmsManageOrder record);
}