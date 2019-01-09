package com.welian.mapper;

import com.welian.pojo.ProjectReceivePerOrgOneDay;
import com.welian.pojo.ProjectReceivePerOrgOneDayExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectReceivePerOrgOneDayMapper {
    long countByExample(ProjectReceivePerOrgOneDayExample example);

    int deleteByExample(ProjectReceivePerOrgOneDayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectReceivePerOrgOneDay record);

    int insertSelective(ProjectReceivePerOrgOneDay record);

    List<ProjectReceivePerOrgOneDay> selectByExample(ProjectReceivePerOrgOneDayExample example);

    ProjectReceivePerOrgOneDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectReceivePerOrgOneDay record, @Param("example") ProjectReceivePerOrgOneDayExample example);

    int updateByExample(@Param("record") ProjectReceivePerOrgOneDay record, @Param("example") ProjectReceivePerOrgOneDayExample example);

    int updateByPrimaryKeySelective(ProjectReceivePerOrgOneDay record);

    int updateByPrimaryKey(ProjectReceivePerOrgOneDay record);

    int updateProjectCount(@Param("record") ProjectReceivePerOrgOneDay record, @Param("example") ProjectReceivePerOrgOneDayExample example);

}