package com.welian.mapper;

import com.welian.pojo.ProjectList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectListMapper {

    List<ProjectList> selectProject(@Param("eventIds") String eventIds, @Param("orgId") Integer orgId,
                                    @Param("start") Integer start,
                                    @Param("size") Integer size,
                                    @Param("condition") String condition);

    int selectProjectCount(@Param("eventIds") String eventIds, @Param("orgId") Integer orgId, @Param("condition") String condition);
}