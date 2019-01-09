package com.welian.mapper;

import com.welian.pojo.ProjectReceivePerLinkOneDay;
import com.welian.pojo.ProjectReceivePerLinkOneDayExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectReceivePerLinkOneDayMapper {
    long countByExample(ProjectReceivePerLinkOneDayExample example);

    int deleteByExample(ProjectReceivePerLinkOneDayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectReceivePerLinkOneDay record);

    int insertSelective(ProjectReceivePerLinkOneDay record);

    List<ProjectReceivePerLinkOneDay> selectByExample(ProjectReceivePerLinkOneDayExample example);

    ProjectReceivePerLinkOneDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectReceivePerLinkOneDay record, @Param("example") ProjectReceivePerLinkOneDayExample example);

    int updateByExample(@Param("record") ProjectReceivePerLinkOneDay record, @Param("example") ProjectReceivePerLinkOneDayExample example);

    int updateByPrimaryKeySelective(ProjectReceivePerLinkOneDay record);

    int updateByPrimaryKey(ProjectReceivePerLinkOneDay record);

    int updateProjectCount(@Param("record") ProjectReceivePerLinkOneDay record, @Param("example") ProjectReceivePerLinkOneDayExample example);

    /**
     * 查找需要同步的事件活动ids
     *
     * @param date
     * @return
     */
    List<Integer> selectActivityIdsNeededSync(Long date);

    /**
     * @param date
     * @param activityId
     */
    Integer sumActivityProjectsCount(@Param("date") Long date, @Param("activityId") Integer activityId);

    Integer sumActivityTicketsCount(@Param("date") Long beginOfYesterday,@Param("activityId") Integer activityId);

    Integer sumActivitySignInCount(@Param("date") Long beginOfYesterday,@Param("activityId") Integer activityId);
}