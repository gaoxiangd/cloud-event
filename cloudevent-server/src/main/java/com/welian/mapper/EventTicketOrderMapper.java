package com.welian.mapper;

import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventTicketOrderMapper {
    long countByExample(EventTicketOrderExample example);

    int deleteByExample(EventTicketOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventTicketOrder record);

    int insertSelective(EventTicketOrder record);

    List<EventTicketOrder> selectByExample(EventTicketOrderExample example);

    EventTicketOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventTicketOrder record, @Param("example") EventTicketOrderExample example);

    int updateByExample(@Param("record") EventTicketOrder record, @Param("example") EventTicketOrderExample example);

    int updateByPrimaryKeySelective(EventTicketOrder record);

    int updateByPrimaryKey(EventTicketOrder record);

    void batchInsert(List<EventTicketOrder> list);


    /**
//     * 获取有效的报名签到列表
//     *
//     * @param eventIds 活动IDs
//     * @param starttimesub90 开始时间的前90分钟
//     * @param starttimeadd120 开始时间的后120分钟
//     * @param uids 不包含的活动方的用户id列表
//     * @return
//     */
//    List<CheckInResp> selectValidTicketOrder(@Param("eventIds") List<Integer> eventIds, @Param("starttimesub90") Long starttimesub90,
//                                             @Param("starttimeadd120") Long starttimeadd120, @Param("uids") List<Integer> uids);

    /**
     * 获取有效的报名签到列表
     *
     * @param eventId 活动ID
     * @param starttimesub90 开始时间的前90分钟
     * @param starttimeadd120 开始时间的后120分钟
     * @param uids 不包含的活动方的用户id列表
     * @return
     */
    Integer selectValidTicketOrder(@Param("eventId") Integer eventId, @Param("starttimesub90") Long starttimesub90,
                                   @Param("starttimeadd120") Long starttimeadd120, @Param("uids") List<Integer> uids);



}