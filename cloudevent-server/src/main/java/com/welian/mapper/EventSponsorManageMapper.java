package com.welian.mapper;

import com.welian.pojo.EventSponsorManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventSponsorManageMapper {

    /**
     * eventIds 不为空时使用
     * @param eventIds
     * @return
     */
    List<EventSponsorManage> selectSponsorsByEventIds(List<Integer> eventIds);

}