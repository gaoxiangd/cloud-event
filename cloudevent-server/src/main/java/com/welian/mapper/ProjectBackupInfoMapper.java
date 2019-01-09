package com.welian.mapper;

import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectBackupInfoMapper {

    long countByExample(ProjectBackupInfoExample example);

    int deleteByExample(ProjectBackupInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectBackupInfo record);

    int insertSelective(ProjectBackupInfo record);

    List<ProjectBackupInfo> selectByExample(ProjectBackupInfoExample example);

    ProjectBackupInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectBackupInfo record,
                                 @Param("example") ProjectBackupInfoExample example);

    int updateByExample(@Param("record") ProjectBackupInfo record,
                        @Param("example") ProjectBackupInfoExample example);

    int updateByPrimaryKeySelective(ProjectBackupInfo record);

    int updateByPrimaryKey(ProjectBackupInfo record);

}