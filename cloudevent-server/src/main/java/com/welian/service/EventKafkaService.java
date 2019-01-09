package com.welian.service;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.config.MiniPush;
import com.welian.config.SmsMsg;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.commodity.message.CommodityKafkaKey;
import com.welian.enums.wallet.SystemDictionary;
import com.welian.enums.wallet.message.KafkaKey;
import com.welian.enums.wallet.message.PaySuccessMessage;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.SmsManageMapper;
import com.welian.mapper.SmsManageOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.SmsManage;
import com.welian.pojo.SmsManageExample;
import com.welian.pojo.SmsManageOrder;
import com.welian.pojo.SmsManageOrderExample;
import com.welian.service.impl.EventRecordImportServiceImpl;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.SmsRemoteServiceImpl;
import com.welian.service.impl.StateCustomModuleService;
import com.welian.service.impl.StateProjectModuleServiceImpl;
import com.welian.service.record.impl.IEventRecordCustomInfoImpl;
import com.welian.utils.ExceptionUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhaopu on 2017/12/8.
 */
@Service
public class EventKafkaService {

    private static final Logger logger = LoggerFactory.getLogger(EventKafkaService.class);

    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private TicketOrderService ticketOrderService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private IEventRecordCustomInfoImpl iEventRecordCustomInfo;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private SmsManageOrderMapper smsManageOrderMapper;
    @Autowired
    private SmsManageMapper smsManageMapper;
    @Autowired
    private EventRecordImportServiceImpl eventRecordImportService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private ProjectService projectService;


    @Autowired
    private CloudRedisService redisService;

    @Value("${cloudevent.wechat.miniporgram.template.id.signup_remind}")
    private String templateIdForSignup;
    @Value("${cloudevent.wechat.miniporgram.template.content.signup_remind}")
    private String templateContentForSignup;

    //监听的商品topics需要改
    @KafkaListener(topics = {"${app.kafka.topics.commodityDefault}"})
    public void commodityHandle(ConsumerRecord<String, String> data, Acknowledgment ack) {
        String key = data.key();
        String value = data.value();
        logger.info("commodityHandle，offset:{} ,partition:{} ,topic:{} ,key:{} ,value:{}", data.offset(), data.partition(),
                data.topic(), key, value);
        switch (key) {
            case CommodityKafkaKey.COMMODITY_ORDER_STATUS_DELETE:
                try {
                    commodityOrderStatusDelete(value);
                    ack.acknowledge();
                } catch (Exception e) {
                    logger.error("KAFKA value error: " + value);
                }
                break;
            case KafkaKey.WALLET_PAY_SUCCESS:
                walletKeyHandle(value,ack);
                break;
        }
    }


    /**
     * 统一处理key为WALLET_PAY_SUCCESS的业务逻辑
     * @param value kafka的value
     *  @param ack Acknowledgment 参数，成功需要手动提交kafka
     */
    private void walletKeyHandle(String value,Acknowledgment ack) {
            try {
            PaySuccessMessage message = JSONUtil.json2Obj(value, PaySuccessMessage.class);
            if (message.getModule() != null) {
                if (message.getModule() == SystemDictionary.TradeModel.EVENT.getCode()) {
                    insertEventTicketOrder(message);
                } else if (message.getModule() == SystemDictionary.TradeModel.ORGSMS.getCode()) {
                    insertSmsMangeOrder(message);
                }
            } else {
                insertEventTicketOrder(message);
            }
            ack.acknowledge();
        } catch (Exception e) {
            logger.error("KAFKA value error: " + value + ";错误信息: " + e.getMessage());

        }
    }

    //监听的钱包topics需要改
    @KafkaListener(topics = {"${app.kafka.topics.walletDefault}"})
    public void walletHandle(ConsumerRecord<String, String> data, Acknowledgment ack) {
        String key = data.key();
        String value = data.value();
        logger.info("walletHandle，offset:{} ,partition:{} ,topic:{} ,key:{} ,value:{}", data.offset(), data.partition(),
                data.topic(), key, value);
        switch (key) {
            case KafkaKey.WALLET_PAY_SUCCESS:
                walletKeyHandle(value,ack);
                break;
        }
    }


