package com.welian.mapper;

import com.welian.pojo.InvestorGroup;
import com.welian.pojo.InvestorGroupExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvestorGroupMapper {
    long countByExample(InvestorGroupExample example);

    int deleteByExample(InvestorGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvestorGroup record);

    int insertSelective(InvestorGroup record);

    List<InvestorGroup> selectByExample(InvestorGroupExample example);

    InvestorGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvestorGroup record, @Param("example") InvestorGroupExample example);

    int updateByExample(@Param("record") InvestorGroup record, @Param("example") InvestorGroupExample example);

    int updateByPrimaryKeySelective(InvestorGroup record);

    int updateByPrimaryKey(InvestorGroup record);

    /**
     * 根据事件活动的id获取此活动下所关联的投资人分组列表
     *
     * @param eventId
     * @return
     */
    List<InvestorGroup> selectListByEventId(Integer eventId);
}