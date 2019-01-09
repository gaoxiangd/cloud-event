package com.welian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.welian.beans.BpResp;
import com.welian.beans.account.Organization;
import com.welian.beans.account.User;
import com.welian.beans.chat.GroupChatReq;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.Tag;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketInfo;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.coupon.CouponReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.org.CityResp;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.project.ProjectFinancingInfoReq;
import com.welian.beans.cloudevent.project.ProjectListBeanResp;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.EventDetailResp;
import com.welian.beans.cloudevent.record.EventRecordReq;
import com.welian.beans.cloudevent.record.EventRecordResp;
import com.welian.beans.cloudevent.record.EventRecordUserResp;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.record.RecordUploadResp;
import com.welian.beans.cloudevent.record.SignUpReq;
import com.welian.beans.cloudevent.signup.SearchSignupListResp;
import com.welian.beans.cloudevent.signup.SearchSignupReq;
import com.welian.beans.cloudevent.signup.SearchSignupResp;
import com.welian.beans.cloudevent.signup.UpdateSignupReq;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.beans.tag.TagResp;
import com.welian.client.account.RongChatClient;
import com.welian.client.commodity.CommodityOrderClient;
import com.welian.commodity.beans.Coupon;
import com.welian.commodity.beans.Order;
import com.welian.config.AppMsg;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.config.EventTemplate;
import com.welian.config.MiniPush;
import com.welian.config.SmsMsg;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.notify.EnumIMType;
import com.welian.mapper.EventCustomFieldMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordAdRelationMapper;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.mapper.EventRecordJoinUserMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.EventUidFavoriteRelationMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordAdRelationExample;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordJoinUser;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.EventUidFavoriteRelation;
import com.welian.pojo.EventUidFavoriteRelationExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import com.welian.request.RequestHolder;
import com.welian.service.BpRemoteService;
import com.welian.service.CommodityRemoteService;
import com.welian.service.CouponRemoteService;
import com.welian.service.CustomFormModuleService;
import com.welian.service.EventCityRelationModule;
import com.welian.service.ProjectService;
import com.welian.service.TagModuleService;
import com.welian.service.UserService;
import com.welian.service.record.impl.IEventRecordCustomInfoImpl;
import com.welian.service.record.impl.IEventRecordProjectInfoImpl;
import com.welian.service.task.RecRTForkJoinTask;
import com.welian.service.task.RecRecordForkJoinTask;
import com.welian.utils.AliyunOSSUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.FractionUtil;
import com.welian.utils.POIUtils;
import com.welian.utils.PagingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeException;
import org.sean.framework.exception.CodeI18NException;
import org.sean.framework.pool.ExecutorPool;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Created by dayangshu on 17/7/13.
 */
@Service("eventRecordService")
public class EventRecordServiceImpl  {
    private static final Logger logger = Logger.newInstance(EventRecordServiceImpl.class);
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventRecordJoinUserMapper eventRecordJoinUserMapper;
    @Autowired
    private IEventRecordProjectInfoImpl iEventRecordProjectInfo;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private OrgPrivilegeRemoteServiceImpl OrgRemoteService;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private EventStatisticServiceImpl eventStatisticService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EventRecordCollectionServiceImpl eventRecordCollectionService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Autowired
    private TagModuleService tagModuleService;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private BpRemoteService bpRemoteService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private IEventRecordCustomInfoImpl iEventRecordCustomInfo;
    @Autowired
    private EventUidFavoriteRelationMapper eventUidFavoriteRelationMapper;
    @Autowired
    private CustomFormModuleService customFormModuleService;
    @Autowired
    private CommodityOrderClient commodityOrderClient;
    @Autowired
    private RongChatClient rongChatClient;
    @Autowired
    private EventCustomFieldMapper eventCustomFieldMapper;
    @Autowired
    private EventRecordAdRelationMapper eventRecordAdRelationMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    @Autowired
    private CouponRemoteService couponRemoteService;

    private ForkJoinPool forkJoinPool = new ForkJoinPool(500);

    @Value("${cloudevent.wechat.miniporgram.template.id.check_success_remind}")
    private String templateIdForCheckSuccess;
    @Value("${cloudevent.wechat.miniporgram.template.content.check_success_remind}")
    private String templateContentForCheckSuccess;
    @Value("${cloudevent.wechat.miniporgram.template.id.check_fail_remind}")
    private String templateIdForCheckFail;
    @Value("${cloudevent.wechat.miniporgram.template.content.check_fail_remind}")
    private String templateContentForCheckFail;


    public void batchUpdate(UpdateSignupReq req) {

        Map<Integer, EventRecord> recordMap = getEventRecordMap(req.signUpList);
        //插入日志用list
        List<EventSms> smsList = new ArrayList<>();
        if (!StringUtil.isEmpty(recordMap)) {
            List<Integer> records = new ArrayList<>();
            List<Integer> recordList = new ArrayList<>();
            for (Integer signUpId : req.signUpList) {
                EventRecord eventRecord = recordMap.get(signUpId);
                if (eventRecord != null) {
                    records.add(eventRecord.getId());
                    if (eventRecord.getState() ==
                            SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().intValue()
                            || eventRecord.getState() ==
                            SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getValue().intValue()) {
                        recordList.add(eventRecord.getId());
                    }
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0, count = records.size(); i < count; i++) {
                stringBuilder.append(records.get(i));
                if (i != count - 1) {
                    stringBuilder.append(",");
                }
            }

            if (stringBuilder.length() > 0) {
                Integer recordId1 = req.signUpList.get(0);
                EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(recordId1);
                EventRecordUserExample eventRecordUserExample = new EventRecordUserExample();
                eventRecordUserExample.createCriteria().andEventRecordIdEqualTo(recordId1);
                List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.
                        selectByExample(eventRecordUserExample);
                if (StringUtil.isEmpty(eventRecordUsers)) {
                    throw ExceptionUtil.createParamException("没有eventRecordUser数据");
                }
                List<String> integers = new ArrayList<>();
                EventRecordUser eventRecordUser = eventRecordUsers.get(0);
                Event event = eventMapper.selectByPrimaryKey(eventRecord.getEventId());
                if (StringUtil.isMobile(eventRecordUser.getPhone())) {
                    integers.add(eventRecordUser.getPhone());
                }
                EventSms eventSms = new EventSms();
                eventSms.setCreateTime(new Date().getTime());
                eventSms.setModifyTime(new Date().getTime());
                eventSms.setEventId(eventRecord.getEventId());
                eventSms.setFlag(SqlEnum.SmsFlag.TYPE_BATCH.getValue());
                eventSms.setUid(eventRecordUser.getUid());
                String content;
                ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(event.getId());
                String weChatContent;
                String page;
                if (req.state.equals(SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue())) {
                    //审核失败，目前只支持一个报名审核失败
                    if (req.signUpList.size() != 1) {
                        throw ExceptionUtil.createParamException("signUpList数量异常");
                    }

                    EventTicketOrderExample example = new EventTicketOrderExample();
                    example.createCriteria().andEventRecordIdEqualTo(recordId1).andTicketStateEqualTo(
                            SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue()
                    );
                    if (StringUtil.isEmpty(eventTicketOrderMapper.selectByExample(example))) {
                        throw ExceptionUtil.createParamException("找不到eventTicketOrder");
                    }
                    if (eventTicketOrderMapper.selectByExample(example).get(0).getSignState().equals(
                            SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue()
                    )) {
                        throw ExceptionUtil.createParamException("已签到，无法拒绝");
                    }

                    EventRecordAdRelationExample adRelationExample = new EventRecordAdRelationExample();
                    adRelationExample.createCriteria().andEventRecordIdEqualTo(eventRecord.getId());
                    eventRecordAdRelationMapper.deleteByExample(adRelationExample);
                    //更新统计数量
                    EventTicketOrderExample ex = new EventTicketOrderExample();
                    ex.createCriteria().andEventRecordIdEqualTo(recordId1);
                    Integer ticketCount = (int) eventTicketOrderMapper.countByExample(ex);
                    //统计报名记录表里减
                    eventStatisticService.countChangeAfterProjectsJoin(eventRecord, -1,
                            -ticketCount);
                    //短信，app
                    content = SmsMsg.SIGNUP_CHECK_FAILED[0] + event.getTitle() + SmsMsg.SIGNUP_CHECK_FAILED[1] +
                            (req.reason.startsWith("其他原因:") ? req.reason.replaceFirst("其他原因: ", "") : req.reason) +
                            SmsMsg.SIGNUP_CHECK_FAILED[2] + extensionLink.getLinkUrlCommon();
                    weChatContent = MiniPush.spellWechatContent(templateContentForCheckFail, event, MiniPush
                            .RECORD_CHECK_REFUSE);
                    page = "pages/home?scene=" + URLEncoder.encode("event?id=" + event.getId());
                    CountDownLatch countDownLatch = new CountDownLatch(4);
                    ExecutorPool.getInstance().execute(() -> {
                        smsRemoteService.sendWechatPush(eventRecord, weChatContent, 6, templateIdForCheckFail, page);
                        countDownLatch.countDown();
                    });
                    ExecutorPool.getInstance().execute(() -> {
                        smsRemoteService.sendSMS(integers, content);
                        countDownLatch.countDown();
                    });
                    ExecutorPool.getInstance().execute(() -> {
                        sendAppMsg(eventRecordUser, req.state, event);
                        countDownLatch.countDown();
                    });
                    //调用订单接口把库存加回来，state表报名数量减去一
                    ExecutorPool.getInstance().execute(() -> {
                        commodityRemoteService.refuseOrder(req.signUpList.get(0));
                        countDownLatch.countDown();
                    });
                    eventSms.setContent(content);
                    eventSms.setSource(SqlEnum.SmsSource.TYPE_VERIFY_REFUSE.getValue());

                } else if (req.state.equals(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue())) {
                    //审核成功，目前只支持一个报名审核成功
                    if (req.signUpList.size() != 1) {
                        throw ExceptionUtil.createParamException("signUpList数量异常");
                    }
                    //审核通过短信，app
                    //审核通过
                    page = "pages/home";
                    content = eventRecordUser.getName() + SmsMsg.SIGNUP_CHECK_PASS[0] + event.getTitle() +
                            SmsMsg.SIGNUP_CHECK_PASS[1] + eventRecord.getTicketUrl() + SmsMsg.SIGNUP_CHECK_PASS[2];
                    weChatContent = MiniPush.spellWechatContent(templateContentForCheckSuccess, event, MiniPush
                            .RECORD_CHECK_PASS);
                    CountDownLatch countDownLatch = new CountDownLatch(4);
                    ExecutorPool.getInstance().execute(() -> {
                        smsRemoteService.sendWechatPush(eventRecord, weChatContent, 5, templateIdForCheckSuccess, page);
                        countDownLatch.countDown();
                    });
                    ExecutorPool.getInstance().execute(() -> {
                        smsRemoteService.sendSMS(integers, content);
                        countDownLatch.countDown();
                    });
                    ExecutorPool.getInstance().execute(() -> {
                        sendAppMsg(eventRecordUser, req.state, event);
                        countDownLatch.countDown();
                    });
                    eventSms.setContent(content);
                    eventSms.setSource(SqlEnum.SmsSource.TYPE_VERIFY_PASS.getValue());

                    EventRecord record = eventRecordMapper.selectByPrimaryKey(req.signUpList.get(0));
                    String groupChat;
                    if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                        groupChat = stateCustomModuleService.get(event.getId()).getGroupChat();
                    } else {
                        groupChat = stateProjectModuleService.get(event.getId()).getGroupChat();
                    }
                    GroupChatReq groupChatReq = new GroupChatReq();
                    groupChatReq.number = groupChat;
                    groupChatReq.uid = record.getUid();
                    ExecutorPool.getInstance().execute(() -> {
                        rongChatClient.join(groupChatReq);
                        countDownLatch.countDown();
                    });
                }
                smsList.add(eventSms);
                eventRecordMapper.batchUpdate(req.state, req.reason, stringBuilder.toString());
            }

            req.signUpList = recordList;
        }
        if (!StringUtil.isEmpty(smsList)) {
            eventSmsMapper.batchInsert(smsList);
        }

    }

