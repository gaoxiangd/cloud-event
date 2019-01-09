package com.welian.mapper;

import com.welian.pojo.ProjectFeedbackBackup;
import com.welian.pojo.ProjectFeedbackBackupExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectFeedbackBackupMapper {
    long countByExample(ProjectFeedbackBackupExample example);

    int deleteByExample(ProjectFeedbackBackupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectFeedbackBackup record);

    int insertSelective(ProjectFeedbackBackup record);

    List<ProjectFeedbackBackup> selectByExample(ProjectFeedbackBackupExample example);

    ProjectFeedbackBackup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectFeedbackBackup record, @Param("example") ProjectFeedbackBackupExample example);

    int updateByExample(@Param("record") ProjectFeedbackBackup record, @Param("example") ProjectFeedbackBackupExample example);

    int updateByPrimaryKeySelective(ProjectFeedbackBackup record);

    int updateByPrimaryKey(ProjectFeedbackBackup record);

    List<ProjectFeedbackBackup> getFeedbackList(@Param("eventRecordId") Integer eventRecordId,
                                                @Param("start") Integer start,
                                                @Param("size") Integer size);


    List<Integer> selectOrderIdByPage(@Param("start") Integer start, @Param("size") Integer size);

}