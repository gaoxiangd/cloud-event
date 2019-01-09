package com.welian.mapper;

import com.welian.pojo.EventManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventListMapper {
    List<EventManage> searchForPage(@Param("beforeDeadLineTime") Boolean beforeDeadLineTime,
                                    @Param("eventId") Integer eventId,
                                    @Param("type") Integer type,
                                    @Param("state") Integer state,
                                    @Param("recommend") Integer recommend,
                                    @Param("hot") Integer hot,
                                    @Param("timeType") Integer timeType,
                                    @Param("startTime") String startTime,
                                    @Param("endTime") String endTime,
                                    @Param("orderType") Integer orderType,
                                    @Param("cityId") Integer cityId,
                                    @Param("eventName") String eventName,
                                    @Param("lineType") Integer lineType,
                                    @Param("offset") Integer page,
                                    @Param("limit") Integer size,
                                    @Param("nowTime") Long nowTime,
                                    @Param("recommendHomeStatus") Integer recommendHomeStatus,
                                    @Param("recommendFinnacingStatus") Integer recommendFinnacingStatus);

    Integer countValidEvent(@Param("beforeDeadLineTime") Boolean beforeDeadLineTime,
                            @Param("eventId") Integer eventId,
                            @Param("type") Integer type,
                            @Param("state") Integer state,
                            @Param("recommend") Integer recommend,
                            @Param("hot") Integer hot,
                            @Param("timeType") Integer timeType,
                            @Param("startTime") String startTime,
                            @Param("endTime") String endTime,
                            @Param("orderType") Integer orderType,
                            @Param("cityId") Integer cityId,
                            @Param("eventName") String eventName,
                            @Param("lineType") Integer lineType,
                            @Param("nowTime") Long nowTime,
                            @Param("recommendHomeStatus") Integer recommendHomeStatus,
                            @Param("recommendFinnacingStatus") Integer recommendFinnacingStatus);
}