    private void sendAppMsg(EventRecordUser eventRecordUser, Byte state, Event event) {
        Integer fromUid = EnumIMType.ACTIVE.getValue();
        Integer toUid = eventRecordUser.getUid();
        String content = "";
        if (state == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue()) {
            content = eventRecordUser.getName() + AppMsg.EVENT_SIGNUP_PASS[0] + event.getTitle() + AppMsg
                    .EVENT_SIGNUP_PASS[1];
        } else if (state == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getValue().byteValue()) {
            content = AppMsg.EVENT_SIGNUP_CANCEL[0] + event.getTitle() + AppMsg.EVENT_SIGNUP_CANCEL[1];
        } else if (state == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getValue().byteValue()) {
            content = eventRecordUser.getName() + AppMsg.EVENT_SIGNUP_REFUSE[0] + event.getTitle() + AppMsg
                    .EVENT_SIGNUP_REFUSE[1];
        }
        smsRemoteService.sendAppMsg(content, fromUid, toUid);
    }



    public Object getDetailByKey(Base64KeyReq req) {
        ExtensionLink extensionLink;
        if (req.source != null && SqlEnum.RequestSource.SOURCE_MINIPROGRAM.getValue().equals(req.source) &&
                NumberUtil.isValidId(req.eventId)) {
            extensionLink = extensionLinkService.getDefaultLinkByEventId(req.eventId);
        } else {
            if (StringUtil.isEmpty(req.uniqueKey)) {
                throw ExceptionUtil.createParamException("参数错误");
            }
            extensionLink = iEventRecordProjectInfo.getExtensionLink(req.uniqueKey);
        }
        Event event = eventService.getEvent(extensionLink.getEventId());
        EventDetailResp eventDetailResp = (EventDetailResp) iEventRecordProjectInfo.getEventProjectInfoForDetail
                (extensionLink);
        //拿到extensionLinkId
        ExtensionLinkResp extensionLink2 = new ExtensionLinkResp();
        extensionLink2.extensionLinkId = extensionLink.getId();
        eventDetailResp.extensionLink = extensionLink2;
        EventStateProject eventStateProject = null;
        EventStateCustom eventStateCustom;
        if ((event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue()) ||
                event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue()))) {
            eventStateProject = stateProjectModuleService.get(event
                    .getId());
            //项目的信息
            eventDetailResp.projectModule = stateProjectModuleService.conversePara(eventStateProject);
            //活动报名限制数量
            eventDetailResp.projectModule.projectFreeCount = commodityRemoteService.getFreeTicketCount(event
                    .getCommodityId());
        }
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            eventStateCustom = stateCustomModuleService.get(event.getId());
            eventDetailResp.customModule = stateCustomModuleService.conversePara(eventStateCustom);
            eventDetailResp.customModule.ticketList = commodityRemoteService.converseToTicket(event.getCommodityId());
            //前端需要，免费活动:buyCount为1，收费活动:buyCount为0
            Integer buyCount;
            if (eventDetailResp.customModule.isCharge.equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue())) {
                eventDetailResp.customModule.freeCount = commodityRemoteService.getFreeTicketCount(event
                        .getCommodityId());
                buyCount = 1;
            } else {
                buyCount = 0;
            }
            for (Ticket ticket : eventDetailResp.customModule.ticketList) {
                ticket.buyCount = buyCount;
            }

        }
        //只有在一键融资活动的时候才会传此状态给客户端
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().byteValue()) {
            eventDetailResp.projectModule.isOpenFinancingService = eventStateProject.getIsOpenFinancing();
        }
        if (OrgRemoteService.getOrgById(event.getOrgId()) != null) {
            eventDetailResp.org = getSimpleOrg(OrgRemoteService.getOrgById(event.getOrgId()));
        }
        TagResp tagResp = (TagResp) tagModuleService.get(event.getId());
        List<Tag> tagList = tagModuleService.converseParaList(tagResp.list);
        eventDetailResp.tags = tagList;
        //更新详情页面浏览数量
        Event eventCount = new Event();
        eventCount.setId(eventDetailResp.eventId);
        eventCount.setDetailBrowseCount(event.getDetailBrowseCount() + 1);
        eventMapper.updateByPrimaryKeySelective(eventCount);
        eventDetailResp.startTime = event.getStartTime();
        eventDetailResp.endTime = event.getEndTime();
        //获取收藏状态
        eventDetailResp.isFavorite = getEventFavorite(req.uid, event);
        //3.6.0增加根据uid获取该用户的原来票券信息
        eventDetailResp = getUserTickets(req.uid, eventDetailResp);
        //返回跳转地址
        eventDetailResp.resultUrl = cloudEventConfig.getLink_jump_result_prefix() + req.uniqueKey;
        //票券详情地址，前端拼接recordId跳转
        eventDetailResp.ticketUrl = cloudEventConfig.getLink_ticket_prefix();

        //自定义字段
        List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(extensionLink.getEventId());
        List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList(eventCustomFieldList);
        eventDetailResp.additionalForm = addtionalFormList;

        eventDetailResp.city = new CityResp();
        eventDetailResp.city.id = event.getCityId();
        eventDetailResp.city.name = event.getCityName();

        return eventDetailResp;
    }

    /**
     * 3.6.0增加根据uid获取该用户的原来票券信息
     *
     * @param uid
     * @param eventDetailResp
     * @return
     */
    public EventDetailResp getUserTickets(Integer uid, EventDetailResp eventDetailResp) {

        eventDetailResp.ticketUrls = new ArrayList<>();

        if (uid == null) {
            return eventDetailResp;
        }
        EventRecordExample example = new EventRecordExample();
        List<Byte> validStates = iEventRecordCustomInfo.getStatesByLevel(ParamEnum.ProjectRecordListGetType
                .TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        example.createCriteria().andUidEqualTo(uid).andEventIdEqualTo(eventDetailResp.eventId).andStateIn(validStates);
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(example);
        for (EventRecord eventRecord : eventRecords) {
            //取通过，待审核，已拒绝的
            if (eventRecord.getTicketLock().equals(SqlEnum.TicketLockType.TYPE_NORMAL.getValue())) {
                eventDetailResp.ticketUrls.add(eventRecord.getTicketUrl());
            }
            //取待付款的。未付款的ticket_lock一定是1,如果订单时间没过，就是待支付
            else if (eventRecord.getTradeEndTime().longValue() >= System.currentTimeMillis()) {
                eventDetailResp.ticketUrls.add(eventRecord.getTicketUrl());
            }

        }
        return eventDetailResp;

    }

    private Integer getEventFavorite(Integer uid, Event event) {
        Integer isFavorite;
        if (uid == null) {
            //如果前端没有传uid，该字段显示异常，返回-1
            isFavorite = ParamEnum.EventFavorite.TYPE_FAVORITE_NOT.getValue();
            return isFavorite;
        }
        EventUidFavoriteRelationExample example = new EventUidFavoriteRelationExample();
        example.createCriteria().andEventIdEqualTo(event.getId()).andUidEqualTo(uid);
        List<EventUidFavoriteRelation> result = eventUidFavoriteRelationMapper.selectByExample(example);
        if (StringUtil.isEmpty(result)) {
            isFavorite = ParamEnum.EventFavorite.TYPE_FAVORITE_NOT.getValue();
        } else {
            isFavorite = ParamEnum.EventFavorite.TYPE_FAVORITE.getValue();
        }
        return isFavorite;
    }

    /**
     * 拿到简单版org信息
     *
     * @param organization
     * @return
     */
    private OrgResp getSimpleOrg(Organization organization) {
        OrgResp org = new OrgResp();
        if (organization == null) {
            return null;
        }
        org.verifyStatus = SqlEnum.OrgVerifyStatus.CHECK_NOT.getValue();
        if (organization.authInfo != null) {
            org.verifyStatus = organization.authInfo.verifyStatus;
        }
        if (!SqlEnum.OrgVerifyStatus.CHECK_PASS.getValue().equals(org.verifyStatus)) {
            return null;
        }
        org.id = organization.id;
        org.name = organization.name;
        org.intro = organization.intro;
        org.logo = organization.logo.contains(cloudEventConfig.getImage_prefix() + cloudEventConfig.getImage_prefix()
        ) ? organization
                .logoNoImagePrefix : organization.logo;
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventService.getEventIdsByOrgId(organization.id));
        List<Integer> reommendIds = new ArrayList<>();
        for (EventCityRelation relation : recommendEvents) {
            reommendIds.add(relation.getEventId());
        }
        OrgReq req = new OrgReq();
        req.orgId = organization.id;
        req.withPage = false;
        List<Event> events = eventService.getValidEventList(req, reommendIds);
        org.eventCount = events.size();
        return org;
    }



    public Object getFormByKey(Base64KeyReq req) {

        return iEventRecordProjectInfo.getEventProjectInfoForForm(req);

    }


    public Object getSignUpProjectList(EventServiceReq req) {
        return iEventRecordProjectInfo.getEventProjectList(req);
    }



    public Object batchOrderFromChannel(ExtensionLinkReq req) {
        return iEventRecordProjectInfo.batchOrderFromChannel(req);
    }


    public Object updateSignUpBP(ExtensionLinkReq req) {
        return iEventRecordProjectInfo.updateSignUpBP(req);
    }



    public Object updateSignUpBPByRecordId(ExtensionLinkReq req) {
        return iEventRecordProjectInfo.updateSignUpBPByRecordId(req);
    }

    /**
     * 根据条件获取有效的报名记录信息
     *
     * @param query
     * @return
     */
    public List<EventRecord> getValidRecords(EventQuery query) {
        EventRecordExample example = joinExample(query);
        return eventRecordMapper.selectByExample(example);
    }

    /**
     * 根据条件获取有效的报名记录信息总数
     *
     * @param query
     * @return
     */
    public Integer countValidRecords(EventQuery query) {
        EventRecordExample example = joinExample(query);
        return (int) eventRecordMapper.countByExample(example);
    }

    /**
     * 拼接有效example
     *
     * @param query
     * @return
     */
    public EventRecordExample joinExample(EventQuery query) {
        List<Byte> validIds = new ArrayList<>();
        validIds.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        validIds.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        EventRecordExample example = new EventRecordExample();
        if (query.sizeCustom != null) {
            example.setLimit(query.sizeCustom);
            if (query.pageCustom != null) {
                example.setOffset(PagingUtil.getStart(query.pageCustom, query.sizeCustom));
            }
        }
        EventRecordExample.Criteria criteria = example.createCriteria();
        criteria.andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).andStateIn(validIds);
        if (query.orgId != null) {
            criteria.andOrgIdEqualTo(query.orgId);
        }
        if (query.eventId != null) {
            criteria.andEventIdEqualTo(query.eventId);
        }
        if (!StringUtil.isEmpty(query.eventIds)) {
            criteria.andEventIdIn(query.eventIds);
        }
        if (query.uid != null) {
            criteria.andUidEqualTo(query.uid);
        }
        if (query.startTime != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(query.startTime);
        }
        if (query.endTime != null) {
            criteria.andCreateTimeLessThanOrEqualTo(query.endTime);
        }
        //默认的导入数据都不给看，需要的可以看
        if (query.isImport == null) {
            criteria.andIsImportEqualTo(SqlEnum.IsImport.TYPE_IS_NOT_IMPORT.getValue());

        } else if (SqlEnum.IsImport.TYPE_ALL.getValue().equals(query.isImport)) {
            List<Integer> importList = new ArrayList<>();
            importList.add(SqlEnum.IsImport.TYPE_IS_NOT_IMPORT.getValue());
            importList.add(SqlEnum.IsImport.TYPE_IS_IMPORT.getValue());
            criteria.andIsImportIn(importList);
        } else {
            criteria.andIsImportEqualTo(query.isImport);
        }
        return example;
    }

    /**
     * 根据条件获取有效的报名记录id
     *
     * @param query
     * @return
     */
    public List<Integer> getValidRecordIds(EventQuery query) {
        List<EventRecord> eventRecords = getValidRecords(query);
        List<Integer> recordIds = new ArrayList<>();
        if (!StringUtil.isEmpty(eventRecords)) {
            eventRecords.forEach(eventRecord -> {
                recordIds.add(eventRecord.getId());
            });
        }
        return recordIds;
    }


    public SearchSignupListResp searchRecordList(SearchSignupReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (req.type != null && req.type == Constant.SEARCH_RECORD_BY_MOBILE && req.keyword != null
                && req.keyword.length() > 0 && !StringUtil.isMobile(req.keyword)) {
            throw ExceptionUtil.createParamException("手机号格式输入错误");
        }
        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        SearchSignupListResp searchSignupListResp = new SearchSignupListResp();
        //返回活动的标题，用来顶部展示
        EventPara eventPara = new EventPara();
        eventPara.eventId = event.getId();
        eventPara.title = event.getTitle();
        eventPara.eventType = event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) ?
                ParamEnum.EventType.TYPE_COMMON.getByteValue() : ParamEnum.EventType.TYPE_ROAD.getByteValue();
        //只有在第一页获取时传给前端
        if (req.page == 1) {
            searchSignupListResp.event = eventPara;
        }
        //搜索条件
        String name = null;
        String phone = null;
        if (req.type != null && req.type == Constant.SEARCH_RECORD_BY_NAME && !StringUtil.isEmpty(req.keyword)) {
            name = req.keyword;
        } else if (req.type != null && req.type == Constant.SEARCH_RECORD_BY_MOBILE && !StringUtil.isEmpty(req
                .keyword)) {
            phone = req.keyword;
        }
        //运营后台搜索条件
        Integer state = null;
        if (!StringUtil.isEmpty(req.name)) {
            name = req.name;
        }
        if (!StringUtil.isEmpty(req.mobile)) {
            phone = req.mobile;
        }
        if (req.state != null && req.state >= 0) {
            state = req.state;
        }
        //搜索列表
        List<EventRecordJoinUser> eventRecordList = eventRecordJoinUserMapper.searchForPage
                (req.eventId, PagingUtil.getStart(req.page, req.size), req.size, name, phone, state);
        //列表转换
        Long startDate = System.currentTimeMillis();
        List<SearchSignupResp> searchSignupResqList = this.converseDataList(eventRecordList, event);
        logger.info("converseDataList执行了：" + (System.currentTimeMillis() - startDate) + "ms");

        //返回数据
        List<EventRecordJoinUser> resultList;
        searchSignupListResp.list = searchSignupResqList;
        if (req.withCount == null || !req.withCount) {
            return searchSignupListResp;
        }
        searchSignupListResp.total = (resultList = eventRecordJoinUserMapper.searchNoPage(req.eventId, name, phone,
                state)).size();
        //运营后台用-审核通过数量
        List<EventRecordJoinUser> checkPassList = new ArrayList<>();
        List<EventRecordJoinUser> passList = resultList;
        if (!StringUtil.isEmpty(passList)) {
            for (EventRecordJoinUser ssr : passList) {
                if (ssr.getState() == 0) {
                    checkPassList.add(ssr);
                }
            }
        }

        Integer checkPassCount = checkPassList.size();
        searchSignupListResp.checkPassCount = checkPassCount;

        EventRecordExample example = new EventRecordExample();
        List<Byte> validIds = new ArrayList<>();
        validIds.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        validIds.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        EventRecordExample.Criteria criterion = example.createCriteria();
        criterion.andSourceEqualTo(SqlEnum.EventRecordSource.TYPE_SOURCE_APP.getByteValue()).andEventIdEqualTo(req
                .eventId).andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).andStateIn(validIds);
        searchSignupListResp.appCount = (int) eventRecordMapper.countByExample(example);

        example.clear();
        criterion = example.createCriteria();
        criterion.andSourceEqualTo(SqlEnum.EventRecordSource.TYPE_SOURCE_WEIXIN.getByteValue()).andEventIdEqualTo(req
                .eventId).andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).andStateIn(validIds);
        searchSignupListResp.weixinCount = (int) eventRecordMapper.countByExample(example);
        List<EventRecordJoinUser> recordJoinUsers = resultList;
        List<Integer> recordIds = new ArrayList<>();
        for (EventRecordJoinUser recordJoinUser : recordJoinUsers) {
            recordIds.add(recordJoinUser.getId());
        }
        searchSignupListResp.checkinCount = getAllCheckInCount(recordIds);
        EventQuery query = new EventQuery();
        query.eventId = event.getId();
        query.isImport = SqlEnum.IsImport.TYPE_ALL.getValue();
        searchSignupListResp.totalNoSearch = countValidRecords(query);
        return searchSignupListResp;
    }

    private Integer getAllCheckInCount(List<Integer> recordIds) {
        if (StringUtil.isEmpty(recordIds)) {
            return 0;
        }
        EventTicketOrderExample example = new EventTicketOrderExample();
        example.createCriteria().andEventRecordIdIn(recordIds).andSignStateEqualTo(SqlEnum.SignInType
                .TYPE_SIGN_IN_YES.getValue());
        return (int) eventTicketOrderMapper.countByExample(example);
    }


    /**
     * 云活动平台-报名表单-票
     */

    public Object tickets(SearchSignupReq req) {
        SearchSignupResp searchSignupListResp = new SearchSignupResp();
        searchSignupListResp.list = new ArrayList<>();

        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常，请重试");
        }

        List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());
        if (ticketModules != null && ticketModules.size() > 0) {

            EventRecordExample eventRecordExample = new EventRecordExample();
            eventRecordExample.createCriteria().andEventIdEqualTo(req.eventId);
            List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);

            for (Ticket ticket : ticketModules) { //票种
                Ticket ticketR = new Ticket();
                ticketR.title = ticket.title;
//                for (EventRecord eventRecord : eventRecordList) { //报名记录
//                    EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
//                    eventTicketOrderExample.createCriteria().andEventIdEqualTo(event.getId()).
//                            andEventRecordIdEqualTo(eventRecord.getId()).andTicketIdEqualTo(ticket.id);
//                    List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample
//                            (eventTicketOrderExample);
//                    if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
//                        if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue
//                                ()) {
//                            size = size + eventTicketOrders.size();
//                        } else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY
//                                .getByteValue()) {
//                            waitCount = waitCount + eventTicketOrders.size();
//                        }
//                    }
//                }
                Integer waitCount = 0;
                //收费活动没有待审核
                EventRecordExample recordExample = new EventRecordExample();
                recordExample.createCriteria().andEventIdEqualTo(event.getId()).andStateEqualTo(SqlEnum
                        .EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()).andTicketLockEqualTo(SqlEnum
                        .TicketLockType.TYPE_NORMAL.getValue());
                Integer costType = 0;
                if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                    costType = stateCustomModuleService.get(event.getId()).getCostType().intValue();
                } else {
                    costType = stateProjectModuleService.get(event.getId()).getCostType().intValue();
                }
                //只有需要审核的收费活动才会走(目前没这个需求)
                if (costType.equals(SqlEnum.CostType.TYPE_CHARGE.getValue()) && event.getTemplateId().equals(SqlEnum
                        .EventType.TYPE_EVENT_COMMON.getValue())) {

                } else {
                    waitCount = (int) eventRecordMapper.countByExample(recordExample);
                }
                Integer size = ticket.buyCount - waitCount < 0 ? 0 : ticket.buyCount - waitCount;
                //待审核
                ticketR.waitCount = waitCount;
                ticketR.buyCount = size;
                ticketR.price = ticket.price;
                ticketR.totalAmount = ticket.price * ticketR.buyCount;
                ticketR.count = ticket.count;
                searchSignupListResp.list.add(ticketR);
            }
        }

        return searchSignupListResp;
    }

    /**
     * 验证是否有报名记录
     *
     * @param eventId
     * @return
     */
    public boolean checkHasRecord(Integer eventId) {
        long count = iEventRecordProjectInfo.getRecordCountByEventIdAndLevel(eventId,
                ParamEnum.ProjectRecordListGetType.TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否有未支付订单
     *
     * @param eventId
     * @return
     */
    public boolean checkHasToPayOrder(Integer eventId) {
        return commodityRemoteService.checkHasToPayOrder(eventId);
    }



    public Object projectSignUp(ProjectSignUpReq req) {
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("extensionLinkId参数校验失败");
        }
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(req.extensionLinkId);
        if (extensionLink == null || extensionLink.getState() != 0) {
            throw ExceptionUtil.createParamException("找不到推广链接");
        }
        Event event = eventService.getEvent(extensionLink.getEventId());
        if (event == null || !NumberUtil.isValidId(event.getTemplateId()) ||
                event.getState() != SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().intValue()) {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名项目，请联系客服");
        } else if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue().intValue()) {
            if (!NumberUtil.isValidId(req.projectType)) {
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            }
            //如果当前活动是详细版的
            if (SqlEnum.ProjectModuleType.TYPE_DETAIL.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                //如果传入的和当前的不一致,直接进行提示
                if (req.projectType.byteValue() != eventStateProject.getProjectInputType()) {
                    throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
                }
                checkDetailParams(req);
                return iEventRecordProjectInfo.saveProjectAndSignUp(req, event, eventStateProject.getProjectInputType()
                        .intValue());
            } else if (SqlEnum.ProjectModuleType.TYPE_SIMPLE.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                checkSimpleParams(req);
                return iEventRecordProjectInfo.saveProjectAndSignUp(req, event, eventStateProject.getProjectInputType()
                        .intValue());
            } else {
                throw ExceptionUtil.createParamException("项目模板异常");
            }

        } else if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().intValue()) {
            checkDetailParams(req);
            return iEventRecordProjectInfo.saveProjectAndSignUp(req, event, SqlEnum.ProjectModuleType.TYPE_DETAIL
                    .getValue());
        } else {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名项目，请联系客服");
        }
    }

    /**
     * app项目报名
     * uid pid
     */

    public Object projectSignUpFromApp(ProjectSignUpReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        if (!NumberUtil.isValidId(req.pid)) {
            throw ExceptionUtil.createParamException("项目参数错误");
        }
        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(req.eventId);
        if (extensionLink == null || extensionLink.getState() != 0) {
            throw ExceptionUtil.createParamException("找不到推广链接");
        }
        Event event = eventService.getEvent(extensionLink.getEventId());
        if (event == null || !NumberUtil.isValidId(event.getTemplateId()) ||
                event.getState() != SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //拼接报名参数
        req = checkAppParams(req);
        req.extensionLinkId = extensionLink.getId();

        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().intValue()) {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名项目，请联系客服");
        } else if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue().intValue()) {
            if (!NumberUtil.isValidId(req.projectType)) {
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            }
            //如果当前活动是详细版的
            if (SqlEnum.ProjectModuleType.TYPE_DETAIL.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                throw ExceptionUtil.createParamException("当前版本不支持此功能，请升级到最新版本！");
            } else if (SqlEnum.ProjectModuleType.TYPE_SIMPLE.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                checkSimpleParams(req);
                return iEventRecordProjectInfo.signUp(req, event, eventStateProject.getProjectInputType()
                        .intValue());
            } else {
                throw ExceptionUtil.createParamException("项目模板异常");
            }
        } else if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().intValue()) {
            throw ExceptionUtil.createParamException("当前版本不支持此功能，请升级到最新版本！");
        } else {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名项目，请联系客服");
        }
    }


    public Object customSignUp(CustomSignUpReq req) {
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("类型参数错误");
        }
        checkCustomParam(req);

        return iEventRecordCustomInfo.signUp(req);
    }


    public Object customSignUpFromApp(CustomSignUpReq req) {
        //手机收费活动不支持（升级会调用h5接口）
        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (!event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            throw ExceptionUtil.createParamException("活动类型参数异常");
        }
        if (stateCustomModuleService.get(event.getId()).getCostType().equals(SqlEnum.CostType.TYPE_CHARGE
                .getByteValue())) {
            throw ExceptionUtil.createParamException("当前版本不支持此功能，请升级到最新版本！");
        }
        //只有免费活动了,拿到票券id，买一张
        List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
        Ticket ticket = new Ticket();
        ticket.id = tickets.get(0).id;
        ticket.buyCount = 1;
        req.ticketList = new ArrayList<>();
        req.ticketList.add(ticket);
        //赋值默认extensionLinkId
        req.extensionLinkId = extensionLinkService.getDefaultLinkByEventId(req.eventId).getId();

        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("类型参数错误");
        }
        checkCustomParam(req);
        checkAdditonForm(event.getId());
        return iEventRecordCustomInfo.signUp(req);
    }


    public Object existRecord(AppReq req) {
        return eventRecordMapper.existRecord(req.uid);
    }


    public HttpServletResponse templateDownload(Integer eventId, HttpServletResponse response) throws IOException {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //自定义字段
        List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(eventId);
        List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList(eventCustomFieldList);
        Integer costType = eventService.getCostType(event);
        List<Ticket> tickets = new ArrayList<>();
        if (SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType)) {
            tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
        }

        return downLoadExcel(addtionalFormList, costType, tickets, response);
    }

    /**
     * 活动报名数据导入
     *
     * @param file 导入文件
     * @return
     */

    public Object templateUpload(MultipartFile file, Integer eventId) throws Exception {
        Event event = eventService.getEvent(eventId);
        if (event.getEndTime() < redisService.getCurrentTime()) {
            throw ExceptionUtil.createParamException("活动已经结束,无法导入报名数据");
        }
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (!SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().equals(event.getTemplateId())) {
            throw ExceptionUtil.createParamException("只有创业活动支持报名数据导入");
        }
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        if (!fileName.contains(".")) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "文件格式不正确");
        }
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        if (!suffixName.toLowerCase().equalsIgnoreCase(".xls")) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "文件格式不正确");
        }
        RecordUploadResp resp = new RecordUploadResp();

        InputStream in = file.getInputStream();//获取控件内容的输入流
        HSSFWorkbook wb = new HSSFWorkbook(in);
        resp = upLoadRecords(event, wb);


        return resp;
    }


    public Object getCoupons(CouponReq req) {
        if (StringUtil.isNotEmpty(req.uniqueKey)) {
            req.eventId = iEventRecordProjectInfo.getExtensionLink(req.uniqueKey).getEventId();
        }
        if(NumberUtil.isInValidId(req.eventId)){
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
        if(!req.type.equals(ParamEnum.CouponType.TYPE_SIGN_IN_SUCCESS.getValue())&&
                !req.type.equals(ParamEnum.CouponType.TYPE_SIGN_UP_SUCCESS.getValue()) ){
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
        req.uid=0;
        if (StringUtil.isNotEmpty(req.tradeNo)) {
            req.uid=getRecordByTradeNo(req.tradeNo,false).getUid();
        }
        return couponRemoteService.getCoupons(req);

    }


    /**
     * 导入报名数据
     *
     * @param event 活动
     * @param wb    excel文本
     */
    public RecordUploadResp upLoadRecords(Event event, HSSFWorkbook wb) throws ExecutionException,
            InterruptedException {
        RecordUploadResp resp = new RecordUploadResp();
        HSSFSheet sheet0 = wb.getSheetAt(0);
        HSSFRow row0 = sheet0.getRow(1);

        int rowNum = POIUtils.getValidRowNum(sheet0);
        int columeNum = POIUtils.getCelNumByRow(row0);
        if (rowNum > cloudEventConfig.getUpload_records_number()) {
            throw ExceptionUtil.createParamException("导入的报名用户数量不能超过" + cloudEventConfig.getUpload_records_number() +
                    "个");
        }
        //自定义字段
        List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(event.getId());
        List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList(eventCustomFieldList);
        List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());

        List<CustomSignUpReq> wrongList = new ArrayList<>();
        List<CustomSignUpReq> rightList = new ArrayList<>();
        CustomSignUpReq req = new CustomSignUpReq();
        Integer costType = req.costType = stateCustomModuleService.get(event.getId()).getCostType().intValue();
        //先通过列数判断模板是否选择正确
        if ((SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType) && columeNum != 4 + addtionalFormList.size() +
                tickets.size()) || (SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().equals(costType) && columeNum != 4 +
                addtionalFormList.size())) {
            throw ExceptionUtil.createParamException("模板错误");
        }

        Integer totalCount = 0;
        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(event.getId());

        // 1.解析
        logger.info("excel数据解析开始");
        long readStartTime = redisService.getCurrentTime();
        for (int i = 2; i <= rowNum; i++) {
            try {
                if (sheet0.getRow(i) == null) {
                    continue;
                }
                HSSFCell cell;
                if ((cell = sheet0.getRow(i).getCell(1)) != null
                        && POIUtils.getStringCellValue(cell).trim().equals(EventTemplate.TEMPLATE_MOBILE)) {
                    continue;
                }
                if (!POIUtils.checkValidCell(sheet0.getRow(i))) {
                    continue;
                }
                req = new CustomSignUpReq();
                req.costType = costType;
                req.validImportData = true;
                req.isImportRecord = SqlEnum.IsImport.TYPE_IS_IMPORT.getValue();
                req.extensionLinkId = extensionLink.getId();
                req.type = 4;
                List<Ticket> ticketList = new ArrayList<>();
                for (int j = 0; j < tickets.size(); j++) {
                    Ticket ticket = new Ticket();
                    ticket.id = tickets.get(j).id;
                    ticket.title = tickets.get(j).title;
                    if (SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().equals(costType)) {
                        ticket.buyCount = 1;
                    } else {
                        ticket.buyCount = getValidNumValue(sheet0.getRow(i).getCell(4 + j + addtionalFormList.size())
                                , req);
                    }
                    ticketList.add(ticket);
                }
                req.ticketList = ticketList;

                List<AddtionalForm> addtionalForms = new ArrayList<>();
                if (StringUtil.isNotEmpty(addtionalFormList)) {
                    for (int j = 0; j < addtionalFormList.size(); j++) {
                        AddtionalForm addtionalForm = new AddtionalForm();
                        addtionalForm.id = addtionalFormList.get(j).id;
                        addtionalForm.label = addtionalFormList.get(j).label;
                        addtionalForm.required = addtionalFormList.get(j).required;
                        HSSFCell valueCell = sheet0.getRow(i).getCell(4 + j);
                        if (SqlEnum.CustomFieldRequiredType.TYPE_MUST.equals(addtionalForm.required)) {
                            addtionalForm.value = getMustStringValue(valueCell, req);
                        } else {
                            addtionalForm.value = valueCell == null ? "" : POIUtils.getStringCellValue(valueCell);
                        }
                        addtionalForms.add(addtionalForm);
                    }
                }
                req.additionalForm = addtionalForms;
                UserReq user = new UserReq();
                user.name = getMustStringValue(sheet0.getRow(i).getCell(0), req);
                user.mobile = getMustStringValue(sheet0.getRow(i).getCell(1), req);
                user.company = getMustStringValue(sheet0.getRow(i).getCell(2), req);
                user.position = getMustStringValue(sheet0.getRow(i).getCell(3), req);
                req.user = user;
                //如果每张票buycount都是0，不允许报名
                if (!checkTicket(req.ticketList)) {
                    req.validImportData = false;
                }
                if (!req.validImportData) {
                    wrongList.add(req);
                } else {
                    rightList.add(req);
                }
            } catch (Exception e) {
                req.reason = "数据错误";
                wrongList.add(req);
                logger.error("数据解析时异常:{}", JSONObject.toJSONString(e));
            }
            totalCount += 1;
        }
        logger.info("excel数据解析结束，一共{}条数据，其中解析成功{}条，解析失败{}条，一共用时{}毫秒",
                totalCount, rightList.size(), wrongList.size(), redisService.getCurrentTime() - readStartTime);
        resp.totalCount = totalCount;
        resp.failCount = wrongList.size();
        if (rightList.size() == 0) {
            Long uploadStartTime = redisService.getCurrentTime();
            logger.info("生成错误excel并上传阿里云开始");
            String url = "";
            if (!StringUtil.isEmpty(wrongList)) {
                url = uploadWrongExcel(addtionalFormList, costType, tickets, wrongList);
                url = cloudEventConfig.isRelease() ? cloudEventConfig.getRelease_bp_prefix() + url :
                        cloudEventConfig.getBp_prefix() + url;
            }
            logger.info("生成错误excel并上传阿里云结束，一共用时{}毫秒", redisService.getCurrentTime() - uploadStartTime);
            resp.url = url;
            return resp;
        }
        //批量插入数据
        logger.info("插入数据库开始");
        Long insertDbStartTime = redisService.getCurrentTime();
        //在预注册前先拦截手机号，手机号对应uid
        EventQuery query = new EventQuery();
        query.eventId = event.getId();
        query.isImport = SqlEnum.IsImport.TYPE_ALL.getValue();
        List<String> phoneLists = new ArrayList<>();
        List<Integer> hasSignIds = Optional.ofNullable(getValidRecords(query).stream().map(a -> a.getId()).collect
                (Collectors.toList())).orElse(new ArrayList<>());
        if (StringUtil.isNotEmpty(hasSignIds)) {
            EventRecordUserExample userExample = new EventRecordUserExample();
            userExample.createCriteria().andEventRecordIdIn(hasSignIds);
            userExample.setDistinct(true);
            eventRecordUserMapper.selectPhone(userExample).forEach(e -> phoneLists.add(e.getPhone()));
        }
        long time = System.currentTimeMillis();
        RecRecordForkJoinTask task = new RecRecordForkJoinTask(phoneLists, rightList, wrongList, 0, rightList.size()
                , event, userService, eventRecordMapper, eventRecordCollectionMapper, eventTicketOrderMapper,
                redisService, iEventRecordCustomInfo);
        forkJoinPool.execute(task);
        List<Integer> recordIds = task.get();
        //报名数量增加
        logger.info("新增的recordIds字段参数：{}", JSONObject.toJSONString(recordIds));
        if (StringUtil.isNotEmpty(recordIds)) {
            Long signStartTime = redisService.getCurrentTime();
            EventTicketOrderExample orderExample = new EventTicketOrderExample();
            orderExample.createCriteria().andEventRecordIdIn(recordIds);
            Integer count = (int) eventTicketOrderMapper.countByExample(orderExample);
            if (NumberUtil.isValidId(count)) {
                stateCustomModuleService.updateRecordCount(stateCustomModuleService.get(event.getId()), count);
            }
            logger.info("增加活动报名数据，执行时间：{} ms", redisService.getCurrentTime() - signStartTime);
        }
        logger.info("导入总耗时：{}", System.currentTimeMillis() - time);
        logger.info("插入数据库结束，失败（包括解析）报名数据{}条，一共用时{}毫秒", wrongList.size(),
                redisService.getCurrentTime() - insertDbStartTime);
        //3.生成错误excel文件，上传阿里云，返回访问地址
        Long uploadStartTime = redisService.getCurrentTime();
        logger.info("生成错误excel并上传阿里云开始");
        String url = "";
        if (!StringUtil.isEmpty(wrongList)) {
            url = uploadWrongExcel(addtionalFormList, costType, tickets, wrongList);
            url = cloudEventConfig.isRelease() ? cloudEventConfig.getRelease_bp_prefix() + url :
                    cloudEventConfig.getBp_prefix() + url;
        }
        logger.info("生成错误excel并上传阿里云结束，一共用时{}毫秒", redisService.getCurrentTime() - uploadStartTime);
        resp.failCount = wrongList.size();
        resp.url = url;
        return resp;
    }

    private boolean checkTicket(List<Ticket> ticketList) {
        Integer buyCount = 0;
        for (Ticket ticket : ticketList) {
            buyCount += ticket.buyCount;
        }
        if (NumberUtil.isValidId(buyCount)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * -1异常数据返回数量 0
     *
     * @param buyCount
     * @return
     */
    private Integer getValidCount(Integer buyCount) {
        if (buyCount.equals(-1)) {
            return 0;
        }
        return buyCount;
    }

    /**
     * 生成错误excel文件，上传阿里云，返回访问地址
     *
     * @param addtionalFormList 自定义数据
     * @param costType          是否收费
     * @param tickets           票券
     * @param wrongList         错误数据
     * @return
     */
    private String uploadWrongExcel(List<AddtionalForm> addtionalFormList, Integer costType, List<Ticket> tickets
            , List<CustomSignUpReq> wrongList) {

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(EventTemplate.TITLE);
        //设置行高，宽高
        POIUtils.setDefaultHeighAndWidth(sheet);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //红色字体
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);//HSSFColor.VIOLET.index //字体颜色
        HSSFCellStyle style = wb.createCellStyle(); // 样式对象
        style.setFont(font);
        //设置单元格内容
        cell.setCellValue(EventTemplate.TEMPLATE_NOTE);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        if (SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().equals(costType)) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    3 + addtionalFormList.size()));
        } else {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    3 + addtionalFormList.size() + tickets.size()));
        }
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue(EventTemplate.NAME);
        row2.createCell(1).setCellValue(EventTemplate.MOBILE);
        row2.createCell(2).setCellValue(EventTemplate.COMPANY);
        row2.createCell(3).setCellValue(EventTemplate.POSITION);
        for (int i = 0; i < addtionalFormList.size(); i++) {
            row2.createCell(i + 4).setCellValue(addtionalFormList.get(i).label +
                    (addtionalFormList.get(i).required == (byte) 1 ? EventTemplate.REQUIRE_MUST : ""));
        }
        if (SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType)) {
            for (int i = 0; i < tickets.size(); i++) {
                row2.createCell(i + addtionalFormList.size() + 4).setCellValue(tickets.get(i).title +
                        EventTemplate.TICKET_UNIT);
            }
        }
        //错误数据插入
        for (int i = 0; i < wrongList.size(); i++) {
            try {
                HSSFRow row = sheet.createRow(i + 2);
                CustomSignUpReq wrongReq = wrongList.get(i);
                row.createCell(0).setCellValue(wrongReq.user.name);
                row.createCell(1).setCellValue(wrongReq.user.mobile);
                row.createCell(2).setCellValue(wrongReq.user.company);
                row.createCell(3).setCellValue(wrongReq.user.position);

                for (int j = 0; j < addtionalFormList.size(); j++) {
                    row.createCell(4 + j).setCellValue(wrongReq.additionalForm.get(j).value);
                }
                HSSFCell cellRed;
                if (SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType)) {
                    for (int j = 0; j < tickets.size(); j++) {
                        Integer buyCount = wrongReq.ticketList.get(j).buyCount;
                        row.createCell(4 + addtionalFormList.size() + j).setCellValue(
                                buyCount.equals(-1) ? "数量异常" : buyCount.toString());
                    }
                    (cellRed = row.createCell(4 + addtionalFormList.size() + tickets.size())).setCellValue(wrongReq
                            .reason);
                    cellRed.setCellStyle(style);
                } else {
                    (cellRed = row.createCell(4 + addtionalFormList.size())).setCellValue(wrongReq.reason);
                    cellRed.setCellStyle(style);
                }
            } catch (Exception e) {
                logger.error("生成错误模板时出错，错误数量{}，第{}个出错，对象是{}，异常{},所有data数据为:{}",
                        wrongList.size(), i + 1, JSONObject.toJSONString(wrongList.get(i)), e.getMessage(),
                        JSONObject.toJSONString(wrongList));
            }

        }

        POIUtils.createCellTextFormat(wb, sheet, 2 + wrongList.size(), 500, 1);
        //输出Excel文件
        String url = AliyunOSSUtil.createXLSName();
        File dir = new File(Constant.XLS_DIR);
        if (!dir.exists() || dir.isFile()) {
            dir.delete();
            dir.mkdirs();
        }
        try {
            File file = new File(Constant.getXlsPath(url));
            wb.write(file);
            //上传阿里云oss
            String bucketName = cloudEventConfig.isRelease() ? cloudEventConfig.getPdf_bucket_name() : cloudEventConfig
                    .getPdf_bucket_test_name();
            AliyunOSSUtil.uploadFile(cloudEventConfig.getAlioss_pdf_endpoint(), bucketName, Constant.XLS_DIR, url);
        } catch (Exception e) {
            e.printStackTrace();
            logger.printStackTrace(e);
        } finally {
            if (StringUtil.isNotBlank(url)) {
                File upload = new File(Constant.getXlsPath(url));
                if (null != upload && upload.isFile()) {
                    upload.delete();
                }
            }
        }

        return url;
    }

    /**
     * 获取非零正整数类型值，如果为空，返回0；不是非零正整数，返回-1，且设置无效
     *
     * @param cell 列值
     * @param req  需要赋值的入参
     * @return
     */
    private Integer getValidNumValue(HSSFCell cell, CustomSignUpReq req) {
        if (cell == null || StringUtil.isEmpty(POIUtils.getStringCellValue(cell))) {
            return 0;
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if (!cell.getStringCellValue().trim().matches("^\\d+$")) {
            req.validImportData = false;
            req.reason = "数据错误";
            return -1;
        }
        return Integer.parseInt(cell.getStringCellValue().trim());
    }

    /**
     * 获取必填的值，如果为空，返回""，设置这条数据无效；不为空返回正常string
     *
     * @param cell 列值
     * @param req  需要赋值的入参
     * @return
     */
    private String getMustStringValue(HSSFCell cell, CustomSignUpReq req) {
        if (cell == null) {
            req.validImportData = false;
            req.reason = "数据错误";
            return "";
        } else {
            return POIUtils.getStringCellValue(cell);
        }
    }

    /**
     * 第一部，加票券数量
     */
    private void addTicket(Event event, HSSFSheet sheet0) {
        HSSFRow row0 = sheet0.getRow(0);
        int rowNum = sheet0.getLastRowNum();
        int columeNum = row0.getPhysicalNumberOfCells();
        EventStateCustom eventStateCustom = stateCustomModuleService.get(event.getId());
        //免费活动
        if (SqlEnum.CostType.TYPE_CHARGE_NOT.getByteValue().equals(eventStateCustom.getCostType())) {
            //去掉说明和标题两列
            Integer addTicketCount = rowNum - 1;
            commodityRemoteService.increaseStandardCount(Arrays.asList(addTicketCount), event.getCommodityId());
        } else {
            List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(event.getId());
            Integer ticketKinds = commodityRemoteService.getTicketsKinds(event.getCommodityId());
            Map<Integer, Integer> ticketCount = new LinkedHashMap<>();
            //获取每种票的总数
            for (int i = 1; i <= rowNum; i++) {
                for (int j = 1; j <= ticketKinds; j++) {
                    if (!ticketCount.containsKey(j)) {
                        ticketCount.put(j, 0);
                    } else {
                        Integer count = ticketCount.get(j);
                        HSSFCell cell = sheet0.getRow(i).getCell(3 + eventCustomFieldList.size() + j);
                        if (POIUtils.checkNumber(cell)) {
                            count += POIUtils.getNumberValue(cell);
                        } else {
                            count += 0;
                        }
                        ticketCount.put(j, count);
                    }
                }
            }
            List countList = new ArrayList();
            ticketCount.keySet().forEach(a -> countList.add(ticketCount.get(a)));
            commodityRemoteService.increaseStandardCount(countList, event.getCommodityId());
        }
    }

    /**
     * 导出（下载）excel
     *
     * @param addtionalFormList 自定义字段
     * @param costType          是否收费
     * @param tickets           票券信息
     */
    public HttpServletResponse downLoadExcel(List<AddtionalForm> addtionalFormList, Integer costType,
                                             List<Ticket> tickets, HttpServletResponse response) throws
            IOException {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(EventTemplate.TITLE);
        //设置行高，宽高
        POIUtils.setDefaultHeighAndWidth(sheet);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue(EventTemplate.TEMPLATE_NOTE);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        if (SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().equals(costType)) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    3 + addtionalFormList.size()));
        } else {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    3 + addtionalFormList.size() + tickets.size()));
        }
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue(EventTemplate.NAME);
        row2.createCell(1).setCellValue(EventTemplate.MOBILE);
        row2.createCell(2).setCellValue(EventTemplate.COMPANY);
        row2.createCell(3).setCellValue(EventTemplate.POSITION);
        for (int i = 0; i < addtionalFormList.size(); i++) {
            row2.createCell(i + 4).setCellValue(addtionalFormList.get(i).label +
                    (addtionalFormList.get(i).required == (byte) 1 ? EventTemplate.REQUIRE_MUST : ""));
        }
        if (SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType)) {
            for (int i = 0; i < tickets.size(); i++) {
                row2.createCell(i + addtionalFormList.size() + 4).setCellValue(tickets.get(i).title +
                        EventTemplate.TICKET_UNIT);
            }
        }
        //在sheet里创建第三行（报名数据示例）
        HSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue(EventTemplate.TEMPLATE_NAME);
        row3.createCell(1).setCellValue(EventTemplate.TEMPLATE_MOBILE);
        row3.createCell(2).setCellValue(EventTemplate.TEMPLATE_COMPANY);
        row3.createCell(3).setCellValue(EventTemplate.TEMPLATE_POSITION);
        for (int i = 0; i < addtionalFormList.size(); i++) {
            row3.createCell(i + 4).setCellValue(
                    (addtionalFormList.get(i).required == (byte) 1 ? EventTemplate.TEMPLATE_REQUIRED : ""));
        }
        if (SqlEnum.CostType.TYPE_CHARGE.getValue().equals(costType)) {
            row3.createCell(addtionalFormList.size() + 4).setCellValue(EventTemplate.TEMPLATE_TICKET_FIRST);
            for (int i = 1; i < tickets.size(); i++) {
                row3.createCell(i + addtionalFormList.size() + 4).setCellValue(0);
            }
        }
        POIUtils.createCellTextFormat(wb, sheet, 3, 500, 1);
