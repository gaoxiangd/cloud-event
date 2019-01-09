package com.welian.service;

import com.welian.beans.cloudevent.manage.RmdAndHotImportReq;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventCityRelationMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCityRelationExample;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/10/26
 */
@Service
public class EventCityRelationModule extends BaseModule<EventCityRelation> {

    @Autowired
    private EventCityRelationMapper eventCityRelationMapper;


    public void delete(Integer eventId) {
        eventCityRelationMapper.deleteByPrimaryKey(eventId);
    }


    public Object get(Integer eventId) {
        return eventCityRelationMapper.selectByPrimaryKey(eventId);
    }


    public EventCityRelation conversePara(Object object) {
        if (object != null) {
            EventCityRelation eventCityRelation = new EventCityRelation();
            eventCityRelation.setId(((EventCityRelation) object).getId());
            eventCityRelation.setCreateTime(((EventCityRelation) object).getCreateTime());
            eventCityRelation.setRecommendState(((EventCityRelation) object).getRecommendState());
            eventCityRelation.setHotState(((EventCityRelation) object).getHotState());
            eventCityRelation.setEventId(((EventCityRelation) object).getEventId());
            eventCityRelation.setHotTime(((EventCityRelation) object).getHotTime());
            eventCityRelation.setCityId(((EventCityRelation) object).getCityId());
            eventCityRelation.setRecommendTime(((EventCityRelation) object).getRecommendTime());
            eventCityRelation.setModifyTime(((EventCityRelation) object).getModifyTime());
        }
        return null;
    }

    /**
     * 获取所有的推荐的活动的实体类 Integer-》eventId
     *
     * @return
     */
    public Map<Integer, EventCityRelation> getAllRecommendMap() {
        EventCityRelationExample example = new EventCityRelationExample();
        example.setGroupByClause("event_id");
        example.createCriteria().andRecommendStateEqualTo(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
        List<EventCityRelation> eventCityRelations = eventCityRelationMapper.selectByExample(example);
        Map<Integer, EventCityRelation> allRecommendMap = new HashMap<>();
        for (EventCityRelation eventCityRelation : eventCityRelations) {
            allRecommendMap.put(eventCityRelation.getEventId(), eventCityRelation);
        }
        return allRecommendMap;
    }

    /**
     * 获取所有热门或者推荐的EventCityRelation
     *
     * @param typeRecomment
     * @return
     */
    public List<EventCityRelation> getHotOrRecommendEvents(ParamEnum.HotOrRcmType typeRecomment,List<Integer> eventIds) {
        EventCityRelationExample example = new EventCityRelationExample();
        EventCityRelationExample.Criteria criteria=example.createCriteria();
        if (typeRecomment == ParamEnum.HotOrRcmType.TYPE_RECOMMENT) {
            criteria.andRecommendStateEqualTo(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
        } else if (typeRecomment == ParamEnum.HotOrRcmType.TYPE_HOT) {
            criteria.andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        }
        if(!StringUtil.isEmpty(eventIds)){
            criteria.andEventIdIn(eventIds);
        }
        List<EventCityRelation> relations = eventCityRelationMapper.selectByExample(example);
        return relations;
    }

    public void save(Integer eventId, RmdAndHotImportReq req) {
        EventCityRelation record = new EventCityRelation();
        record.setCityId(req.cityId);
        record.setHotState(req.hotState);
        record.setRecommendState(req.recommendState);
        record.setRecommendTime(req.recommendTime);
        record.setEventId(eventId);
        record.setCreateTime(DateUtil.getNowDate());
        eventCityRelationMapper.insertSelective(record);
    }

    public List<EventCityRelation> getCityHotEvent(Integer cityId, ParamEnum.HotOrRcmType typeHot) {
        List<EventCityRelation> responseList = new ArrayList<>();
        EventCityRelationExample example = new EventCityRelationExample();
        EventCityRelationExample.Criteria criteria=example.createCriteria();
        criteria.andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<Integer> cityIds=new ArrayList<>();
        //全国
        cityIds.add(-2);
        if(NumberUtil.isValidId(cityId)){
            cityIds.add(cityId);
            criteria.andCityIdIn(cityIds);
        }
        List<EventCityRelation> relations = eventCityRelationMapper.selectByExample(example);
        for (EventCityRelation relation : relations) {
            //去重
            if (!responseList.contains(relation.getEventId())) {
                responseList.add(relation);
            }
        }
        return responseList;
    }
}
