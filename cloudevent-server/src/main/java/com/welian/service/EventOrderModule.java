package com.welian.service;

import com.welian.mapper.EventMapper;
import com.welian.mapper.EventOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventOrder;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * created by GaoXiang on 2018/3/8
 */
@Service
public class EventOrderModule {

    @Autowired
    private EventOrderMapper eventOderMapper;
    @Autowired
    private EventMapper eventMapper;

    /**
     *
     */
    public Map<String,Event> getEventsMapByBatchNums(List<EventOrder> eventOrders,Map<String,List<Event>> eventTemp) {
        Map<String,Event> returnMap = new HashMap<>();
        if(StringUtil.isEmpty(eventOrders)){
            return returnMap;
        }
        List<Integer> eventIds=new ArrayList<>();
        for (EventOrder eventOrder:eventOrders) {
            eventIds.add(eventOrder.getEventId());
        }
        final List<Event> events = Optional.ofNullable(eventTemp).orElseGet(HashMap::new)
                .computeIfAbsent(JSONUtil.obj2Json(eventIds),e -> {
                    EventExample eventExample = new EventExample();
                    eventExample.createCriteria().andIdIn(eventIds);
                    return eventMapper.selectByExample(eventExample);
                });
        Map<Integer,Event> eventMap = new HashMap<>();
        if(StringUtil.isEmpty(events)){
            return returnMap;
        }
        for(Event event:events){
            eventMap.put(event.getId(),event);
        }
        for(EventOrder eventOrder:eventOrders){
            if(eventMap.containsKey(eventOrder.getEventId())){
                returnMap.put(eventOrder.getBatchnum(),eventMap.get(eventOrder.getEventId()));
            }
        }
        return returnMap;
    }
}
