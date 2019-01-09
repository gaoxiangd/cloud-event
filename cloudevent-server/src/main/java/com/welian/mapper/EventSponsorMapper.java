package com.welian.mapper;

import com.welian.pojo.EventSponsor;
import com.welian.pojo.EventSponsorExample;
import com.welian.pojo.EventSponsorManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventSponsorMapper {
    long countByExample(EventSponsorExample example);

    int deleteByExample(EventSponsorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventSponsor record);

    int insertSelective(EventSponsor record);

    List<EventSponsor> selectByExample(EventSponsorExample example);

    EventSponsor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventSponsor record, @Param("example") EventSponsorExample example);

    int updateByExample(@Param("record") EventSponsor record, @Param("example") EventSponsorExample example);

    int updateByPrimaryKeySelective(EventSponsor record);

    int updateByPrimaryKey(EventSponsor record);

    List<EventSponsor> selectSponsorByEventId(Integer eventId);

    List<EventSponsor> searchSponsorByKeyword(@Param("keyword")String keyword);

    List<EventSponsorManage> selectSponsorsByEventIds(@Param("list") List<Integer> eventIds);

    int countSponsors();

}