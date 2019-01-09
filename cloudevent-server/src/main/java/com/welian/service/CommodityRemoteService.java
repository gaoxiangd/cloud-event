package com.welian.service;

import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketCountResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.user.EventUserSignUpResp;
import com.welian.client.commodity.CommodityClient;
import com.welian.client.commodity.CommodityOrderClient;
import com.welian.client.commodity.CustomServiceClient;
import com.welian.client.commodity.LiquidationClient;
import com.welian.commodity.beans.Buyer;
import com.welian.commodity.beans.CustomServiceDetailReq;
import com.welian.commodity.beans.CustomServiceReq;
import com.welian.commodity.beans.Goods;
import com.welian.commodity.beans.GoodsQuery;
import com.welian.commodity.beans.LiquidateDetail;
import com.welian.commodity.beans.Liquidation;
import com.welian.commodity.beans.LiquidationBean;
import com.welian.commodity.beans.LiquidationQuery;
import com.welian.commodity.beans.Order;
import com.welian.commodity.beans.OrderDetail;
import com.welian.commodity.beans.OrderQuery;
import com.welian.commodity.beans.Property;
import com.welian.commodity.beans.Standard;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.commodity.EnumOrderStatus;
import com.welian.enums.commodity.EnumPlatform;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.service.impl.StateCustomModuleService;
import com.welian.service.impl.StateProjectModuleServiceImpl;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/12/1
 */
@Service
public class CommodityRemoteService {

    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private CommodityClient commodityClient;
    @Autowired
    private CommodityOrderClient commodityOrderClient;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private CustomServiceClient customServiceClient;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private LiquidationClient liquidationClient;

