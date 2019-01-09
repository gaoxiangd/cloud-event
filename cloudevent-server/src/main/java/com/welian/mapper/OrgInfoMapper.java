package com.welian.mapper;

import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgInfoMapper {
    long countByExample(OrgInfoExample example);

    int deleteByExample(OrgInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrgInfo record);

    int insertSelective(OrgInfo record);

    List<OrgInfo> selectByExample(OrgInfoExample example);

    OrgInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrgInfo record, @Param("example") OrgInfoExample example);

    int updateByExample(@Param("record") OrgInfo record, @Param("example") OrgInfoExample example);

    int selectByNewId(@Param("newId") Integer newId);

    int updateByPrimaryKeySelective(OrgInfo record);

    int updateByPrimaryKey(OrgInfo record);
}