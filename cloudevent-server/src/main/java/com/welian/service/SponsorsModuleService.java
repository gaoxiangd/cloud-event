package com.welian.service;

import com.welian.beans.cloudevent.Sponsor;
import com.welian.mapper.EventSponsorMapper;
import com.welian.mapper.EventSponsorRelationMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventSponsor;
import com.welian.pojo.EventSponsorExample;
import com.welian.pojo.EventSponsorManage;
import com.welian.pojo.EventSponsorRelation;
import com.welian.pojo.EventSponsorRelationExample;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayangshu on 17/7/7.
 */
@Service
public class SponsorsModuleService extends BaseModule<Sponsor> {
    @Autowired
    private EventSponsorMapper eventSponsorMapper;
    @Autowired
    private EventSponsorRelationMapper eventSponsorRelationMapper;


    public void save(Integer eventId, List<Sponsor> sponsors) {
        if (sponsors != null && sponsors.size() > 0) {
            List<EventSponsorRelation> sponsorRelationList = new ArrayList<>();
            for (Sponsor sponsorReq : sponsors) {
                int sponsorId = 0;
                if (!StringUtil.isEmpty(sponsorReq.name)) {
                    EventSponsorExample example = new EventSponsorExample();
                    //条件两边空格截掉
                    example.createCriteria().andNameEqualTo(sponsorReq.name.trim());
                    List<EventSponsor> eventSponsorList = eventSponsorMapper.selectByExample(example);
                    if (eventSponsorList != null && eventSponsorList.size() > 0) {
                        sponsorId = eventSponsorList.get(0).getId();
                    }
                }

                if (sponsorId == 0 && !NumberUtil.isValidId(sponsorReq.id)) {
                    EventSponsor eventSponsor = new EventSponsor();
                    eventSponsor.setName(sponsorReq.name);
                    eventSponsor.setCreateTime(System.currentTimeMillis());
                    eventSponsor.setModifyTime(System.currentTimeMillis());
                    eventSponsorMapper.insertSelective(eventSponsor);
                    sponsorId = eventSponsor.getId();
                } else if (sponsorId == 0 && sponsorReq.id != 0) {
                    sponsorId = sponsorReq.id;
                }

                EventSponsorRelation eventSponsorRelation = new EventSponsorRelation();
                eventSponsorRelation.setEventId(eventId);
                eventSponsorRelation.setSponsorId(sponsorId);
                eventSponsorRelation.setCreateTime(System.currentTimeMillis());
                eventSponsorRelation.setModifyTime(System.currentTimeMillis());
                sponsorRelationList.add(eventSponsorRelation);
            }
            eventSponsorRelationMapper.insertByBatch(sponsorRelationList);
        }
    }


    public void delete(Integer eventId) {
        EventSponsorRelationExample example = new EventSponsorRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        eventSponsorRelationMapper.deleteByExample(example);
    }


    public Object get(Integer eventId) {
        return eventSponsorMapper.selectSponsorByEventId(eventId);
    }




    public Sponsor conversePara(Object object) {
        if (object != null) {
            Sponsor sponsorReq = new Sponsor();
            sponsorReq.id = ((EventSponsor) object).getId();
            sponsorReq.name = ((EventSponsor) object).getName();
            return sponsorReq;
        }
        return null;
    }

    /**
     * 批量转换主办方的信息
     *
     * @param sponsorList
     * @return
     */
    public List converseParaList(List<EventSponsor> sponsorList) {
        List<Sponsor> sponsorReqList = new ArrayList<>();
        if (sponsorList != null && sponsorList.size() > 0) {
            for (EventSponsor sponsor : sponsorList) {
                sponsorReqList.add(conversePara(sponsor));
            }
        }
        return sponsorReqList;
    }

    /**
     * 搜索主办方
     *
     * @param keyword
     * @return
     */
    public List<EventSponsor> searchSponsor(String keyword) {
        if (keyword != null) {
            return eventSponsorMapper.searchSponsorByKeyword(keyword);
        }
        return null;
    }

    /**
     * 批量查询主办方
     *
     * @param
     * @return
     */
    public List<EventSponsorManage> selectSponsorsByEventIds(List<Integer> eventIds){
        List<EventSponsorManage> sponsors = eventSponsorMapper.selectSponsorsByEventIds(eventIds);
        return sponsors;
    }
}