//        //输出Excel文件
//        FileOutputStream fileOut = new FileOutputStream("批量导入用户模板.xls");
//        // 将workbook写到输出流中
//        wb.write(fileOut);
//        fileOut.flush();
//        fileOut.close();


        return packageIOResponse(wb, "template.xls", response);
    }

    /**
     * 微信根据unionId获取用户信息
     *
     * @param unionId 识别微信的唯一id
     */
    public UserResp getUserByUnionId(String unionId) {
        User user;
        if (StringUtil.isBlank(unionId)) {
            return new UserResp();
        }
        if ((user = userService.getAccountbyUnionid(unionId)) != null) {
            return userService.user2UserResp(user);
        } else {
            EventRecordUserExample example = new EventRecordUserExample();
            //unionId不针对eventId做区分
            example.setOrderByClause("id desc");
            example.createCriteria().andUnionIdEqualTo(unionId);
            List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.selectByExample(example);
            if (StringUtil.isEmpty(eventRecordUsers)) {
                return new UserResp();
            }
            return userService.eventRecordUser2UserResp(eventRecordUsers.get(0));
        }

    }

    /**
     * 老的免费创业活动不支持自定义字段活动报名，如果有提醒升级
     *
     * @param eventId
     */
    private void checkAdditonForm(Integer eventId) {
        EventCustomFieldExample example = new EventCustomFieldExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        List<EventCustomField> eventCustomFields = eventCustomFieldMapper.selectByExample(example);
        if (eventCustomFields != null && eventCustomFields.size() != 0) {
            throw ExceptionUtil.createParamException("当前版本不支持此功能，请升级到最新版本！");
        }
    }

    public void checkCustomParam(SignUpReq req) {

        if (req.user == null) {
            throw ExceptionUtil.createParamException("user参数校验失败");
        }
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("extensionLinkId参数校验失败");
        }
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(req.extensionLinkId);
        if (extensionLink == null || extensionLink.getState() != 0) {
            throw ExceptionUtil.createParamException("找不到推广链接");
        }
        Event event = eventService.getEvent(extensionLink.getEventId());
        if (event == null || !NumberUtil.isValidId(event.getTemplateId())) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (event.getTemplateId() != SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().intValue()) {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名，请联系客服");
        }
        if (SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(req.isImportRecord)) {
            return;
        }
        if (SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue().equals(event.getState())) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ, "error_custom_signup_event_deleted");
        }
        if (!SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue().equals(event.getState())) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ, "error_custom_signup_event_not_publish");
        }
    }



    public SearchSignupResp converseData(EventRecordJoinUser eventRecordJoinUser, Map<String, Order> tradeMap, Event
            event, List<Ticket> ticketModules, Map<Integer, ProjectListBeanResp> projectMap) {
        if (eventRecordJoinUser != null) {
            SearchSignupResp searchSignupResp = new SearchSignupResp();
            searchSignupResp.isVerify = eventRecordJoinUser.getState();
            searchSignupResp.signUpId = eventRecordJoinUser.getId();
            searchSignupResp.reason = eventRecordJoinUser.getReason();
            searchSignupResp.signUpTime = eventRecordJoinUser.getCreateTime();
            searchSignupResp.source = eventRecordJoinUser.getSource().intValue();
            searchSignupResp.isImport = eventRecordJoinUser.getIsImport();
            UserResp user = new UserResp();
            user.uid = eventRecordJoinUser.getUid();
            user.name = eventRecordJoinUser.getName();
            user.position = eventRecordJoinUser.getPosition();
            user.company = eventRecordJoinUser.getCompany();
            user.mobile = eventRecordJoinUser.getPhone();
            searchSignupResp.user = user;

            if (eventRecordJoinUser.getSignUpType() == SqlEnum.SignUpType.TYPE_EVENT_PROJECT.getByteValue()) {
                searchSignupResp.project = projectMap.get(eventRecordJoinUser.getId()).project;
            } else {
                if (StringUtil.isEmpty(eventRecordJoinUser.getTradeNo())) {
                    throw ExceptionUtil.createParamException("订单号错误");
                }
                if (tradeMap.containsKey(eventRecordJoinUser.getTradeNo())) {
                    Order order = tradeMap.get(eventRecordJoinUser.getTradeNo());
                    //订单状态 DELETED(99, "已删除"), TOPAY(0, "待支付"), PAID(1, "已支付"), TIMEOUT(2, "超时未付款"),
                    // CANCELED(3, "已取消"), SERVER_CANCELED(4, "后台取消"), COMPLETED(5, "已完成"), CLOSED(6, "已关闭"),
                    // REFUND(9, "已退款"), FAIL(10, "支付失败");
                    if (order.status == 1 || order.status == 5) {
                        //票信息
                        searchSignupResp.ticketTypes = converseTicket(event, eventRecordJoinUser, ticketModules);
                    } else {
                        //票信息

                        searchSignupResp.ticketTypes = eventService.converseTicketOrder(order, ticketModules, null);
                    }
                }
                //A 3.7.8 导入的数据
                if (SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecordJoinUser.getIsImport())) {
                    searchSignupResp.ticketTypes = converseTicket(event, eventRecordJoinUser, ticketModules);
                }
            }

            return searchSignupResp;
        }
        return null;
    }


    public List<SearchSignupResp> converseDataList(List<EventRecordJoinUser> eventRecordList, Event event) {
        List<SearchSignupResp> searchSignupRespList = new ArrayList<>();
        if (!StringUtil.isEmpty(eventRecordList)) {
            List<String> tradeNos = new ArrayList<>();
            List<Integer> recordIds = new ArrayList<>();
            for (EventRecordJoinUser recordJoinUser : eventRecordList) {
                tradeNos.add(recordJoinUser.getTradeNo());
                recordIds.add(recordJoinUser.getId());
            }
            Map<String, Order> tradeMap = commodityRemoteService.getOrdersByRecords(tradeNos);
            Map<Integer, ProjectListBeanResp> projectMap = new HashMap<>();
            if (!event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                projectMap = projectService.getProjectInfoMapByIdsSimple(recordIds);
            }
            List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());

            RecRTForkJoinTask task = new RecRTForkJoinTask(eventRecordList, tradeMap, event, ticketModules,
                    projectMap, this, eventRecordCollectionService, 0, eventRecordList.size());
            forkJoinPool.submit(task);
            try {
                searchSignupRespList = task.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return searchSignupRespList;
    }


    private void checkDetailParams(ProjectSignUpReq req) {
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("参数异常");
        }
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("参数异常");
        }
