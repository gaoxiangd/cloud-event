package com.welian.service;

import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.mapper.EventCityRelationMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCityRelationExample;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.ExtensionLinkServiceImpl;
import org.sean.framework.util.Logger;
import com.welian.utils.Base64Utils;
import com.welian.utils.CommonExecutorPool;
import com.welian.utils.DateUtil;
import com.welian.utils.EventSolrBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by zhaopu on 2018/1/15.
 */
@Service
public class EventSolrService {

    private static final Logger logger = Logger.newInstance(EventSolrService.class);


    @Autowired
    private SolrService solrService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private EventCityRelationMapper eventCityRelationMapper;


    /**
     *  同步solr
     */
    public void synchronousEventToSolr(EventPara event) {

        CommonExecutorPool.execute(new Runnable() {
            public void run() {
                String sponsorid = "";
                String sponsor_name = "";
                if (event.sponsors != null && event.sponsors.size() > 0) {
                    for (int i = 0; i < event.sponsors.size(); i++) {
                        Sponsor sponsor = event.sponsors.get(i);
                        if (i == 0) {
                            sponsorid = sponsor.id.toString();
                            sponsor_name = sponsor.name;
                        } else {
                            sponsorid = sponsorid + "," + sponsor.id.toString();
                            sponsor_name = sponsor_name + "," + sponsor.name;
                        }
                    }
                }

                EventSolrBean solrBean = new EventSolrBean();
                solrBean.id = event.eventId.toString();
                solrBean.title = event.title;
                solrBean.start_time = event.startTime;
                solrBean.end_time = event.endTime;
                solrBean.type = event.eventType.intValue();
                solrBean.joined_count = 0;
                if (event.eventType.intValue() == 1){
                    solrBean.joined_count_status = event.customModule.numberStatus == null ? 0 : 1;
                    solrBean.ticket_type = event.customModule.isCharge;
                    if (event.customModule.isCharge == 0){//免费
                        solrBean.limited = event.customModule.freeCount;
                        solrBean.amountStr = "免费";
                    }else {
                        solrBean.limited = 0;
                        Double amount = new Double(0);
                        for (int i = 0 ; i < event.customModule.ticketList.size() ; i++) {
                            Ticket ticket = event.customModule.ticketList.get(i);
                            solrBean.limited = solrBean.limited + ticket.count;
                            if (i == 0) {
                                amount = ticket.price;
                            }else {
                                int retval = amount.compareTo(ticket.price);
                                if (retval > 0) {
                                    amount = ticket.price;
                                }
                            }
                        }
                        solrBean.amountStr = "￥" + (amount / 100) + "起";
                    }
                }else {
                    solrBean.joined_count_status = event.projectModule.numberStatus == null ? 0: 1 ;
                    solrBean.limited = event.projectModule.projectFreeCount;
                    solrBean.ticket_type = event.projectModule.isFree.intValue();
                }
                solrBean.sort_type = 0;
                solrBean.sponsor = sponsorid;
                solrBean.sponsor_name = sponsor_name;
                solrBean.recommend_status = 0;
                solrBean.state = event.state.intValue();
                solrBean.city_name = event.city.name;
                solrBean.city_id = event.city.id;
                solrBean.logo = event.logo;
                solrBean.recommend_time = Long.valueOf(0);

                String uniqueKey = extensionLinkService.getDefaultLinkByEventId(event.eventId).getUniqueKey();
                solrBean.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                        ((uniqueKey+"&"+event.eventId).getBytes());

                solrService.addEventToSolr(solrBean);
            }
        });
    }


    /**
     *  更新solr
     */
    public void updataEventToSolr(EventPara event) {
        CommonExecutorPool.execute(new Runnable() {
            public void run() {
                String sponsorid = "";
                String sponsor_name = "";
                if (event.sponsors != null && event.sponsors.size() > 0) {
                    for (int i = 0; i < event.sponsors.size(); i++) {
                        Sponsor sponsor = event.sponsors.get(i);
                        if (i == 0) {
                            sponsorid = sponsor.id.toString();
                            sponsor_name = sponsor.name;
                        } else {
                            sponsorid = sponsorid + "," + sponsor.id.toString();
                            sponsor_name = sponsor_name + "," + sponsor.name;
                        }
                    }
                }


                solrService.updateSolrInfoById(event.eventId.toString(), "title", event.title);
                solrService.updateSolrInfoById(event.eventId.toString(), "start_time", event.startTime);
                solrService.updateSolrInfoById(event.eventId.toString(), "end_time", event.endTime);
                solrService.updateSolrInfoById(event.eventId.toString(), "sponsor", sponsorid.toString());
                solrService.updateSolrInfoById(event.eventId.toString(), "sponsor_name", sponsor_name);
                solrService.updateSolrInfoById(event.eventId.toString(), "state", event.state);
                solrService.updateSolrInfoById(event.eventId.toString(), "city_id", event.city.id);
                solrService.updateSolrInfoById(event.eventId.toString(), "city_name", event.city.name);
                solrService.updateSolrInfoById(event.eventId.toString(), "logo", event.logo);

                if (event.eventType.intValue() == 1){
                    solrService.updateSolrInfoById(event.eventId.toString(), "joined_count_status",  event.customModule.numberStatus == null ? 0: 1);
                    solrService.updateSolrInfoById(event.eventId.toString(), "ticket_type", event.customModule.isCharge.byteValue());
                    if (event.customModule.isCharge == 0){//免费
                        solrService.updateSolrInfoById(event.eventId.toString(), "limited", event.customModule.freeCount);
                        solrService.updateSolrInfoById(event.eventId.toString(), "amountStr", "免费");
                    }else {
                        Integer limited = 0;
                        Double amount = new Double(0);
                        for (int i = 0 ; i < event.customModule.ticketList.size() ; i++) {
                            Ticket ticket = event.customModule.ticketList.get(i);
                            limited = limited + ticket.count;
                            if (i == 0) {
                                amount = ticket.price;
                            }else {
                                int retval = amount.compareTo(ticket.price);
                                if (retval > 0) {
                                    amount = ticket.price;
                                }
                            }
                        }
                        solrService.updateSolrInfoById(event.eventId.toString(), "limited", limited);
                        solrService.updateSolrInfoById(event.eventId.toString(), "amountStr", "￥" + (amount / 100) + "起");
                    }
                }else {
                    solrService.updateSolrInfoById(event.eventId.toString(), "ticket_type", event.projectModule.isFree.intValue());
                    solrService.updateSolrInfoById(event.eventId.toString(), "limited", event.projectModule.projectFreeCount);
                    solrService.updateSolrInfoById(event.eventId.toString(), "joined_count_status", event.projectModule.numberStatus == null ? 0: 1);
                }
            }
        });
    }


