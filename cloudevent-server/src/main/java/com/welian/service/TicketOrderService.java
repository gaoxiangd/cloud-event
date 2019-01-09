package com.welian.service;

import com.welian.beans.chat.GroupChatReq;
import com.welian.client.account.RongChatClient;
import com.welian.commodity.beans.Order;
import com.welian.commodity.beans.OrderDetail;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.EventStatisticServiceImpl;
import com.welian.service.impl.StateCustomModuleService;
import com.welian.service.impl.StateProjectModuleServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/12/16
 */
@Service
public class TicketOrderService {

    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private EventStatisticServiceImpl eventStatisticService;
    @Autowired
    private RongChatClient rongChatClient;

    public void insertByRecord(EventRecord eventRecord) {
        Order order = commodityRemoteService.getOrder(eventRecord.getTradeNo());
        if (eventRecord != null) {
            List<EventTicketOrder> list = new ArrayList<>();
            EventTicketOrder eventTicketOrder;
            for (OrderDetail orderDetail : order.orderDetails) {
                eventTicketOrder = new EventTicketOrder();
                eventTicketOrder.setEventRecordId(eventRecord.getId());
                eventTicketOrder.setEventId(eventRecord.getEventId());
                eventTicketOrder.setSignState(SqlEnum.EventSignStatus.EVENT_SIGN_FALSE.getValue());
                eventTicketOrder.setTicketState(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue());
                eventTicketOrder.setCreateTime(System.currentTimeMillis());
                eventTicketOrder.setModifyTime(System.currentTimeMillis());
                //票号为商品id+规格id+明细id
                eventTicketOrder.setTicketNo(orderDetail.commodityId.toString() + orderDetail.standardId.toString() +
                        orderDetail.id.toString());
                eventTicketOrder.setTicketId(orderDetail.standardId);
                eventTicketOrder.setCommodityDetailId(orderDetail.id);
                list.add(eventTicketOrder);
            }
            eventTicketOrderMapper.batchInsert(list);

            Event event = eventService.getEvent(eventRecord.getEventId());
            if (event == null) {
                throw ExceptionUtil.createParamException("活动不存在");
            }
            Byte groupChatState;
            Byte verifyType;
            String groupChat;
            if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                EventStateCustom eventStateCustom = stateCustomModuleService.get(eventRecord.getEventId());
                groupChatState = eventStateCustom.getGroupChatState();
                verifyType = eventStateCustom.getVerifyType();
                groupChat = eventStateCustom.getGroupChat();
                //报名票数统计
                stateCustomModuleService.updateRecordCount(eventStateCustom, list.size());
            } else {
                EventStateProject eventStateProject = stateProjectModuleService.get(eventRecord.getEventId());
                groupChatState = eventStateProject.getGroupChatState();
                verifyType = eventStateProject.getVerifyType();
                groupChat = eventStateProject.getGroupChat();
                stateProjectModuleService.updateRecordCount(eventStateProject, 1);
            }
            //统计报名记录表里
            eventStatisticService.countChangeAfterProjectsJoin(eventRecord, 1, list.size());
            //如果是导入的老的融资活动项目，无需加入群聊
            if (NumberUtil.isValidId(eventRecord.getOldRecordId())||
                    SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord.getIsImport())) {
                return;
            }
            //将报名人员加入群聊（条件，该活动不需要审核）
            if (groupChatState.equals(SqlEnum.GroupChatOpenType.TYPE_OPEN.getByteValue().byteValue()) &&
                    !verifyType.equals(SqlEnum.EventVerifyType.TYPE_VERIFY_YES.getByteValue())) {
                if (!StringUtil.isEmpty(groupChat)) {
                    GroupChatReq groupChatReq = new GroupChatReq();
                    groupChatReq.number = groupChat;
                    groupChatReq.uid = eventRecord.getUid();
                    rongChatClient.join(groupChatReq);
                }
            }
        }
    }

    public Map<String,Long> getSignInForApp(Integer recordId) {
        Map<String,Long> resultMap=new HashMap<>();
        Integer signInState = SqlEnum.SignInType.TYPE_SIGN_IN_NO.getValue();
        Long signInTime=0l;
        EventTicketOrderExample example = new EventTicketOrderExample();
        example.createCriteria().andEventRecordIdEqualTo(recordId);
        List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(example);
        for (EventTicketOrder ticketOrder : eventTicketOrders) {
            if (ticketOrder.getSignState().equals(SqlEnum.SignInType.TYPE_SIGN_IN_YES.getValue())) {
                signInState = SqlEnum.SignInType.TYPE_SIGN_IN_YES.getValue();
                signInTime=ticketOrder.getSignTime();
            }
        }
        resultMap.put("signInState",signInState.longValue());
        resultMap.put("signInTime",signInTime);
        return resultMap;
    }
}