//        if (req.isOpenFinancingService == null) {
//            throw ExceptionUtil.createParamException("开启融资服务参数校验失败");
//        }
        if (req.user == null) {
            throw ExceptionUtil.createParamException("请填写用户信息");
        }
        if (req.financingInfo == null) {
            throw ExceptionUtil.createParamException("请填写融资信息");
        }
        if (req.financingInfo.stage == null || req.financingInfo.stage < 0 ||
                req.financingInfo.amount == null || req.financingInfo.amount <= 0 ||
                req.financingInfo.amountUnit == null || req.financingInfo.amountUnit <= 0) {
            throw ExceptionUtil.createParamException("请填写融资信息");
        }
        if (req.financingInfo.share == null || req.financingInfo.share <= 0 || req.financingInfo.share > 100) {
            throw ExceptionUtil.createParamException("出让股份应为0到100之间");
        }
        if (req.project == null) {
            throw ExceptionUtil.createParamException("请填写项目信息");
        }
        ProjectBaseReq project = req.project;
        if (StringUtil.isEmpty(project.name)) {
            throw ExceptionUtil.createParamException("请填写项目名称");
        }
        if (StringUtil.isEmpty(project.intro)) {
            throw ExceptionUtil.createParamException("请填写项目一句话介绍");
        }
        if (!NumberUtil.isValidId(project.cityId)) {
            throw ExceptionUtil.createParamException("请填写城市id");
        }
        if (StringUtil.isEmpty(project.cityName)) {
            throw ExceptionUtil.createParamException("请填写城市名称");
        }
        if (!NumberUtil.isValidId(project.industryFirstId)) {
            throw ExceptionUtil.createParamException("请填写一级领域id");
        }
        if (StringUtil.isEmpty(project.industryFirstName)) {
            throw ExceptionUtil.createParamException("请填写一级领域名称");
        }
        if (!NumberUtil.isValidId(project.industrySecondId)) {
            throw ExceptionUtil.createParamException("请填写二级领域id");
        }
        if (StringUtil.isEmpty(project.industrySecondName)) {
            throw ExceptionUtil.createParamException("请填写二级领域名称");
        }
        if (!NumberUtil.isValidId(project.projectStage)) {
            throw ExceptionUtil.createParamException("请选择项目阶段");
        }
        //此版本，bp不是必须的，也能报名成功
    }

    private void checkSimpleParams(ProjectSignUpReq req) {
        if (req.user == null) {
            throw ExceptionUtil.createParamException("参数异常");
        }
        if (req.project == null) {
            throw ExceptionUtil.createParamException("请填写项目信息");
        }
        ProjectBaseReq project = req.project;
        if (StringUtil.isEmpty(project.name)) {
            throw ExceptionUtil.createParamException("请填写项目名称");
        }
        if (StringUtil.isEmpty(project.intro)) {
            throw ExceptionUtil.createParamException("请填写项目一句话介绍");
        }
    }



    public Map<Integer, EventRecord> getEventRecordMap(List<Integer> idList) {
        Map<Integer, EventRecord> map = new HashMap<>();
        if (idList != null && idList.size() > 0) {
            EventRecordExample eventRecordExample = new EventRecordExample();
            eventRecordExample.createCriteria().andIdIn(idList);
            List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);
            for (EventRecord eventRecord : eventRecordList) {
                map.put(eventRecord.getId(), eventRecord);
            }
        }
        return map;
    }


    public ExtensionLinkResp getKeyByOld(EventPara eventPara) {
        Event event = eventService.getEvent(eventPara.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }

        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andEventIdEqualTo(eventPara.eventId).
                andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue());
        List<ExtensionLink> list = extensionLinkMapper.selectByExample(example);
        ExtensionLink link = null;
        if (!StringUtil.isEmpty(list)) {
            link = list.get(0);
        }
        ExtensionLinkResp resp = new ExtensionLinkResp();
        if (link != null) {
            resp.commonUrl = link.getLinkUrlCommon();
        }
        return resp;
    }



    public EventRecordUserResp getEventRecordUser(Integer recordId) {
        EventRecordUserExample eventRecordUserExample = new EventRecordUserExample();
        eventRecordUserExample.createCriteria().andEventRecordIdEqualTo(recordId);
        List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.
                selectByExample(eventRecordUserExample);
        if (!StringUtil.isEmpty(eventRecordUsers)) {
            throw ExceptionUtil.createParamException("没有活动报名记录");
        }
        EventRecordUser eventRecordUser = eventRecordUsers.get(0);
        EventRecordUserResp recordUserResp = new EventRecordUserResp();
        if (eventRecordUser == null) {
            return recordUserResp;
        }
        recordUserResp.company = eventRecordUser.getCompany();
        recordUserResp.name = eventRecordUser.getName();
        recordUserResp.phone = eventRecordUser.getPhone();
        recordUserResp.position = eventRecordUser.getPosition();
        recordUserResp.uid = eventRecordUser.getUid();
        return recordUserResp;
    }


    public Object getEventRecord(AppReq req) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(req.recordId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("没有活动报名记录");
        }
        return eventRecord;
    }


    private ProjectSignUpReq checkAppParams(ProjectSignUpReq req) {
        req.user = new UserReq();
        req.project = new ProjectBaseReq();
        req.financingInfo = new ProjectFinancingInfoReq();
        req.bp = new BPResp();

        BpResp bpResp = bpRemoteService.getBpDetailById(req.pid);
        //用户信息
        User user = userService.getUserInfoById(bpResp.uid);
        req.user.uid = user.id;
        req.user.company = user.company;
        req.user.position = user.position;
        req.user.name = user.name;
        req.user.mobile = user.phone;
        //项目信息
        req.project.pid = bpResp.id;
        req.project.name = bpResp.name;
        req.project.intro = bpResp.intro;
        req.project.logo = bpResp.logoNoImagePrefix;//特别注意，不能有前缀
        req.project.projectStage = bpResp.projectStage;
        req.project.companyName = bpResp.company;
        req.project.website = bpResp.remark;
        req.project.remark = bpResp.website;
        if (bpResp.firstIndustry != null) {
            req.project.industryFirstId = bpResp.firstIndustry.id;
            req.project.industryFirstName = bpResp.firstIndustry.name;
        }
        if (bpResp.secondIndustry != null) {
            req.project.industrySecondId = bpResp.secondIndustry.id;
            req.project.industrySecondName = bpResp.secondIndustry.name;
        }
        if (bpResp.city != null) {
            req.project.cityId = bpResp.city.id;
            req.project.cityName = bpResp.city.name;
        }
        if (bpResp.financingInfo != null) {
            req.financingInfo.amount = bpResp.financingInfo.amount;
            req.financingInfo.share = bpResp.financingInfo.share;
            if (bpResp.financingInfo.stageInfo != null) {
                req.financingInfo.stage = bpResp.financingInfo.stageInfo.id;
            }
            if (bpResp.financingInfo.amountUnit != null) {
                req.financingInfo.amountUnit = (int) (byte) bpResp.financingInfo.amountUnit;
            }
        }
        if (bpResp.bpAttachment != null) {
            req.bp.bpId = bpResp.bpAttachment.id;
            req.bp.bpName = bpResp.bpAttachment.name;
            req.bp.bpSize = bpResp.bpAttachment.size;
            req.bp.bpUrl = bpResp.bpAttachment.urlNoFilePrefix;//特别注意，不能有前缀
        }
        req.projectType = SqlEnum.ProjectModuleType.TYPE_SIMPLE.getValue();
        return req;
    }


    private List<Ticket> converseTicket(Event event, EventRecordJoinUser eventRecordJoinUser, List<Ticket>
            ticketModules) {
        List<Ticket> ticketTypes = new ArrayList<>();

        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                Ticket ticketR = new Ticket();
                ticketR.ticketList = new ArrayList<>();
                ticketR.title = ticket.title;
                ticketR.price = ticket.price;

                EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                eventTicketOrderExample.createCriteria().andEventRecordIdEqualTo(eventRecordJoinUser.getId())
                        .andTicketIdEqualTo(ticket.id);
                Long startTime = System.currentTimeMillis();
                List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample
                        (eventTicketOrderExample);
                logger.info("recordId为{}的报名数据select时间{}毫秒", eventRecordJoinUser.getId(), System
                        .currentTimeMillis() - startTime);


                if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                    for (EventTicketOrder eventTicketOrder : eventTicketOrders) { //订单票
                        TicketInfo ticketInfo = new TicketInfo();
                        ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                        ticketInfo.state = eventTicketOrder.getSignState();
                        ticketInfo.tradeNo = eventRecordJoinUser.getTradeNo();
                        ticketInfo.signTime = eventTicketOrder.getSignTime();
                        ticketR.ticketList.add(ticketInfo);
                    }
                }
                ticketTypes.add(ticketR);
            }
        }

        return ticketTypes;
    }

    /**
     * 通过tradeNo找到报名记录
     */

    public EventRecord getRecordByTradeNo(String ticketNo,Boolean throwException) {
        EventRecordExample example = new EventRecordExample();
        example.createCriteria().andTradeNoEqualTo(ticketNo);
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(example);
        if (StringUtil.isEmpty(eventRecords)||eventRecords.size() != 1 ) {
            if(throwException) {
                throw ExceptionUtil.createParamException("该tradeNo找不到报名记录");
            }else{
                return new EventRecord();
            }
        }
        return eventRecords.get(0);
    }

    /**
     * 活动报名记录下载
     *
     * @param req      报名数据入参
     * @param response 输出流
     * @return
     */
    public HttpServletResponse recordsDownload(SearchSignupReq req, HttpServletResponse response) throws IOException {
        req.page = 1;
        req.size = Integer.MAX_VALUE;
        SearchSignupListResp resp = searchRecordList(req);
        List<EventCustomField> eventCustomFields = (List<EventCustomField>) customFormModuleService.get(resp.event
                .eventId);
        if (resp.event.eventType.equals(ParamEnum.EventType.TYPE_COMMON.getByteValue())) {
            return downLoadCommonRecords(resp, eventCustomFields, response);
        } else {
            return downLoadRoadRecords(resp, eventCustomFields, response);
        }

    }

    /**
     * 路演活动excel数据导出
     *
     * @param resp              搜索出来的结果列表
     * @param eventCustomFields 自定义字段
     * @param response          返回结果流
     * @throws IOException
     */
    private HttpServletResponse downLoadRoadRecords(SearchSignupListResp resp, List<EventCustomField> eventCustomFields,
                                                    HttpServletResponse response) throws IOException {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(resp.event.title);
        //设置行高，宽高
        POIUtils.setDefaultHeighAndWidth(sheet);
        //设置绿色字体字样
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.GREEN.index);//HSSFColor.VIOLET.index //字体颜色
        HSSFCellStyle style = wb.createCellStyle(); // 样式对象
        style.setFont(font);
        //在sheet里创建第一行(标题行)
        HSSFRow row0 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        row0.createCell(0).setCellValue(EventTemplate.TEMPLATE_PROJECT_NAME);
        row0.createCell(1).setCellValue(EventTemplate.TEMPLATE_PROJECT_INTRO);
        row0.createCell(2).setCellValue(EventTemplate.TEMPLATE_PROJECT_CITY_NAME);
        row0.createCell(3).setCellValue(EventTemplate.TEMPLATE_RECORDS_NAME);
        row0.createCell(4).setCellValue(EventTemplate.TEMPLATE_RECORDS_NAME);
        row0.createCell(5).setCellValue(EventTemplate.TEMPLATE_RECORDS_MOBILE);
        row0.createCell(6).setCellValue(EventTemplate.TEMPLATE_RECORDS_POSITION);
        row0.createCell(7).setCellValue(EventTemplate.TEMPLATE_RECORDS_COMPANY);
        for (int i = 0; i < eventCustomFields.size(); i++) {
            row0.createCell(8 + i).setCellValue(eventCustomFields.get(i).getTitle());
        }
        row0.createCell(8 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_PROJECT_BP_URL);
        row0.createCell(9 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_PROJECT_STAGE);
        row0.createCell(10 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_PROJECT_AMOUNT);
        row0.createCell(11 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_PROJECT_SHARE);
        row0.createCell(12 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_CHECK_STATUS);
        row0.setRowStyle(style);

        //导出数据
        //导出数据
        for (int i = 0; i < resp.list.size(); i++) {
            SearchSignupResp signupResp = resp.list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(signupResp.project.name);
            row.createCell(1).setCellValue(signupResp.project.intro);
            row.createCell(2).setCellValue(POIUtils.empty2Line(signupResp.project.cityName));
            row.createCell(3).setCellValue(POIUtils.empty2Line(signupResp.project.industryName));
            row.createCell(4).setCellValue(signupResp.user.name);
            row.createCell(5).setCellValue(signupResp.user.mobile);
            row.createCell(6).setCellValue(signupResp.user.position);
            row.createCell(7).setCellValue(signupResp.user.company);
            List<AddtionalForm> addtionalForms = signupResp.additionalForm;
            for (int j = 0; j < addtionalForms.size(); i++) {
                row.createCell(8 + j).setCellValue(addtionalForms.get(j).value);
            }
            row.createCell(8 + addtionalForms.size()).setCellValue(signupResp.project.bp == null ? "-" : signupResp
                    .project.bp.bpUrl == null ? "-" : cloudEventConfig.isRelease() ? cloudEventConfig.getRelease_bp_prefix()
                    : cloudEventConfig.getBp_prefix() + signupResp.project.bp.bpUrl);
            row.createCell(9 + addtionalForms.size()).setCellValue(signupResp.project
                    .stage == null ? "-" : Constant.getStageMap().get(signupResp.project.stage));
            row.createCell(10 + addtionalForms.size()).setCellValue(NumberUtil.isValidId(signupResp.project.amount) ?
                    signupResp.project.amount + "万人民币" : "-");
            row.createCell(11 + addtionalForms.size()).setCellValue(signupResp.project.share == null ? "-" :
                    signupResp.project.share + "%");
            row.createCell(12 + addtionalForms.size()).setCellValue(signupResp.isVerify.equals(
                    SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()) ? EventTemplate.CHECK_FAIL :
                    signupResp.isVerify.equals(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()) ?
                            EventTemplate.CHECK_WAIT : EventTemplate.CHECK_PASS);

        }
        return packageIO(wb, resp.event.title + ".xls", response);


    }

    /**
     * 不返回response
     * @param wb
     * @param fileName
     * @param response
     * @return
     */
    private HttpServletResponse packageIO(HSSFWorkbook wb, String fileName, HttpServletResponse response)
    throws IOException{

        FileOutputStream fileOutputStream = new FileOutputStream( fileName);
        wb.write(fileOutputStream);

        return null;
    }

    /**
     * 创业活动excel数据导出
     *
     * @param resp              搜索出来的结果列表
     * @param eventCustomFields 自定义字段
     * @param response          返回结果流
     * @throws IOException
     */
    private HttpServletResponse downLoadCommonRecords(SearchSignupListResp resp, List<EventCustomField>
            eventCustomFields, HttpServletResponse response) throws IOException {

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(resp.event.title);
        //设置行高，宽高
        POIUtils.setDefaultHeighAndWidth(sheet);
        //设置绿色字体字样
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.GREEN.index);//HSSFColor.VIOLET.index //字体颜色
        HSSFCellStyle style = wb.createCellStyle(); // 样式对象
        style.setFont(font);
        //在sheet里创建第一行(标题行)
        HSSFRow row0 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        row0.createCell(0).setCellValue(EventTemplate.TEMPLATE_RECORDS_NAME);
        row0.createCell(1).setCellValue(EventTemplate.TEMPLATE_RECORDS_MOBILE);
        row0.createCell(2).setCellValue(EventTemplate.TEMPLATE_RECORDS_POSITION);
        row0.createCell(3).setCellValue(EventTemplate.TEMPLATE_RECORDS_COMPANY);
        for (int i = 0; i < eventCustomFields.size(); i++) {
            row0.createCell(4 + i).setCellValue(eventCustomFields.get(i).getTitle());
        }
        row0.createCell(4 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_CHECK_STATUS);
        row0.createCell(5 + eventCustomFields.size()).setCellValue(EventTemplate.TEMPLATE_CHECK_IN_STATUS);
        row0.setRowStyle(style);

        //导出数据
        for (int i = 0; i < resp.list.size(); i++) {
            SearchSignupResp signupResp = resp.list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(signupResp.user.name);
            row.createCell(1).setCellValue(signupResp.user.mobile);
            row.createCell(2).setCellValue(signupResp.user.position);
            row.createCell(3).setCellValue(signupResp.user.company);
            List<AddtionalForm> addtionalForms = signupResp.additionalForm;
            for (int j = 0; j < addtionalForms.size(); i++) {
                row.createCell(4 + j).setCellValue(addtionalForms.get(j).value);
            }
            row.createCell(4 + addtionalForms.size()).setCellValue(signupResp.isVerify.equals(
                    SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()) ? EventTemplate.CHECK_FAIL :
                    signupResp.isVerify.equals(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()) ?
                            EventTemplate.CHECK_WAIT : EventTemplate.CHECK_PASS);
            row.createCell(5 + addtionalForms.size()).setCellValue(getCheckInResult(signupResp.ticketTypes));

        }


        return packageIO(wb, resp.event.title + ".xls", response);


    }

    /**
     * 组装流输出格式
     *
     * @param wb 文本
     * @param fileName 文件名
     * @param response 返回流
     */
    public HttpServletResponse packageIOResponse(HSSFWorkbook wb, String fileName, HttpServletResponse response)
            throws IOException {
        String filename = fileName;
        final String userAgent = RequestHolder.getRequestFilterHeaders().get("User-Agent");
        response.reset();
        if (StringUtils.contains(userAgent, "Edge")) {
            filename = new String(filename.getBytes(), "UTF-8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            filename = new String(filename.getBytes(), "ISO8859-1");
        } else {
            filename = new String(filename.getBytes(), "UTF-8");
        }
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.flush();
        output.close();

        return null;
    }

    /**
     * 获取excel的签到状态
     *
     * @param ticketTypes
     * @return
     */
    private String getCheckInResult(List<Ticket> ticketTypes) {

        Integer ticketCount = 0;
        Integer checkinCount = 0;
        for (Ticket ticket : ticketTypes) {
            for (TicketInfo ticketInfo : ticket.ticketList) {
                ticketCount += 1;
                if (ticketInfo.state.equals(SqlEnum.SignInType.TYPE_SIGN_IN_YES.getValue())) {
                    checkinCount += 1;
                }
            }
        }
        if (checkinCount.equals(0)) {
            return EventTemplate.CHECK_IN_NO;
        } else if (ticketCount.equals(checkinCount)) {
            return EventTemplate.CHECK_IN_YES;
        } else {
            return FractionUtil.getFraction(checkinCount, ticketCount);
        }

    }

    public Coupon getCoupon(CouponReq req) {
        if(StringUtil.isEmpty(req.tradeNo)||NumberUtil.isInValidId(req.ruleId)){
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
        EventRecordExample example=new EventRecordExample();
        example.createCriteria().andTradeNoEqualTo(req.tradeNo);
        List<EventRecord> eventRecords=eventRecordMapper.selectByExample(example) ;
        if(StringUtil.isNotEmpty(eventRecords)){
            EventRecordUserExample userExample=new EventRecordUserExample();
            userExample.createCriteria().andEventRecordIdEqualTo(eventRecords.get(0).getId());
            List<EventRecordUser> recordUsers=eventRecordUserMapper.selectByExample(userExample);
            if(StringUtil.isNotEmpty(recordUsers)){
                req.mobile=recordUsers.get(0).getPhone();
            }
        }
        if(StringUtil.isEmpty(req.mobile)){
            throw ExceptionUtil.createParamException("mobile参数异常");
        }
        return couponRemoteService.getCoupon(req);
    }

    /**
     * 获取报名信息
     * @param req
     * @return
     */
    public Object getRecordInfo(EventRecordReq req) {
        EventRecord eventRecord=eventRecordMapper.selectByPrimaryKey(req.recordId);
        if(eventRecord==null){
            throw ExceptionUtil.createParamException("报名记录不存在");
        }
        return converse2EventRecordResp(eventRecord);
    }

    /**
     * eventRecord->EventRecordResp
     * @param eventRecord
     * @return
     */
    private EventRecordResp converse2EventRecordResp(EventRecord eventRecord) {
        EventRecordResp resp=new EventRecordResp();
        resp.uid=eventRecord.getUid();
        resp.id=eventRecord.getId();
        resp.tradeNo=eventRecord.getTradeNo();
        resp.eventId=eventRecord.getEventId();
        resp.extensionLinkId=eventRecord.getExtensionLinkId();
        resp.state=eventRecord.getState();
        resp.signUpType=eventRecord.getSignUpType();
        resp.orgId=eventRecord.getOrgId();
        resp.source=eventRecord.getSource();
        resp.reason=eventRecord.getReason();
        resp.createTime=eventRecord.getCreateTime();
        resp.modifyTime=eventRecord.getModifyTime();
        resp.isImport=eventRecord.getIsImport();
        return resp;
    }
}
