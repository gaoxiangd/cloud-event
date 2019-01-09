package com.welian.service;

import com.welian.beans.cloudevent.Guest;
import com.welian.config.CloudEventConfig;
import com.welian.mapper.EventGuestMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventGuest;
import com.welian.pojo.EventGuestExample;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayangshu on 17/7/7.
 */
@Service
public class GuestModuleService extends BaseModule<Guest> {
    @Autowired
    private EventGuestMapper eventGuestMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;


    public void save(Integer eventId, List<Guest> guests) {
        if (guests != null && guests.size() > 0) {
            List<EventGuest> eventGuestList = new ArrayList<>();
            for (Guest guestReq : guests) {
                EventGuest eventGuest = new EventGuest();
                eventGuest.setEventId(eventId);
                eventGuest.setAvatar(guestReq.avatar);
                eventGuest.setCompany(guestReq.company);
                eventGuest.setName(guestReq.name);
                eventGuest.setPosition(guestReq.position);
                eventGuest.setCreateTime(System.currentTimeMillis());
                eventGuest.setModifyTime(System.currentTimeMillis());

                eventGuestList.add(eventGuest);
            }
            eventGuestMapper.insertByBatch(eventGuestList);
        }
    }


    public void delete(Integer eventId) {
        EventGuestExample example = new EventGuestExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        eventGuestMapper.deleteByExample(example);
    }


    public Object get(Integer eventId) {
        EventGuestExample example = new EventGuestExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        return eventGuestMapper.selectByExample(example);
    }


    public Guest conversePara(Object object) {
        if (object != null) {
            Guest guestReq = new Guest();
            if (!StringUtil.isEmpty(((EventGuest) object).getAvatar())) {
                guestReq.avatar = cloudEventConfig.getImage_prefix() + ((EventGuest) object).getAvatar();
            }
            guestReq.name = ((EventGuest) object).getName();
            guestReq.position = ((EventGuest) object).getPosition();
            guestReq.company = ((EventGuest) object).getCompany();
            //3.3..0.1  新增
            guestReq.uid = ((EventGuest) object).getId();
            return guestReq;
        }
        return null;
    }

    /**
     * 批量转换嘉宾的信息
     *
     * @param guestList
     * @return
     */
    public List converseParaList(List<EventGuest> guestList) {
        List<Guest> guestReqList = new ArrayList<>();
        if (guestList != null && guestList.size() > 0) {
            for (EventGuest guest : guestList) {
                guestReqList.add(conversePara(guest));
            }

        }
        return guestReqList;
    }

    /**
     * 批量查找嘉宾的信息
     *
     * @param eventIds
     * @return
     */
    public List<EventGuest> selectBatchEventGuest(List<Integer> eventIds) {
        EventGuestExample example = new EventGuestExample();
        example.createCriteria().andEventIdIn(eventIds);
        List<EventGuest> eventGuests = eventGuestMapper.selectByExample(example);
        return eventGuests;
    }

}
