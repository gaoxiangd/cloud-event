package com.welian.mapper;

import com.welian.pojo.EventRecordJoinUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventRecordJoinUserMapper {

    /**
     * 搜索
     *
     * @param
     * @return
     */
    List<EventRecordJoinUser> searchForPage(@Param("eventId") Integer eventId,
                                     @Param("start") Integer start,
                                     @Param("size") Integer size,
                                     @Param("name") String name,
                                     @Param("phone") String phone,
                                     @Param("state") Integer state);
    /**
     * 搜索(不分页)
     *
     * @param
     * @return
     */
    List<EventRecordJoinUser> searchNoPage(@Param("eventId") Integer eventId,
                                     @Param("name") String name,
                                     @Param("phone") String phone,
                                     @Param("state") Integer state);



}