    /**
     * 插入订单记录
     *
     * @param msg
     */
    public void insertEventTicketOrder(PaySuccessMessage msg) {

        logger.info("插入订单记录,订单号为:" + msg.getBusinessTradeNo());
        EventRecordExample recordExample = new EventRecordExample();
        recordExample.createCriteria().andTradeNoEqualTo(msg.getBusinessTradeNo());
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(recordExample);
        if (StringUtil.isEmpty(eventRecords)) {
            logger.error("找不到EventRecord");
            return ;
        }
        EventRecord eventRecord = eventRecords.get(0);
        //防止kafka多次调用同一个记录，先做去重
        EventTicketOrderExample example = new EventTicketOrderExample();
        example.createCriteria().andEventRecordIdEqualTo(eventRecord.getId());
        if (eventTicketOrderMapper.selectByExample(example).size() != 0) {
            logger.error("该订单已经存在记录" + msg.toString());
            return;
        }
        Event event = eventService.getEvent(eventRecord.getEventId());
        //防止kafka时间差中有同名用户报名免费活动
//        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
//            if (SqlEnum.CostType.TYPE_CHARGE_NOT.getByteValue().equals(stateCustomModuleService.get(event.getId())
//                    .getCostType()) && eventRecordImportService.checkUserFirstSignup(eventRecord.getUid(), event)) {
//                //商品减回来
//                logger.info("eventRecordId:{} kafka回调重复", eventRecord.getId());
//                commodityRemoteService.onlyRefuseOrder(eventRecord.getId());
//                return;
//            }
//        }
        //支付回调成功，解锁
        eventRecord.setTicketLock(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        eventRecordMapper.updateByPrimaryKeySelective(eventRecord);
        ticketOrderService.insertByRecord(eventRecord);

        // 判断这次报名是否是这个活动的第一次报名记录
        Boolean first = iEventRecordCustomInfo.checkFirstRecord(event);
        //如果是导入的老的融资活动项目，无需发送短信
        if (NumberUtil.isValidId(eventRecord.getOldRecordId()) ||
                SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord.getIsImport())) {
            logger.info("eventRecordId为：{}，导入的老的融资活动项目，无需发送短信",eventRecord.getId());
            return;
        }
        //发送短信和app
        //获取是否需要审核
        Byte verifyType;
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            verifyType = stateCustomModuleService.get(event.getId()).getVerifyType();
        } else {
            verifyType = stateProjectModuleService.get(event.getId()).getVerifyType();
        }
        String content = "";
        String weChatContent = "";
        EventSms eventSms = new EventSms();
        eventSms.setCreateTime(new Date().getTime());
        eventSms.setModifyTime(new Date().getTime());
        eventSms.setEventId(eventRecord.getEventId());
        eventSms.setFlag(SqlEnum.SmsFlag.TYPE_BATCH.getValue());
        eventSms.setUid(eventRecord.getUid());
        EventRecordUserExample userExample = new EventRecordUserExample();
        userExample.createCriteria().andEventRecordIdEqualTo(eventRecord.getId());
        if (StringUtil.isEmpty(eventRecordUserMapper.selectByExample(userExample))) {
            logger.error("找不到eventRecordUser信息");
            return;
        }
        EventRecordUser eventRecordUser = eventRecordUserMapper.selectByExample(userExample).get(0);
        UserReq user = new UserReq();
        user.name = eventRecordUser.getName();
        user.uid = eventRecordUser.getUid();
        user.mobile = eventRecordUser.getPhone();
        //不需要,
        if (verifyType.equals(SqlEnum.RecordVerifyType.TYPE_VERIFY_NO.getByteValue())) {
            content = user.name + SmsMsg.SIGNUP_PASS[0] + event.getTitle() +
                    SmsMsg.SIGNUP_PASS[1] + eventRecord.getTicketUrl() + SmsMsg.SIGNUP_PASS[2];
            weChatContent = MiniPush.spellWechatContent(templateContentForSignup, event,
                    MiniPush.COMMON_SIGNUP_SUCCESS_NEED_CHECK_NOT);

            eventSms.setSource(SqlEnum.SmsSource.TYPE_NOT_VERIFY.getValue());
            iEventRecordCustomInfo.sendAppMsgForSignUp(user, event, verifyType.intValue());

        } else if (verifyType.equals(SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getByteValue())) {
            //需要
            weChatContent = MiniPush.spellWechatContent(templateContentForSignup, event,
                    MiniPush.COMMON_SIGNUP_SUCCESS_NEED_CHECK);
            content = user.name + SmsMsg.SIGNUP_CHECKING[0] + event.getTitle()
                    + SmsMsg.SIGNUP_CHECKING[1] + eventRecord.getTicketUrl();
            eventSms.setSource(SqlEnum.SmsSource.TYPE_WAIT_VERIFY.getValue());
            iEventRecordCustomInfo.sendAppMsgForSignUp(user, event, verifyType.intValue());
        }

