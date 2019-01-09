package com.welian.mapper;

import com.welian.pojo.InvestorGroupRelation;
import com.welian.pojo.InvestorGroupRelationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvestorGroupRelationMapper {
    long countByExample(InvestorGroupRelationExample example);

    int deleteByExample(InvestorGroupRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvestorGroupRelation record);

    int insertSelective(InvestorGroupRelation record);

    List<InvestorGroupRelation> selectByExample(InvestorGroupRelationExample example);

    InvestorGroupRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvestorGroupRelation record, @Param("example") InvestorGroupRelationExample example);

    int updateByExample(@Param("record") InvestorGroupRelation record, @Param("example") InvestorGroupRelationExample example);

    int updateByPrimaryKeySelective(InvestorGroupRelation record);

    int updateByPrimaryKey(InvestorGroupRelation record);

    List<InvestorGroupRelation> selectUidListFromInvestorGroup(@Param("condition") String condition);
}