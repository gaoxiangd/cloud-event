package com.welian.mapper;

import com.welian.beans.cloudevent.statistical.StatisticsNumber;
import com.welian.pojo.ProjectReceivePerActivityOneDay;
import com.welian.pojo.ProjectReceivePerActivityOneDayExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectReceivePerActivityOneDayMapper {
    long countByExample(ProjectReceivePerActivityOneDayExample example);

    int deleteByExample(ProjectReceivePerActivityOneDayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectReceivePerActivityOneDay record);

    int insertSelective(ProjectReceivePerActivityOneDay record);

    List<ProjectReceivePerActivityOneDay> selectByExample(ProjectReceivePerActivityOneDayExample example);

    ProjectReceivePerActivityOneDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectReceivePerActivityOneDay record, @Param("example") ProjectReceivePerActivityOneDayExample example);

    int updateByExample(@Param("record") ProjectReceivePerActivityOneDay record, @Param("example") ProjectReceivePerActivityOneDayExample example);

    int updateByPrimaryKeySelective(ProjectReceivePerActivityOneDay record);

    int updateByPrimaryKey(ProjectReceivePerActivityOneDay record);

    int updateProjectCount(@Param("record") ProjectReceivePerActivityOneDay record, @Param("example") ProjectReceivePerActivityOneDayExample example);

    /**
     * 查找需要同步的渠道ids
     *
     * @param date
     * @return
     */
    List<Integer> selectChannelIdsNeededSync(Long date);

    Integer sumChannelProjectsCount(@Param("date") Long date, @Param("orgId") Integer orgId);

    Integer sumChannelTicketsCount(@Param("date") Long beginOfYesterday,@Param("orgId") Integer channelId);

    Integer sumActivitySignInCount(@Param("date") Long beginOfYesterday,@Param("orgId") Integer channelId);

    List<StatisticsNumber> selectEventIdsSum(@Param("nowTime") Long nowTime, @Param("endTime") Long endTime , @Param("eventIds") List<Integer> eventIds);


}