        List<String> phoneList = new ArrayList<>();
        phoneList.add(user.mobile);
        smsRemoteService.sendSMS(phoneList, content);
        //保存短信记录到event_sms表
        eventSms.setContent(content);
        eventSmsMapper.insert(eventSms);
        //如果该记录是活动第一个报名，给publishuid发sms，app，云msg
        if (first) {
            User profile = userService.getUserInfoById(event.getPublishUid());
            if (profile == null) {
                logger.error("用户不存在");
                return;
            }
            iEventRecordCustomInfo.sendSms(event, verifyType, profile);
            iEventRecordCustomInfo.sendAppMsg(event, verifyType, profile);
            iEventRecordCustomInfo.saveSysMsg(event);
        }
        //getRemoteProjct可能会抛异常，后面方法无法执行
        if (SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().equals(event.getTemplateId()) ||
                (projectService.getRemoteProjct(eventRecord.getId()).bpAttachment.url) != null) {
            String page = "pages/home";
            smsRemoteService.sendWechatPush(eventRecord, weChatContent, 4, templateIdForSignup, page);
        }
        logger.info("插入订单记录成功" + msg.toString());

    }


    /**
     * 短信充值kfk
     *
     * @param msg
     */
    @Transactional
    public void insertSmsMangeOrder(PaySuccessMessage msg) {

        SmsManageOrderExample example = new SmsManageOrderExample();
        example.createCriteria().andTradeNoEqualTo(msg.getBusinessTradeNo()).andWalletTradeNoEqualTo(msg.getTradeNo());
        List<SmsManageOrder> smsManageOrders = smsManageOrderMapper.selectByExample(example);
        if (smsManageOrders == null || smsManageOrders.size() == 0) {
            throw ExceptionUtil.createParamException("短信充值订单不存在");
        }

        SmsManageOrder smsManageOrder = smsManageOrders.get(0);

        example.createCriteria().andIdEqualTo(smsManageOrder.getId());
        SmsManageOrder record = new SmsManageOrder();
        record.setState(SqlEnum.SmsManageState.SUCCESS.getValue());
        record.setModifyTime(System.currentTimeMillis());
        record.setId(smsManageOrder.getId());
        int result1 = smsManageOrderMapper.updateByExampleSelective(record, example);
        if (result1 == 0) {
            throw ExceptionUtil.createParamException("短信充值记录成功更新失败");
        }

        SmsManageExample smsManageExample = new SmsManageExample();
        smsManageExample.createCriteria().andOrgIdEqualTo(smsManageOrder.getOrgId());
        List<SmsManage> smsManages = smsManageMapper.selectByExample(smsManageExample);
        if (smsManages != null && smsManages.size() > 0) {
            SmsManage smsManage = new SmsManage();
            smsManage.setId(smsManages.get(0).getId());
            smsManage.setOrgId(smsManages.get(0).getOrgId());
            smsManage.setSmsCount(smsManages.get(0).getSmsCount() + smsManageOrder.getSmsCount());
            smsManage.setModifyTime(System.currentTimeMillis());
            int result = smsManageMapper.updateByExampleSelective(smsManage, smsManageExample);
            if (result == 0) {
                throw ExceptionUtil.createParamException("短信充值失败");
            }
        } else {
            SmsManage smsManage = new SmsManage();
            smsManage.setOrgId(smsManageOrder.getOrgId());
            smsManage.setSmsCount(smsManageOrder.getSmsCount());
            smsManage.setModifyTime(System.currentTimeMillis());
            smsManage.setCreateTime(System.currentTimeMillis());
            int result = smsManageMapper.insertSelective(smsManage);
            if (result == 0) {
                throw ExceptionUtil.createParamException("短信充值失败");
            }


        }
    }

    /**
     * 商品订单 订单超时未支付
     *
     * @param msg
     */
    public void commodityOrderStatusDelete(String msg) {


    }

}