    /**
     *  数据脚本迁移同步solr
     */
    public void dataSynchronousEventToSolr(EventPara event , Event event1) {

        CommonExecutorPool.execute(new Runnable() {
            public void run() {
                String sponsorid = "";
                String sponsor_name = "";
                if (event.sponsors != null && event.sponsors.size() > 0) {
                    for (int i = 0; i < event.sponsors.size(); i++) {
                        Sponsor sponsor = event.sponsors.get(i);
                        if (i == 0) {
                            sponsorid = sponsor.id.toString();
                            sponsor_name = sponsor.name;
                        } else {
                            sponsorid = sponsorid + "," + sponsor.id.toString();
                            sponsor_name = sponsor_name + "," + sponsor.name;
                        }
                    }
                }

                EventSolrBean solrBean = new EventSolrBean();
                solrBean.id = event.eventId.toString();
                solrBean.title = event.title;
                solrBean.start_time = event.startTime;
                solrBean.end_time = event.endTime;
                solrBean.type = event.eventType.intValue();
                solrBean.joined_count = 0;
                if (event.eventType.intValue() == 1){
                    solrBean.joined_count_status = event.customModule.numberStatus == null ? 0 : 1;
                    solrBean.ticket_type = event.customModule.isCharge;
                    if (event.customModule.isCharge == 0){//免费
                        solrBean.limited = event.customModule.freeCount;
                        solrBean.amountStr = "免费";
                    }else {
                        solrBean.limited = 0;
                        Double amount = new Double(0);
                        for (int i = 0 ; i < event.customModule.ticketList.size() ; i++) {
                            Ticket ticket = event.customModule.ticketList.get(i);
                            solrBean.limited = solrBean.limited + ticket.count;
                            if (i == 0) {
                                amount = ticket.price;
                            }else {
                                int retval = amount.compareTo(ticket.price);
                                if (retval > 0) {
                                    amount = ticket.price;
                                }
                            }
                        }
                        solrBean.amountStr = "￥" + (amount / 100) + "起";
                    }
                }else {
                    solrBean.joined_count_status = event.projectModule.numberStatus == null ? 0: 1 ;
                    solrBean.limited = event.projectModule.projectFreeCount;
                    solrBean.ticket_type = event.projectModule.isFree.intValue();
                }

                solrBean.sort_type = ParamEnum.EventSortType.DEGREE_NORMAL.getValue();

                EventCityRelationExample example = new EventCityRelationExample();
                EventCityRelationExample.Criteria criteria=example.createCriteria();
                criteria.andEventIdEqualTo(event1.getId());
                List<EventCityRelation> relations = eventCityRelationMapper.selectByExample(example);
                if (relations == null && relations.size() == 0) {
                    solrBean.recommend_status = 0;
                    solrBean.recommend_time = Long.valueOf(0);
                }else {
                    EventCityRelation eventCityRelation = relations.get(0);
                    solrBean.recommend_status = eventCityRelation.getRecommendState();
                    solrBean.recommend_time = eventCityRelation.getRecommendTime();

                    if (eventCityRelation.getRecommendState() == 1 && eventCityRelation.getRecommendTime() != null &&
                            (DateUtil.getNowDate().getTime() - eventCityRelation.getRecommendTime() <= 24 * 3600 * 1000)) {
                        solrBean.sort_type = ParamEnum.EventSortType.DEGREE_NEW.getValue();
                    }

                    if (eventCityRelation.getHotState() == 1) {
                        solrBean.sort_type = ParamEnum.EventSortType.DEGREE_HOT.getValue();
                    }
                }

                solrBean.sponsor = sponsorid;
                solrBean.sponsor_name = sponsor_name;
                solrBean.state = event.state.intValue();
                solrBean.city_name = event.city.name;
                solrBean.city_id = event.city.id;
                solrBean.logo = event.logo;

                String uniqueKey = extensionLinkService.getDefaultLinkByEventId(event.eventId).getUniqueKey();
                Base64KeyReq base = new Base64KeyReq();
                base.uniqueKey = uniqueKey;
                base.aid = event.eventId;
                solrBean.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                        ((uniqueKey+"&"+event.eventId).getBytes());

                solrService.addEventToSolr(solrBean);
            }
        });
    }






}
