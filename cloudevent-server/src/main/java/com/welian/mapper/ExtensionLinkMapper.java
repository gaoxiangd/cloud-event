package com.welian.mapper;

import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExtensionLinkMapper {
    long countByExample(ExtensionLinkExample example);

    int deleteByExample(ExtensionLinkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtensionLink record);

    int insertSelective(ExtensionLink record);

    List<ExtensionLink> selectByExample(ExtensionLinkExample example);

    ExtensionLink selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtensionLink record, @Param("example") ExtensionLinkExample example);

    int updateByExample(@Param("record") ExtensionLink record, @Param("example") ExtensionLinkExample example);

    int updateByPrimaryKeySelective(ExtensionLink record);

    int updateByPrimaryKey(ExtensionLink record);
}