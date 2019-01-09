package com.welian.mapper;

import com.welian.beans.cloudevent.event.UserEventSearchPara;
import com.welian.beans.cloudevent.usermange.UserMangeResp;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRecordMapper {

    long countByExample(EventRecordExample example);

    int deleteByExample(EventRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EventRecord record);

    int insertSelective(EventRecord record);

    int insertSelective2(EventRecord record);

    List<EventRecord> selectByExample(EventRecordExample example);

    EventRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EventRecord record, @Param("example") EventRecordExample example);

    int updateByExample(@Param("record") EventRecord record, @Param("example") EventRecordExample example);

    int updateByPrimaryKeySelective(EventRecord record);

    int updateByPrimaryKey(EventRecord record);

    int hasSignUpCount(@Param("eventId") Integer eventId, @Param("pid") Integer pid);

    EventRecord getLastRecordInfo(@Param("recordId") Integer recordId, @Param("orgId") Integer orgId);

    EventRecord getNextRecordInfo(@Param("recordId") Integer recordId, @Param("orgId") Integer orgId);

    int batchUpdate(@Param("state") Byte state, @Param("reason") String reason, @Param("list") String list);

    List<EventRecord> selectUserSignUpEventRecordList(@Param("para") UserEventSearchPara eventSearchReq);


    int existRecord(@Param("uid") Integer uid);

    //取审核通过的的报名活动 的用户
    List<EventRecord> selectUserSignUpEventRecordAndOrgList(@Param("para") UserEventSearchPara eventSearchReq);

    //取审核通过的的报名活动 的用户 分组
    List<Integer> selectUserRecordAndOrgList(@Param("para") UserEventSearchPara eventSearchReq);

    //取全部报名过该机构的用户
    List<Integer> selectUserRecordAndOrgCount(@Param("para") UserEventSearchPara eventSearchReq);

    //查看用户的报名记录
    List<UserMangeResp> selectUserRecord(@Param("para") UserEventSearchPara eventSearchReq);


    //取报名过的活动id
    List<Integer> selectUserSignUpEventList(@Param("para") UserEventSearchPara eventSearchReq);
}
