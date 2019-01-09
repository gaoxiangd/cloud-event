package com.welian.service;

import com.welian.beans.cloudevent.admin.AdminReq;
import com.welian.beans.cloudevent.admin.AdminResp;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.commodity.beans.Liquidation;
import com.welian.commodity.beans.LiquidationBean;
import com.welian.mapper.EventOrderMapper;
import com.welian.pojo.Event;
import com.welian.service.impl.EventServiceImpl;
import com.welian.pojo.EventOrder;
import com.welian.pojo.EventOrderExample;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by zhaopu on 2018/1/9.
 */
@Service
public class AdminService {

    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventOrderModule eventOrderModule;
    @Autowired
    private EventOrderMapper eventOderMapper;

    /**
     * 财务系统-活动结算列表
     */
    public Object getEventSettlementes(AdminReq req) {
        final AdminResp adminRespR = new AdminResp();
        adminRespR.list = new ArrayList<>();
        final LiquidationBean liquidationBean = commodityRemoteService.getSettlementList(req.page,req.size);
        if (!Optional.ofNullable(liquidationBean).map(e -> e.pageData).isPresent()) {
            return adminRespR;
        }
        final List<EventOrder> eventOrders = Optional.ofNullable(liquidationBean.pageData.getList())
                .map( l -> l.stream().map(e -> e.batchNum).collect(Collectors.toList()))
                .filter(StringUtil::isNotEmpty)
                .map(e -> {
                    final EventOrderExample example = new EventOrderExample();
                    example.createCriteria().andBatchnumIn(e);
                    return example;
                })
                .map(eventOderMapper::selectByExample)
                .filter(StringUtil::isNotEmpty)
                .map(l -> l.stream().distinct().collect(Collectors.toList())
        ).orElseGet(ArrayList::new);
        final Map<String,List<Event>> eventTemp = new ConcurrentHashMap<>(); // 为了防止重复查询添加了本地缓存
        final CompletableFuture<Map<String,Event>> eventMapFucture = CompletableFuture.supplyAsync(() ->
                eventOrderModule.getEventsMapByBatchNums(eventOrders,eventTemp));
        final CompletableFuture<Map<Integer,UserResp>> userMapFucture = CompletableFuture.supplyAsync(() ->
                eventService.getAdminMapByIds(
                        eventOrders.stream().map(EventOrder::getEventId).collect(Collectors.toList()),eventTemp));
        final Map<String,Event> eventMap = eventMapFucture.join();
        final Map<Integer,UserResp> userMap = userMapFucture.join();
        for (Liquidation liquidation : liquidationBean.pageData.getList()) {
            AdminResp adminResp = new AdminResp();
            Event event = eventMap.get(liquidation.batchNum);
            if (event == null) {
                continue;
            }
            adminResp.eventId = event.getId();
            adminResp.eventTitle = event.getTitle();
            adminResp.eventType = event.getTemplateId();
            adminResp.intro = "活动结算";
            adminResp.price = liquidation.price;
            adminResp.createTime = liquidation.createTime;
            adminResp.status = liquidation.status;
            adminResp.reason = liquidation.note;
            adminResp.batchNum = liquidation.batchNum;
            adminResp.serviceAmount = liquidation.details.get(0).serviceAmount;
            adminResp.user = userMap.get(adminResp.eventId);
            adminRespR.list.add(adminResp);
        }
        adminRespR.count = (long)adminRespR.list.size();
        adminRespR.waitAmount = liquidationBean.waitSum;
        adminRespR.waitTotal = liquidationBean.waitNum;
        return adminRespR;
    }
}