    /**
     * @param eventReq
     * @return
     */
    public Integer CreateCommodity(EventPara eventReq) {
        Integer commodityId;
        if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            //收费或免费票
            if (eventReq.customModule == null) {
                throw ExceptionUtil.createParamException("customModule参数异常");
            }
            if (eventReq.customModule.isCharge.equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue())) {
                //免费票
                commodityId = createFreeTicket(eventReq);
            } else {
                //收费票
                commodityId = createChargeTicket(eventReq);
            }

        } else {
            if (eventReq.projectModule == null) {
                throw ExceptionUtil.createParamException("projectModule参数异常");
            }
            commodityId = createFreeTicket(eventReq);
        }
        //生成默认免费票
        return commodityId;
    }

    private Integer createChargeTicket(EventPara eventReq) {
        List<Ticket> ticketList = eventReq.customModule.ticketList;
        if (StringUtil.isEmpty(ticketList)) {
            throw ExceptionUtil.createWarnException("请填写票券信息");
        }
        Goods goods = new Goods();
        goods.name = eventReq.title + Constant.DEFAULT_TICKET_NAME_APPEND;
        goods.subname = eventReq.title + Constant.DEFAULT_TICKET_NAME_APPEND;
        goods.description = eventReq.title + Constant.DEFAULT_TICKET_NAME_APPEND;
        goods.endEffectTime = new Date(eventReq.endTime);
        goods.platformId = cloudEventConfig.getCommodity_platform_id();
        List<Standard> standards = new ArrayList<>();
        Standard standard;
        List<Property> props;
        Property prop;
//        Integer priceAmount = 0;
        for (int i = 0; i < ticketList.size(); i++) {
            standard = new Standard();
            Ticket ticket = ticketList.get(i);
            standard.totalCount = ticket.count;
            standard.intro = ticket.intro;
            standard.name = ticket.title;
            standard.price = ticket.price.longValue();

            props = new ArrayList<>();
            prop = new Property();
            prop.showName = Constant.TICK_PROP_NAME;
            prop.value = i + "";
            prop.platformId = cloudEventConfig.getCommodity_platform_id();
            prop.code = Constant.DEFAULT_COMMODITY_PROPERTY_CODE;
            props.add(prop);
            standard.props = props;
            standards.add(standard);

//            priceAmount = priceAmount + ticket.price.intValue();
        }

//        if (priceAmount == 0) {
//            throw ExceptionUtil.createParamException("收费票的价格不能为0");
//        }

        goods.standards = standards;
        BaseResult<Integer> baseResult = commodityClient.save(goods);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Integer commodityId = baseResult.getData();
        return commodityId;

    }

    public Integer createFreeTicket(EventPara eventReq) {

        Goods goods = new Goods();
        goods.name = eventReq.title + Constant.DEFAULT_TICKET_NAME_APPEND;
        goods.subname = eventReq.title + Constant.DEFAULT_TICKET_NAME_APPEND;
        goods.endEffectTime = new Date(eventReq.endTime);
        goods.platformId = cloudEventConfig.getCommodity_platform_id();
        List<Standard> standards = new ArrayList<>();
        Standard standard = new Standard();

        //区别创业活动的免费票和路演的免费票
        if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            Integer allCount = eventReq.customModule.freeCount == 0 ? Constant.NO_LIMIT_NUMBER : eventReq
                    .customModule.freeCount;
            standard.totalCount = allCount;
        } else {
            if (eventReq.projectModule.projectFreeCount == null) {
                throw ExceptionUtil.createParamException("projectFreeCount参数异常");
            }
            standard.totalCount = eventReq.projectModule.projectFreeCount == 0 ? Constant.NO_LIMIT_NUMBER : eventReq
                    .projectModule.projectFreeCount;
        }
        standard.intro = Constant.DEFAULT_FREE_TICKET_INTRO;
        standard.name = Constant.FREE_TICKET_DEFAULT_NAME;
        standard.price = Constant.FREE_TICKET_DEFAULT_PRICE.longValue();
        List<Property> props = new ArrayList<>();
        Property prop;
        standard.props = new ArrayList<>();
        prop = new Property();
        prop.showName = Constant.TICK_PROP_NAME;
        prop.value = "0";
        prop.platformId = cloudEventConfig.getCommodity_platform_id();
        prop.code = Constant.DEFAULT_COMMODITY_PROPERTY_CODE;
        props.add(prop);
        standard.props = props;
        standards.add(standard);
        goods.standards = standards;
        BaseResult<Integer> baseResult = commodityClient.save(goods);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Integer commodityId = baseResult.getData();
        return commodityId;
    }

    /**
     * 路演大赛创建的票券和创业活动的免费票券调用商品服务更新（更新免费活动）
     *
     * @param eventReq
     * @param commodityId
     */
    public void updateCommodityForFree(EventPara eventReq, Integer commodityId) {
        Integer totalCount;
        if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            totalCount = eventReq.customModule.freeCount.equals(0) ?
                    Constant.NO_LIMIT_NUMBER : eventReq.customModule.freeCount;
        } else {
            totalCount = eventReq.projectModule.projectFreeCount.equals(0) ?
                    Constant.NO_LIMIT_NUMBER : eventReq.projectModule.projectFreeCount;
        }
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();

        List<Standard> standards = new ArrayList<>();
        Standard standard;
        standard = new Standard();
        if (StringUtil.isEmpty(goods.standards)) {
            throw ExceptionUtil.createParamException("商品服务传来的standards参数异常");
        }
        //免费-》免费
        if (goods.standards.size() == 1) {
            standard.id = goods.standards.get(0).id;
            standard.totalCount = totalCount;
            standard.intro = Constant.DEFAULT_FREE_TICKET_INTRO;
            standard.name = Constant.FREE_TICKET_DEFAULT_NAME;
            standard.price = 0l;
            standards.add(standard);
            //收费-》免费
        } else {
            for (Standard sd : goods.standards) {
                sd.delete = true;
                standards.add(sd);
            }
            Standard sdd = new Standard();
            sdd.totalCount = totalCount;
            sdd.intro = Constant.DEFAULT_FREE_TICKET_INTRO;
            sdd.name = Constant.FREE_TICKET_DEFAULT_NAME;
            sdd.price = 0l;
            standards.add(sdd);
        }
        goods.standards = standards;

        BaseResult<Void> base = commodityClient.modify(goods);
        if (!base.isSuccess()) {
            throw ExceptionUtil.createParamException(base.getErrormsg());
        }


    }

    /**
     * 更新收费活动
     *
     * @param eventReq
     * @param commodityId
     */
    public void updateCommodity(EventPara eventReq, Integer commodityId) {

        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        List<Ticket> ticketList = eventReq.customModule.ticketList;
        List<Standard> standards = new ArrayList<>();
        Standard standard;
        List<Property> props;
        Property prop;
        //检验商品规格，且返回要删除和编辑的standards
        Map<String, List<Standard>> returnMaps = checkCount(goods, ticketList);
        List<Standard> deleteStandards = returnMaps.get("deleteStandards");
//            //如果有删除的票券，要验证是否还存在未支付的订单，存在不允许删除
//            if(!StringUtil.isEmpty(deleteStandards)&& checkHasToPayOrder(eventReq.eventId)){
//               throw ExceptionUtil.createParamException("有用户正在确认付款中，无法删除票券");
//            }
        List<Standard> editStandards = returnMaps.get("editStandards");
        Map<Integer, Standard> editStandardsMap = getStandardsMap(editStandards);
        for (int i = 0; i < ticketList.size(); i++) {
            standard = new Standard();
            Ticket ticket = ticketList.get(i);
            if (ticket.id != null && !ticket.id.equals(0) && !StringUtil.isEmpty(editStandardsMap)) {
                //编辑的
                standard.id = ticket.id;
                //拿到原来的propId才能更改，不然就是新加
                prop = editStandardsMap.get(ticket.id).props == null ?
                        new Property() : editStandardsMap.get(ticket.id).props.get(0);
            } else {
                prop = new Property();
            }
            standard.totalCount = ticket.count;
            standard.intro = ticket.intro;
            standard.name = ticket.title;
            standard.price = ticket.price.longValue();
            props = new ArrayList<>();
            prop.showName = Constant.TICK_PROP_NAME;
            prop.value = i + "";
            prop.platformId = cloudEventConfig.getCommodity_platform_id();
            prop.code = Constant.DEFAULT_COMMODITY_PROPERTY_CODE;
            props.add(prop);
            standard.props = props;
            standards.add(standard);
        }
        //把删除的standards也加上
        standards.addAll(deleteStandards);
        goods.standards = standards;

        BaseResult<Void> baseResult1 = commodityClient.modify(goods);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult1.getErrormsg());
        }

    }

    private Map<String, List<Standard>> checkCount(Goods goods, List<Ticket> ticketList) {

        Map<String, List<Standard>> resultMap = new HashMap<>();
        Map<Integer, Ticket> ticketMap = new HashMap<>();
        //获取要保存的规格Ids
        List<Integer> saveStandardIds = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketMap.put(ticket.id, ticket);
            if (ticket.id != null && ticket.id != 0) {
                saveStandardIds.add(ticket.id);
            }
        }
        //获取删除和编辑的规格
        List<Standard> deleteStandards = new ArrayList<>();
        List<Standard> editStandards = new ArrayList<>();
        for (Standard standard : goods.standards) {
            if (!saveStandardIds.contains(standard.id)) {
                //把delete字段置为true,用于调编辑商品接口删除该规格
                standard.delete = true;
                deleteStandards.add(standard);
            } else {
                editStandards.add(standard);
            }
        }
        //判断删除规格数量是否异常
        for (Standard standard : deleteStandards) {
            if (!standard.totalCount.equals(standard.count)) {
                throw ExceptionUtil.createParamException(standard.name + "已经有人购买，无法删除");
            }
        }
        //判断编辑规格数量是否异常
        for (Standard standard : editStandards) {
            if (standard.totalCount - standard.count > ticketMap.get(standard.id).count) {
                throw ExceptionUtil.createParamException(standard.name + "的票种数量不能小于已报名数量");
            }
        }

        resultMap.put("deleteStandards", deleteStandards);
        resultMap.put("editStandards", editStandards);
        return resultMap;
    }

    public List<Ticket> converseToTicket(Integer commodityId) {
        if (commodityId == null || commodityId.equals(0)) {
            List<Ticket> ticketList = new ArrayList<>();
            return ticketList;
        }
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        List<Ticket> tickets = goodsToTicketList(goods);
        return tickets;
    }

    private List<Ticket> goodsToTicketList(Goods goods) {
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket;
        if (StringUtil.isEmpty(goods.standards)) {
            throw ExceptionUtil.createParamException("standards参数异常");
        }
        if (!StringUtil.isEmpty(goods.standards.get(0).props)) {
            Collections.sort(goods.standards, (o1, o2) -> {
                int i = Integer.parseInt(o1.props.get(0).value) - Integer.parseInt(o2.props.get(0).value);
                return i;
            });
        }
        if (!StringUtil.isEmpty(goods.standards)) {
            for (Standard standard : goods.standards) {
                ticket = new Ticket();
                ticket.id = standard.id;
                ticket.count = standard.totalCount;
                ticket.intro = standard.intro;
                ticket.price = standard.price.doubleValue();
                ticket.title = standard.name;
                ticket.remaindCount = standard.count;
                ticket.buyCount = standard.totalCount - standard.count;
                ticket.hasRecord = standard.totalCount - standard.count == 0 ?
                        ParamEnum.HasRrcord.TYPE_HAS_NOT_RECORD.getValue() : ParamEnum.HasRrcord.TYPE_HAS_RECORD
                        .getValue();
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public Map<Integer, Ticket> converseToTicketMap(Integer commodityId) {
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        Map<Integer, Ticket> tickets = new HashMap<>();
        Ticket ticket;
        if (!StringUtil.isEmpty(goods.standards)) {
            for (Standard standard : goods.standards) {
                ticket = new Ticket();
                ticket.id = standard.id;
                ticket.count = standard.totalCount;
                ticket.intro = standard.intro;
                ticket.price = standard.price.doubleValue();
                ticket.title = standard.name;
                ticket.remaindCount = standard.count;
                ticket.hasRecord = standard.totalCount - standard.count == 0 ?
                        ParamEnum.HasRrcord.TYPE_HAS_NOT_RECORD.getValue() : ParamEnum.HasRrcord.TYPE_HAS_RECORD
                        .getValue();
                tickets.put(ticket.id, ticket);
            }
        }
        return tickets;
    }

    public String createOrder(Event event, CustomSignUpReq req, Long expireTime, String tradeNo, Integer channel) {
        if (req.user == null) {
            throw ExceptionUtil.createParamException("user参数异常");
        }
        Order order = new Order();
        order.orderNo = tradeNo;
        order.orderType = SqlEnum.OrderType.TYPE_EVENT.getValue();
        //优惠价格设置为0
        order.discountAmount = 0l;
        order.isonline = SqlEnum.EventLineType.TYPE_ONLINE.getValue();
        if(SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(req.isImportRecord)){
            order.isonline = SqlEnum.EventLineType.TYPE_OFFLINE.getValue();
        }
        order.platformId = cloudEventConfig.getCommodity_platform_id();
        order.uid = req.user.uid;
        order.failureTime = expireTime;
        order.operEndTime = new Date(event.getEndTime());
        Buyer user = new Buyer();
        user.uid = req.user.uid;
        user.company = req.user.company;
        user.name = req.user.name;
        user.phone = req.user.mobile;
        user.position = req.user.position;
        order.buyer = user;
        if (channel != null) {
            order.channel = channel;
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        Map<Integer, Ticket> ticketsMap = converseToTicketMap(event.getCommodityId());
        EventStateCustomExample example = new EventStateCustomExample();
        example.createCriteria().andEventIdEqualTo(event.getId());
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(example);
        if (StringUtil.isEmpty(eventStateCustoms)) {
            throw ExceptionUtil.createParamException("EventStateCustom参数异常");
        }
        //免费活动
        if (eventStateCustoms.get(0).getCostType().equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().byteValue())) {
            List<Ticket> tickets = converseToTicket(event.getCommodityId());
            if (tickets.size() != 1) {
                throw ExceptionUtil.createParamException("商品传来的规格参数异常");
            }
            Ticket ticket = tickets.get(0);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.commodityId = event.getCommodityId();
            orderDetail.standardId = ticket.id;
            orderDetail.price = ticket.price.longValue();
            orderDetail.payPrice = 0l;
            orderDetail.discountAmount = 0l;
            orderDetail.count = 1;
            orderDetails.add(orderDetail);
        }
        //收费
        else {
            OrderDetail orderDetail;
            for (Ticket ticket : req.ticketList) {
                orderDetail = new OrderDetail();
                orderDetail.commodityId = event.getCommodityId();
                orderDetail.standardId = ticket.id;
                orderDetail.price = ticketsMap.get(ticket.id).price.longValue();
                orderDetail.payPrice = ticketsMap.get(ticket.id).price.longValue();
                orderDetail.discountAmount = 0l;
                orderDetail.count = ticket.buyCount;
                orderDetails.add(orderDetail);
            }
        }
        order.orderDetails = orderDetails;
        BaseResult<String> baseResult = commodityOrderClient.order(order);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }

    public String createOrderForProject(Event event, com.welian.beans.cloudevent.user.UserReq userReq, Long
            expireTime, String tradeNo, boolean isOnline) {
        if (userReq == null) {
            throw ExceptionUtil.createParamException("user参数异常");
        }
        Order order = new Order();
        order.orderNo = tradeNo;
        order.orderType = SqlEnum.OrderType.TYPE_EVENT.getValue();
        //优惠价格设置为0
        order.discountAmount = 0l;
        order.isonline = event.getLineType().intValue();
        order.platformId = cloudEventConfig.getCommodity_platform_id();
        order.uid = userReq.uid;
        if (isOnline) {
            order.isonline = SqlEnum.EventLineType.TYPE_ONLINE.getValue();
        } else {
            order.isonline = SqlEnum.EventLineType.TYPE_OFFLINE.getValue();
        }
        order.failureTime = expireTime;
        order.operEndTime = new Date(event.getEndTime());
        Buyer user = new Buyer();
        user.uid = userReq.uid;
        user.company = userReq.company;
        user.name = userReq.name;
        user.phone = userReq.mobile;
        user.position = userReq.position;
        order.buyer = user;
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<Ticket> tickets = converseToTicket(event.getCommodityId());
        if (tickets.size() != 1) {
            throw ExceptionUtil.createParamException("商品传来的规格参数异常");
        }
        Ticket ticket = tickets.get(0);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.commodityId = event.getCommodityId();
        orderDetail.standardId = ticket.id;
        orderDetail.price = ticket.price.longValue();
        orderDetail.payPrice = 0l;
        orderDetail.discountAmount = 0l;
        orderDetail.count = 1;
        orderDetails.add(orderDetail);
        order.orderDetails = orderDetails;
        BaseResult<String> baseResult = commodityOrderClient.order(order);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();

    }

    public Order getOrder(String tradeNo) {
        BaseResult<Order> baseResult = commodityOrderClient.get(tradeNo);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }


    public String getOrderNo() {

        Integer platformId = cloudEventConfig.getCommodity_platform_id();
        BaseResult<String> baseResult = commodityOrderClient.orderNo(platformId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }

    public void refuseOrder(Integer recordId) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(recordId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("报名记录不存在");
        }
        //调用商品接口把库存释放
        CustomServiceReq req = new CustomServiceReq();
        req.commodityOrderNo = eventRecord.getTradeNo();
        EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
        eventTicketOrderExample.createCriteria().andEventRecordIdEqualTo(recordId);
        List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(eventTicketOrderExample);
        req.customServiceDetailReqs = new ArrayList<>();
        req.price = Constant.FREE_PRICE_LONG;
        req.uid = eventRecord.getUid();
        CustomServiceDetailReq customServiceDetailReq;
        for (EventTicketOrder eventTicketOrder : eventTicketOrders) {
            customServiceDetailReq = new CustomServiceDetailReq();
            customServiceDetailReq.commodityOrderDetailId = eventTicketOrder.getCommodityDetailId();
            //免费票传0
            customServiceDetailReq.price = Constant.FREE_PRICE_LONG;
            req.customServiceDetailReqs.add(customServiceDetailReq);
        }

        EventTicketOrderExample example = new EventTicketOrderExample();
        example.createCriteria().andEventRecordIdEqualTo(recordId);
        Long count = eventTicketOrderMapper.countByExample(example);
        Event event = eventMapper.selectByPrimaryKey(eventRecord.getEventId());
        //更新eventRecord的状态
        EventRecord eventRecordReq = new EventRecord();
        eventRecordReq.setId(recordId);
        eventRecordReq.setState(SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue());
        eventRecordMapper.updateByPrimaryKeySelective(eventRecordReq);
        //更新eventTicketOrder的state为1 异常
        EventTicketOrderExample example1 = new EventTicketOrderExample();
        example1.createCriteria().andEventRecordIdEqualTo(recordId);
        EventTicketOrder orderReq = new EventTicketOrder();
        orderReq.setTicketState(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue().intValue());
        eventTicketOrderMapper.updateByExampleSelective(orderReq, example1);
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            //报名票数统计,减回来
            EventStateCustom eventStateCustom = stateCustomModuleService.get(eventRecord.getEventId());
            stateCustomModuleService.updateRecordCount(eventStateCustom, -count.intValue());
        } else {
            stateProjectModuleService.updateRecordCount(event.getId(), -count.intValue());
        }
        customServiceClient.save(req);
    }
    public void onlyRefuseOrder(Integer recordId) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(recordId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("报名记录不存在");
        }
        //调用商品接口把库存释放
        CustomServiceReq req = new CustomServiceReq();
        req.commodityOrderNo = eventRecord.getTradeNo();
        req.customServiceDetailReqs = new ArrayList<>();
        req.price = Constant.FREE_PRICE_LONG;
        req.uid = eventRecord.getUid();
        req.customServiceDetailReqs = new ArrayList<>();
        Order order = getOrder(eventRecord.getTradeNo());
        CustomServiceDetailReq customServiceDetailReq;
        for(OrderDetail detail:order.orderDetails) {
            customServiceDetailReq = new CustomServiceDetailReq();
            customServiceDetailReq.commodityOrderDetailId = detail.id;
            //免费票传0
            customServiceDetailReq.price = Constant.FREE_PRICE_LONG;
            req.customServiceDetailReqs.add(customServiceDetailReq);
        }
        customServiceClient.save(req);
    }

    public Integer getFreeTicketCount(Integer commodityId) {

        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        if (goods.standards.size() != 1) {
            throw ExceptionUtil.createParamException("商品服务传来的standards参数异常");
        }
        return goods.standards.get(0).totalCount;


    }

    public Map<Integer, Integer> getFreeTicketsCountMap(List<Integer> commodityIds) {
        Map<Integer, Integer> goodsMap = new HashMap<>();
        if (StringUtil.isEmpty(commodityIds)) {
            return goodsMap;
        }
        GoodsQuery req = new GoodsQuery();
        req.ids = commodityIds;
        req.withStandards = true;
        BaseResult<PageData<Goods>> baseResult = commodityClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Goods> goods = baseResult.getData().getList();
        for (Goods good : goods) {
            goodsMap.put(good.id, good.standards.get(0).totalCount);
        }
        return goodsMap;
    }

    public Map<Integer, Standard> getStandardsMap(List<Standard> standards) {
        Map<Integer, Standard> returnMap = new HashMap<>();
        for (Standard stand : standards) {
            returnMap.put(stand.id, stand);
        }
        return returnMap;
    }

    public Map<String, Integer> getTicketCountByOrders(List<String> tradeNos) {

        Map<String, Integer> returnMap = new HashMap<>();
        OrderQuery query = new OrderQuery();
        query.orderNos = tradeNos;
        query.setSize(Integer.MAX_VALUE);
        query.setPage(1);
        BaseResult<PageData<Order>> baseResult = commodityOrderClient.query(query);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Order> orders = baseResult.getData().getList();

        for (Order order : orders) {
            returnMap.put(order.orderNo, order.count);
        }
        return returnMap;
    }

    public TicketCountResp getCountByCommodity(Integer commodityId) {
        TicketCountResp response = new TicketCountResp();

        if (commodityId.equals(0)) {
            throw ExceptionUtil.createParamException("异常commodityId参数");
        }

        List<Ticket> tickets = converseToTicket(commodityId);
        Integer allCount = 0;
        Integer allBuyCount = 0;
        Integer allRemainedCount = 0;
        for (Ticket ticket : tickets) {
            allBuyCount += ticket.buyCount;
            allCount += ticket.count;
            allRemainedCount += ticket.remaindCount;
        }
        response.allBuyCount = allBuyCount;
        response.allCount = allCount;
        response.allRemainedCount = allRemainedCount;
        return response;
    }

    /**
     * 检查该活动下是否还存在待支付的订单
     *
     * @param eventId
     * @return true 存在报名记录，false 不存在报名记录
     */
    public Boolean checkHasToPayOrder(Integer eventId) {

        Boolean flag = false;
        //待支付的订单在record主表必然是ticket_lock为1
        EventRecordExample recordExample = new EventRecordExample();
        recordExample.createCriteria().andEventIdEqualTo(eventId).andTicketLockEqualTo
                (SqlEnum.TicketLockType.TYPE_LOCK.getValue());
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(recordExample);
        List<String> orderNos = new ArrayList<>();
        for (EventRecord record : eventRecords) {
            orderNos.add(record.getTradeNo());
        }
        //还没报名记录
        if (StringUtil.isEmpty(orderNos)) {
            return flag;
        }
        OrderQuery query = new OrderQuery();
        query.orderNos = orderNos;
        query.setSize(Integer.MAX_VALUE);
        query.setPage(1);
        BaseResult<PageData<Order>> baseResult = commodityOrderClient.query(query);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Order> orders = baseResult.getData().getList();
        for (Order order : orders) {
            if (order.status.equals(EnumOrderStatus.TOPAY.getValue())) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    /**
     * 根据多个商品号，返回key：商品号，value：票务信息的格式
     *
     * @param commodityIds
     * @return
     */
    public Map<Integer, List<Ticket>> getTicketsMap(List<Integer> commodityIds) {

        Map<Integer, List<Ticket>> ticketsMap = new HashMap<>();

        if (StringUtil.isEmpty(commodityIds)) {
            return ticketsMap;
        }
        GoodsQuery req = new GoodsQuery();
        req.ids = commodityIds;
        req.withStandards = true;
        BaseResult<PageData<Goods>> baseResult = commodityClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Goods> goods = baseResult.getData().getList();
        for (Goods good : goods) {
            ticketsMap.put(good.id, goodsToTicketList(good));
        }
        return ticketsMap;
    }

    public Map<Integer, Ticket> getTicketMap(Integer commodityId) {
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        Map<Integer, Ticket> map = new HashMap<>();
        List<Ticket> tickets = goodsToTicketList(goods);
        if (!StringUtil.isEmpty(tickets)) {
            tickets.forEach(ticket -> {
                map.put(ticket.id, ticket);
            });
        }
        return map;
    }


    public List<Goods> getCommodityGoods(List<Integer> commodityIds) {

        if (StringUtil.isEmpty(commodityIds)) {
            return null;
        }
        GoodsQuery req = new GoodsQuery();
        req.ids = commodityIds;
        req.withIncome = true;
        BaseResult<PageData<Goods>> baseResult = commodityClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Goods> goods = baseResult.getData().getList();

        return goods;
    }


    public Goods getCommodityGood(Integer commodityId) {

        if (commodityId == null) {
            return null;
        }
        GoodsQuery req = new GoodsQuery();
        req.id = commodityId;
        req.withIncome = true;
        BaseResult<PageData<Goods>> baseResult = commodityClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Goods> goods = baseResult.getData().getList();
        if (goods == null || goods.size() == 0) {
            return null;
        }

        return goods.get(0);
    }


    public Liquidation getLiquidations(String batchNums) {

        if (StringUtil.isEmpty(batchNums)) {
            return null;
        }
        LiquidationQuery req = new LiquidationQuery();
        req.batchNum = batchNums;

        BaseResult<LiquidationBean> baseResult = liquidationClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }

        LiquidationBean liquidationBean = baseResult.getData();
        if (liquidationBean == null || liquidationBean.pageData == null) {
            return null;
        }

        List<Liquidation> liquidations = liquidationBean.pageData.getList();
        if (liquidations == null || liquidations.size() == 0) {
            return null;
        }
        return liquidations.get(0);
    }


    public BaseResult<String> settlementapply(Integer commodityId, Integer createUid, Integer payeeUid, Long price, String intro) {

        Liquidation req = new Liquidation();
        req.price = price;
        req.platformId = cloudEventConfig.getCommodity_platform_id();
        req.createUid = createUid;
        req.commodityId = commodityId;
        req.intro = intro;
        List<LiquidateDetail> details = new ArrayList<>();
        LiquidateDetail liquidateDetail = new LiquidateDetail();
        liquidateDetail.payeeUid = payeeUid;
        liquidateDetail.price = price;

        //最低1块手续费  1%手续费
        if (price >= 10000) {
            Double serviceAmount = price * cloudEventConfig.getPoundage();
            liquidateDetail.serviceAmount = Math.round(serviceAmount);
        } else {
            liquidateDetail.serviceAmount = Long.valueOf(100);
        }
        details.add(liquidateDetail);
        req.details = details;

        BaseResult<String> baseResult = liquidationClient.liquidate(req);
        return baseResult;
//        if (!baseResult.isSuccess()) {
//            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
//        }
//        String string = baseResult.getData();
//        return string;
    }


    public LiquidationBean getSettlementList(Integer page, Integer size) {

        LiquidationQuery req = new LiquidationQuery();
        req.setPage(page);
        req.setSize(size);
        req.platformId = EnumPlatform.EVENT.getValue();
        req.withDetails = true;

        BaseResult<LiquidationBean> baseResult = liquidationClient.query(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        LiquidationBean liquidationBean = baseResult.getData();
        if (liquidationBean == null) {
            return null;
        }
        return liquidationBean;
    }

    /**
     * app调用获取多个订单的票信息,该方法传回的count值代表买的数量，bug表示该票总购买数量
     *
     * @param tradeNoLists
     */
    public List<Ticket> getTicketsForApp(List<String> tradeNoLists, Integer commodityId) {
        List<Ticket> tickets = new ArrayList<>();
        if(StringUtil.isEmpty(tradeNoLists)){
            return tickets;
        }
        Map<Integer, Ticket> map = getTicketMap(commodityId);
        OrderQuery query = new OrderQuery();
        query.orderNos = tradeNoLists;
        query.setSize(Integer.MAX_VALUE);
        query.setPage(1);
        query.withDetail = true;
        BaseResult<PageData<Order>> baseResult = commodityOrderClient.query(query);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Order> orders = baseResult.getData().list;

        if (!StringUtil.isEmpty(orders)) {
            Map<Integer, Ticket> ticketMap = new HashMap();
            ;
            for (Order order : orders) {
                for (OrderDetail orderDetail : order.orderDetails) {
                    if (ticketMap.containsKey(orderDetail.standardId)) {
                        Ticket tick = ticketMap.get(orderDetail.standardId);
                        tick.count += 1;
                        continue;
                    }
                    Ticket ticket = new Ticket();
                    ticket.id = orderDetail.standardId;
                    ticket.price = orderDetail.price.doubleValue();
                    ticket.intro = orderDetail.standardIntro;
                    ticket.title = orderDetail.standardName;
                    ticket.count = 1;
                    //这里app的count表示活动购买的票数
                    ticket.buyCount = map.get(orderDetail.standardId).buyCount;
                    ticketMap.put(ticket.id, ticket);
                }
            }
            for (Integer key : ticketMap.keySet()) {
                tickets.add(ticketMap.get(key));
            }
        }
        return tickets;
    }

    /**
     * 修改商品的下架时间
     *
     * @param commodityId
     * @param endTime
     */
    public void changeCommodityEndTime(Integer commodityId, Long endTime) {
        if (commodityId == null || commodityId == 0) {
            throw ExceptionUtil.createParamException("commodityId参数异常");
        }
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        goods.endEffectTime = new Date(endTime);
        BaseResult<Void> result = commodityClient.modify(goods);
        if (!result.isSuccess()) {
            throw ExceptionUtil.createParamException(result.getErrormsg());
        }
    }

    public Map<String, Order> getOrdersByRecords(List<String> tradeNos) {
        Map<String, Order> returnMap = new HashMap<>();
        OrderQuery query = new OrderQuery();
        query.orderNos = tradeNos;
        query.withDetail = true;
        query.setSize(Integer.MAX_VALUE);
        query.setPage(1);
        BaseResult<PageData<Order>> baseResult = commodityOrderClient.query(query);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Order> orders = baseResult.getData().getList();
        for (Order order : orders) {
            returnMap.put(order.orderNo, order);
        }
        return returnMap;
    }

    public List<EventUserSignUpResp> getUserTicketInfo(List<EventRecord> eventRecords) {
        List<EventUserSignUpResp> response = new ArrayList<>();
        List<String> tradeNos = new ArrayList<>();
        Map<String, EventRecord> eventRecordMap = new HashMap<>();
        for (EventRecord eventRecord : eventRecords) {
            tradeNos.add(eventRecord.getTradeNo());
            eventRecordMap.put(eventRecord.getTradeNo(), eventRecord);
        }
        OrderQuery query = new OrderQuery();
        query.orderNos = tradeNos;
        query.withDetail = true;
        query.setSize(Integer.MAX_VALUE);
        query.setPage(1);
        BaseResult<PageData<Order>> baseResult = commodityOrderClient.query(query);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        List<Order> orders = baseResult.getData().getList();
        for (Order order : orders) {
            EventUserSignUpResp userSignUpResp = new EventUserSignUpResp();
            Map<Integer, String> standards = new HashMap<>();
            List<Integer> standardIds = new ArrayList<>();
            for (OrderDetail orderDetail : order.orderDetails) {
                if (standards.containsKey(orderDetail.standardId)) {
                    continue;
                } else {
                    standards.put(orderDetail.standardId, orderDetail.standardName);
                    standardIds.add(orderDetail.standardId);
                }
            }
            userSignUpResp.url = eventRecordMap.get(order.orderNo).getTicketUrl();
            userSignUpResp.signUpTime = eventRecordMap.get(order.orderNo).getCreateTime();
            userSignUpResp.signUpState = eventRecordMap.get(order.orderNo).getState().intValue();
            userSignUpResp.totalCount = order.orderDetails.size();
            userSignUpResp.totalAmount = order.price.intValue() == 0 ? "免费" : "&yen;" + order.price.doubleValue() / 100;
            userSignUpResp.payState = order.status.equals(0) ? ParamEnum.PayState.PAY_NO.getValue() : ParamEnum
                    .PayState.PAY_YES.getValue();
            //如果有两种以上的票券
            if (standards.size() > 1) {
                userSignUpResp.name = standards.get(standardIds.get(0)) + "...";
            } else {
                userSignUpResp.name = standards.get(standardIds.get(0));
            }
            response.add(userSignUpResp);
        }
        return response;
    }

    /**
     * 删除商品
     * @param commodityId 商品id
     */
    public void deleteCommodity(Integer commodityId) {

        BaseResult<Void> baseResult=commodityClient.delete(commodityId);
        if(!baseResult.isSuccess()){
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }

    /**
     * 活动票券数量增加
     * @param ticketsCount 票券增加的数量
     * @param commodityId 商品id
     */
    public void increaseStandardCount(List<Integer> ticketsCount, Integer commodityId) {
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        if(goods.standards.isEmpty()){
            throw ExceptionUtil.createParamException("规格参数异常");
        }
        for(int i=0;i<ticketsCount.size();i++){
            goods.standards.get(i).totalCount+=ticketsCount.get(i);
        }
        commodityClient.modify(goods);
    }

    /**
     * 根据商品号获取商品票种数量
     * @param commodityId 商品id
     * @return
     */
    public Integer getTicketsKinds(Integer commodityId) {
        BaseResult<Goods> baseResult = commodityClient.get(commodityId);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Goods goods = baseResult.getData();
        if(goods.standards.size()==0){
            throw ExceptionUtil.createParamException("规格参数异常");
        }
        return goods.standards.size();
    }
}
