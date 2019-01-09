package com.welian.mapper;

import com.welian.pojo.EventRecordProject;
import com.welian.pojo.EventRecordProjectExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRecordProjectMapper {
    long countByExample(EventRecordProjectExample example);

    int deleteByExample(EventRecordProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRecordProject record);

    int insertSelective(EventRecordProject record);

    List<EventRecordProject> selectByExample(EventRecordProjectExample example);

    EventRecordProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRecordProject record, @Param("example") EventRecordProjectExample example);

    int updateByExample(@Param("record") EventRecordProject record, @Param("example") EventRecordProjectExample example);

    int updateByPrimaryKeySelective(EventRecordProject record);

    int updateByPrimaryKey(EventRecordProject record);

    /**
     * 获取项目的报名记录
     *
     * @param eventId
     * @param pid
     * @return
     */
    List<EventRecordProject> getEventRecord(@Param("eventId") Integer eventId, @Param("pid") Integer pid);

    List<EventRecordProject> getEventRecordByExtensionLinkIdAndPid(@Param("extensionLinkId") Integer extensionLinkId,
                                                            @Param("pid") Integer pid);

}