package com.welian.mapper;

import com.welian.beans.cloudevent.event.EventSearchPara;
import com.welian.beans.cloudevent.event.UserEventSearchPara;
import com.welian.beans.cloudevent.usermange.UserMangeResp;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {
    long countByExample(EventExample example);

    int deleteByExample(EventExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Event record);

    int insertSelective(Event record);

    int insertSelective2(Event record);

    List<Event> selectByExampleWithBLOBs(EventExample example);

    List<Event> selectByExample(EventExample example);

    Event selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExampleWithBLOBs(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExample(@Param("record") Event record, @Param("example") EventExample example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKeyWithBLOBs(Event record);

    int updateByPrimaryKey(Event record);

    List<Event> selectEventByPara(@Param("para") EventSearchPara eventSearchReq);

    Integer countEventList(@Param("para") EventSearchPara eventSearchReq);

    //查询系统中所有创建过活动的城市id列表
    List<Integer> selectEventCityIdList();

    //用户自己收藏活动列表
    List<Event> selectUserCollectionEventList(@Param("para") UserEventSearchPara eventSearchReq);
    //用户自己报名活动列表
//    List<Event> selectUserSignUpEventList(@Param("para") UserEventSearchPara eventSearchReq);
    //用户自己创建的活动列表
    List<Event> selectUserCreateEventList(@Param("para") UserEventSearchPara eventSearchReq);

    //app活动排序列表 推荐
    List<Event> selectOrderedeCommendList (@Param("para") UserEventSearchPara eventSearchReq);

    //app活动排序列表 最新活动
    List<Event> selectOrderedeNewList (@Param("para") UserEventSearchPara eventSearchReq);

    //app活动排序列表 最热活动
    List<Event> selectOrderedeHotList (@Param("para") UserEventSearchPara eventSearchReq);

   //查询机构下结束的收费活动
    List<Event> selectEndEvent (@Param("orgId") Integer orgId ,@Param("nowTime") Long nowTime);

    //查询昨天结束的收费活动
    List<Event> selectYesterdayEndEvent (@Param("startTime") Long startTime,@Param("endTime") Long endTime);

    //app创业活动根据筛选条件排序
    List<Event> selectCommonEventList(@Param("para") UserEventSearchPara eventSearchReq);

    //活动小程序列表
    List<Event> selectEventMiniList (@Param("para") UserEventSearchPara eventSearchReq);

    //统计机构下报名过的活动和人数
    List<UserMangeResp> selectSignupEvent(@Param("orgId") Integer orgId ,@Param("nowTime") Long nowTime );

    int countCoverCity();

    Integer countEventMiniList(@Param("para") UserEventSearchPara eventSearchReq);

    List<Event> selectByIds(@Param("ids") List<Integer> ids,@Param("state") byte state, @Param("nowTime") Long nowTime,@Param("count") int count);
}