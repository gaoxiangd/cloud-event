package com.welian.service.record.impl;

import com.welian.beans.DeliveryOneBPBatchOrderBeanRequest;
import com.welian.beans.DeliveryOneBPBatchOrderRequest;
import com.welian.beans.DeliveryOrder;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.CreateOrUpdateEventConstant;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.EventAndLinkResultResp;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.Guest;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.UpdateSignUpBPResp;
import com.welian.beans.cloudevent.http.HttpProjectCreateOrUpdateReq;
import com.welian.beans.cloudevent.http.HttpProjectOrderReturnResultResp;
import com.welian.beans.cloudevent.http.HttpProjectReq;
import com.welian.beans.cloudevent.investor.InvestorMatchListResultResp;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.project.ProjectInfoFromOtherServiceRep;
import com.welian.beans.cloudevent.project.ProjectListResultSimpleResp;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.record.EventDetailResp;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.record.ProjectSignUpResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.client.DeliveryClient;
import com.welian.client.commodity.CustomServiceClient;
import com.welian.commodity.beans.CustomServiceDetailReq;
import com.welian.commodity.beans.CustomServiceReq;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.DeliverySource;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordProjectMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.InvestorGroupMapper;
import com.welian.mapper.InvestorGroupRelationMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.mapper.ProjectFeedbackBackupMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventGuest;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordProject;
import com.welian.pojo.EventRecordProjectExample;
import com.welian.pojo.EventSponsor;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.InvestorGroup;
import com.welian.pojo.InvestorGroupRelation;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.pojo.ProjectFeedbackBackup;
import com.welian.pojo.ProjectFeedbackBackupExample;
import com.welian.service.CommodityRemoteService;
import com.welian.service.CustomFormModuleService;
import com.welian.service.GuestModuleService;
import com.welian.service.ProjectService;
import com.welian.service.SolrService;
import com.welian.service.SponsorsModuleService;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.EventStatisticServiceImpl;
import com.welian.service.impl.OrderServiceImpl;
import com.welian.service.impl.StateCustomModuleService;
import com.welian.service.impl.StateProjectModuleServiceImpl;
import com.welian.service.record.IEventRecordProjectInfo;
import com.welian.utils.Base64Utils;
import com.welian.utils.DateUtil;
import com.welian.utils.DateUtils;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.annotation.Synchronized;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeI18NException;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by memorytale on 2017/7/13.
 */
@Component("iEventRecordProjectInfo")
public class IEventRecordProjectInfoImpl extends IEventRecordInfoImpl implements IEventRecordProjectInfo {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventRecordProjectMapper eventRecordProjectMapper;
    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private GuestModuleService guestModuleService;
    @Autowired
    private SponsorsModuleService sponsorsModuleService;
    @Autowired
    private CustomFormModuleService customFormModuleService;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private InvestorGroupMapper investorGroupMapper;
    @Autowired
    private InvestorGroupRelationMapper investorGroupRelationMapper;
    @Autowired
    private ProjectFeedbackBackupMapper projectFeedbackBackupMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private EventStatisticServiceImpl eventStatisticService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private SolrService solrService;
    @Autowired
    private DeliveryClient deliveryClient;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private CustomServiceClient customServiceClient;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RedisService redisService;



    public Object getEventProjectInfoForDetail(ExtensionLink extensionLink) {
        Event event = eventService.getEvent(extensionLink.getEventId());
        EventDetailResp eventDetailResp = (EventDetailResp) getEventDetailInfo(extensionLink.getEventId());
        //活动的可报名状态
        eventDetailResp.signUpType = judgeActivityState(event);
        List<EventSponsor> sponsorList = (List) sponsorsModuleService.get(extensionLink.getEventId());
        List<Sponsor> sponsorReqList = sponsorsModuleService.converseParaList(sponsorList);
        eventDetailResp.sponsors = sponsorReqList;
        //
        if(event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())){
            eventDetailResp.customModule=new CustomModule();
            eventDetailResp.customModule.isCharge=stateCustomModuleService.get(event.getId()).getCostType().intValue();
        }
        List<EventGuest> guestList = (List) guestModuleService.get(extensionLink.getEventId());
        List<Guest> guestReqList = guestModuleService.converseParaList(guestList);
        eventDetailResp.guests = guestReqList;
        eventDetailResp.commonUrl = extensionLink.getLinkUrlCommon();
        eventDetailResp.customUrl = extensionLink.getLinkUrlCustom();
        eventDetailResp.formUrl = extensionLink.getLinkUrlForm();
        return eventDetailResp;
    }


    public Object getEventProjectInfoForForm(Base64KeyReq req) {
        ExtensionLink extensionLink = getExtensionLink(req.uniqueKey);
        EventDetailResp eventDetailResp = (EventDetailResp) getEventInfoForFromPage(extensionLink.getEventId());
        eventDetailResp.formUrl = extensionLink.getLinkUrlForm();//传给客户端的原因是前端分享的时候使用
        EventStateProject eventStateProject = stateProjectModuleService.get(extensionLink.getEventId());
        if (eventStateProject == null) {
            throw ExceptionUtil.createParamException("活动项目信息异常");
        }
        //
        Event event = eventService.getEvent(extensionLink.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常");
        }
        //项目的信息
        eventDetailResp.projectModule = stateProjectModuleService.conversePara(eventStateProject);
        //只有在一键融资活动的时候才会传此状态给客户端
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().byteValue()) {
            eventDetailResp.projectModule.isOpenFinancingService = eventStateProject.getIsOpenFinancing();
        }        //活动的可报名状态
        eventDetailResp.signUpType = judgeActivityState(event);
        //
        EventAndLinkResultResp eventAndLinkResultResp = new EventAndLinkResultResp();
        eventAndLinkResultResp.event = eventDetailResp;

        //自定义字段
        List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(extensionLink.getEventId());
        List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList(eventCustomFieldList);
        eventAndLinkResultResp.additionalForm = addtionalFormList;
        ExtensionLinkResp extensionLinkResp = new ExtensionLinkResp();
        extensionLinkResp.extensionLinkId = extensionLink.getId();
        extensionLinkResp.extensionLinkName = extensionLink.getLinkName();
        eventAndLinkResultResp.extensionLink = extensionLinkResp;

        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(event.getOrgId()).andStateEqualTo((byte) 0);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (orgInfos == null || orgInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        OrgInfo orgInfo = orgInfos.get(0);

        OrgResp orgListBeanResp = new OrgResp();
        orgListBeanResp.orgId = orgInfo.getNewId();
        orgListBeanResp.orgName = orgInfo.getName();
        if (!StringUtil.isEmpty(orgInfo.getLogo())) {
            orgListBeanResp.orgLogo = cloudEventConfig.getImage_prefix() + orgInfo.getLogo();
        }
        eventAndLinkResultResp.org = orgListBeanResp;

        //表单页面浏览数加1
        Event eventCount = new Event();
        eventCount.setId(event.getId());
        eventCount.setFormBrowseCount(event.getFormBrowseCount() + 1);
        eventMapper.updateByPrimaryKeySelective(eventCount);

        return eventAndLinkResultResp;
    }


    public Object getEventProjectList(EventServiceReq req) {
        ProjectListResultSimpleResp projectListResultResp = new ProjectListResultSimpleResp();
        //获取报名列表
        List<EventRecord> eventRecords = getRecordListByEventIdAndLevel(req.eventId,
                req.getPage(), req.getSize(), "id desc", ParamEnum.ProjectRecordListGetType
                        .TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        if (eventRecords == null || eventRecords.isEmpty()) {
            projectListResultResp.count = 0;
            projectListResultResp.list = new ArrayList<>();
            return projectListResultResp;
        }
        //取项目id列表
        List<Integer> integers = new ArrayList<>();
        for (EventRecord eventRecord : eventRecords) {
            integers.add(eventRecord.getId());
        }
        EventRecordProjectExample eventRecordProjectExample = new EventRecordProjectExample();
        eventRecordProjectExample.createCriteria().andEventRecordIdIn(integers);
        List<EventRecordProject> eventRecordProjects = eventRecordProjectMapper.selectByExample
                (eventRecordProjectExample);
        if (eventRecordProjects == null || eventRecordProjects.isEmpty()) {
            projectListResultResp.count = 0;
            projectListResultResp.list = new ArrayList<>();
            return projectListResultResp;
        }
        List<ProjectResp> projectResps = new ArrayList<>();
        ProjectResp projectResp;
        for (EventRecordProject eventRecordProject : eventRecordProjects) {
            ProjectBackupInfoExample example = new ProjectBackupInfoExample();
            example.createCriteria().andPidEqualTo(eventRecordProject.getPid())
                    .andEventRecordIdEqualTo(eventRecordProject.getEventRecordId());
            example.setLimit(1);
            example.setOffset(0);
            List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(example);
            if (projectBackupInfos != null && !projectBackupInfos.isEmpty()) {
                ProjectBackupInfo projectBackupInfo = projectBackupInfos.get(0);
                projectResp = new ProjectResp();
                projectResp.pid = projectBackupInfo.getPid();
                projectResp.name = projectBackupInfo.getName();
                projectResp.intro = projectBackupInfo.getIntro();
                if (NumberUtil.isValidId(projectBackupInfo.getCreateTime())) {
                    projectResp.signUpTime = DateUtils.getTimeFormatText(projectBackupInfo.getCreateTime());
                } else {
                    projectResp.signUpTime = "";
                }
                if (!StringUtil.isEmpty(projectBackupInfo.getLogo())) {
                    projectResp.logo = cloudEventConfig.getImage_prefix() + projectBackupInfo.getLogo();
                }
                projectResps.add(projectResp);
            }
        }
        projectListResultResp.list = projectResps;
        projectListResultResp.count = getRecordCountByEventIdAndLevel(req.eventId,
                ParamEnum.ProjectRecordListGetType.TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        return projectListResultResp;
    }


    @Transactional
    public HttpProjectOrderReturnResultResp batchOrderFromChannel(ExtensionLinkReq req) {
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(req.extensionLinkId);
        if (extensionLink == null) {
            throw ExceptionUtil.createParamException("链接不存在");
        }
        Event event = eventMapper.selectByPrimaryKey(extensionLink.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("链接所属活动不存在");
        }
        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
        if (eventStateProject.getIsOpenFinancing() == CreateOrUpdateEventConstant.OpenFinancingServiceType.NOT_OPEN) {
            throw ExceptionUtil.createParamException("对不起，此活动的一键融资服务已关闭，不能匹配投资人");
        }
        //获取报名记录
        List<EventRecordProject> eventRecordProjects = eventRecordProjectMapper.getEventRecord(event.getId(), req.pid);
        if (eventRecordProjects == null || eventRecordProjects.isEmpty()) {
            throw ExceptionUtil.createParamException("报名记录不存在");
        }
        EventRecordProject eventRecordProject = eventRecordProjects.get(0);
        //判断是否已经有了报名记录
        ProjectFeedbackBackupExample projectFeedbackBackupExample = new ProjectFeedbackBackupExample();
        projectFeedbackBackupExample.createCriteria().andEventRecordIdEqualTo(eventRecordProject.getEventRecordId());
        long count = projectFeedbackBackupMapper.countByExample(projectFeedbackBackupExample);
        if (count > 0) {
            throw ExceptionUtil.createParamException("你已经投递过项目，不能再次投递");
        }

        //进行批量投递功能
        DeliveryOneBPBatchOrderRequest orderBeanRequest = new DeliveryOneBPBatchOrderRequest();
        orderBeanRequest.source = DeliverySource.THIRD_CHANNEL.getCode();
        orderBeanRequest.bpId = req.pid;
        orderBeanRequest.deliveryUid = null;//如果不传默认项目的创建者uid
        orderBeanRequest.deliveryOneBPBatchOrderBeans = new ArrayList<>();
        DeliveryOneBPBatchOrderBeanRequest bpBatchOrderBeanRequest;
        for (UserResp user : req.investors) {
            if (user == null || NumberUtil.isInValidId(user.uid)) {
                continue;
            }
            bpBatchOrderBeanRequest = new DeliveryOneBPBatchOrderBeanRequest();
            bpBatchOrderBeanRequest.investorUid = user.uid;
            bpBatchOrderBeanRequest.channelId = event.getOrgId();
            bpBatchOrderBeanRequest.friendRelationShip = 0;
            orderBeanRequest.deliveryOneBPBatchOrderBeans.add(bpBatchOrderBeanRequest);
        }
        BaseResult<DeliveryOrder> baseResult = deliveryClient.order2(orderBeanRequest);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        if (StringUtil.isEmpty(baseResult.getList())) {
            throw ExceptionUtil.createParamException("批量投递失败");
        }
        //更新数量
        EventRecordProject eventRecordProject1 = new EventRecordProject();
        eventRecordProject1.setId(eventRecordProject.getId());
        eventRecordProject1.setDeliveryCount(baseResult.getList().size());
        eventRecordProjectMapper.updateByPrimaryKeySelective(eventRecordProject1);

        //将返回的投递的数据存入数据表
        ProjectFeedbackBackup projectFeedbackBackup;
        for (DeliveryOrder httpProjectOrderReturnListResp : baseResult.getList()) {
            projectFeedbackBackup = new ProjectFeedbackBackup();
            projectFeedbackBackup.setCreateTime(System.currentTimeMillis());
            projectFeedbackBackup.setModifyTime(System.currentTimeMillis());
            projectFeedbackBackup.setOrderTime(httpProjectOrderReturnListResp.getCreateTime());
            projectFeedbackBackup.setOrderId(httpProjectOrderReturnListResp.getId());
            projectFeedbackBackup.setState(SqlEnum.ProjectFeedbackState.TYPE_DEFAULT.getValue());
            projectFeedbackBackup.setInvestorId(httpProjectOrderReturnListResp.getInvestorUid());
            projectFeedbackBackup.setEventRecordId(eventRecordProject.getEventRecordId());
            projectFeedbackBackupMapper.insertSelective(projectFeedbackBackup);
        }
        //
        HttpProjectOrderReturnResultResp returnResultResp = new HttpProjectOrderReturnResultResp();
        returnResultResp.orderSuccessCount = baseResult.getList().size();
        returnResultResp.signUpId = eventRecordProject.getEventRecordId();
        return returnResultResp;
    }


    public Object updateSignUpBP(ExtensionLinkReq req) {
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(req.extensionLinkId);
        if (extensionLink == null) {
            throw ExceptionUtil.createParamException("链接不存在");
        }
        EventStateProject eventStateProject = stateProjectModuleService.get(extensionLink
                .getEventId());
        //获取报名记录
        List<EventRecordProject> eventRecords = eventRecordProjectMapper.
                getEventRecordByExtensionLinkIdAndPid(req.extensionLinkId, req.pid);
        if (StringUtil.isEmpty(eventRecords)) {
            throw ExceptionUtil.createParamException("没有报名记录");
        }
        ProjectBackupInfo projectBackupInfo = new ProjectBackupInfo();
        //更新项目的备份表中的项目bp的有关字段
        projectBackupInfo.setProjectSignBpUrl(req.bpUrl);
        projectBackupInfo.setProjectSignBpId(req.bpId);
        projectBackupInfo.setProjectSignBpName(req.bpName);

        ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
        projectBackupInfoExample.createCriteria().andPidEqualTo(req.pid).
                andEventRecordIdEqualTo(eventRecords.get(0).getEventRecordId());//只可能有一个
        projectBackupInfoMapper.updateByExampleSelective(projectBackupInfo, projectBackupInfoExample);

        UpdateSignUpBPResp updateSignUpBPResp = new UpdateSignUpBPResp();
        ExtensionLinkReq extension = new ExtensionLinkReq();
        extension.uniqueKey = extensionLink.getUniqueKey();
        updateSignUpBPResp.extensionUrlContent = Base64Utils.encode(JSONUtil.obj2Json(extension).getBytes()).toString();
        updateSignUpBPResp.isOpenFinancingService = Integer.valueOf(eventStateProject.getIsOpenFinancing());
        updateSignUpBPResp.signUpId = eventRecords.get(0).getEventRecordId();
        return updateSignUpBPResp;
    }


    public Object updateSignUpBPByRecordId(ExtensionLinkReq req) {
        EventRecordProjectExample eventRecordProjectExample = new EventRecordProjectExample();
        eventRecordProjectExample.createCriteria().andEventRecordIdEqualTo(req.recordId);
        List<EventRecordProject> eventRecords = eventRecordProjectMapper.selectByExample(eventRecordProjectExample);
        if (StringUtil.isEmpty(eventRecords)) {
            throw ExceptionUtil.createParamException("没有报名记录");
        }
        ProjectBackupInfo projectBackupInfo = new ProjectBackupInfo();
        //更新项目的备份表中的项目bp的有关字段
        projectBackupInfo.setProjectSignBpUrl(req.bpUrl);
        projectBackupInfo.setProjectSignBpId(req.bpId);
        projectBackupInfo.setProjectSignBpName(req.bpName);

        ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
        projectBackupInfoExample.createCriteria().andPidEqualTo(req.pid).
                andEventRecordIdEqualTo(eventRecords.get(0).getEventRecordId());//只可能有一个
        projectBackupInfoMapper.updateByExampleSelective(projectBackupInfo, projectBackupInfoExample);

        UpdateSignUpBPResp updateSignUpBPResp = new UpdateSignUpBPResp();
        updateSignUpBPResp.signUpId = eventRecords.get(0).getEventRecordId();
        return updateSignUpBPResp;
    }


    public ProjectSignUpResp saveProjectAndSignUp(ProjectSignUpReq req, Event event, Integer moduleType) {
        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
        //先去创建或者修改此项目
        ProjectInfoFromOtherServiceRep projectInfoFromOtherServiceRep = getFromOtherServer(req,
                req.extensionLinkId, eventStateProject);
        //如果是创建项目时，前端传入的没有pid，保存完成后，将返回的pid重新赋值
        req.project.pid = projectInfoFromOtherServiceRep.pid;
        if (projectInfoFromOtherServiceRep.bp != null) {
            req.bp = projectInfoFromOtherServiceRep.bp;
        }
        return signUp(req, event, moduleType);
    }


    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, argIndex = 1, keyName = "id")//锁定活动
    public ProjectSignUpResp signUp(ProjectSignUpReq req, Event event, Integer moduleType) {



        Long expireTime = DateUtil.curTimeMillis() + 10 * 60 * 1000;
        //判断活动是否符合
        judgeActivityStateToThrow(event);
        //判断是否已经报过名了
        judgeHasSignUpActivity(event.getId(), req.project.pid);
        //保存活动报名，获取订单号
        EventRecord eventRecord = saveProjectRecord(req, event, expireTime);
        //保存活动报名时填写的自定义字段
        saveRecordCustomField(req.additionalForm, eventRecord.getId(),event.getId());
        //保存项目备份
        saveProjectBackUp(req, eventRecord.getId(), moduleType);
        //保存报名者的信息
        saveRecordUser(req.user, eventRecord.getId());
        //下单
        commodityRemoteService.createOrderForProject(event, req.user, expireTime, eventRecord
                .getTradeNo(), true);
        //将信息返回客户端
        ProjectSignUpResp projectSignUpResp = new ProjectSignUpResp();
        projectSignUpResp.tradeNo=eventRecord.getTradeNo();
        projectSignUpResp.pid = req.project.pid;
        projectSignUpResp.signUpId = eventRecord.getId();
        projectSignUpResp.recordStatus = eventRecord.getState();
        projectSignUpResp.startTime = event.getStartTime();
        projectSignUpResp.endTime = event.getEndTime();
        projectSignUpResp.eventId=event.getId();
        projectSignUpResp.recordId=eventRecord.getId();
        projectSignUpResp.uid=eventRecord.getUid();
        return projectSignUpResp;
    }


    /**
     * 判断是否报名了活动
     *
     * @param eventId
     * @param pid
     */
    private void judgeHasSignUpActivity(Integer eventId, Integer pid) {
        int count = eventRecordMapper.hasSignUpCount(eventId, pid);
        if (count != 0) {
            throw ExceptionUtil.createParamException("该项目已经报名此活动");
        }
    }

    @Transactional
    public EventRecord saveProjectRecord(ProjectSignUpReq req, Event event, Long expireTime) {
        //生成订单(3.4.0版本后该字段已废弃）
        String orderNumber = orderService.createOrderNumber();
        //生成票单(3.4.0版本后该字段已废弃）
        String ticketNumber = orderService.createTicketNumber();

        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getId());
        eventRecord.setOrgId(event.getOrgId());
        eventRecord.setCreateTime(System.currentTimeMillis());
        eventRecord.setExtensionLinkId(req.extensionLinkId);
        eventRecord.setSignUpType(SqlEnum.SignUpType.TYPE_EVENT_PROJECT.getByteValue());//项目报名
        eventRecord.setModifyTime(System.currentTimeMillis());
        eventRecord.setSource((byte) (int) req.type);
        eventRecord.setUid(req.user.uid);
        eventRecord.setOrderNumber(orderNumber);
        eventRecord.setTicketNumber(ticketNumber);
        //获得订单号
        String tradeNo = commodityRemoteService.getOrderNo();
        eventRecord.setTradeNo(tradeNo);
        eventRecord.setTradeEndTime(expireTime);
        eventRecord.setTicketLock(SqlEnum.TicketLockType.TYPE_LOCK.getValue());
        EventStateProject eventStateProject = stateProjectModuleService.
                get(event.getId());
        //如果需要审核，需要将状态置为待审核状态
        if (eventStateProject.getVerifyType() != null &&
                eventStateProject.getVerifyType() == SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getByteValue()
                        .byteValue()) {
            eventRecord.setState(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        } else {
            eventRecord.setState(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        }

        int result = eventRecordMapper.insertSelective(eventRecord);
        if (result == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }

        EventRecordProject eventRecordProject = new EventRecordProject();
        eventRecordProject.setCreateTime(System.currentTimeMillis());
        eventRecordProject.setEventRecordId(eventRecord.getId());
        eventRecordProject.setPid(req.project.pid);
        eventRecordProject.setSignUpTime(System.currentTimeMillis());
        eventRecordProject.setStarLevel((byte) 0);
        int result1 = eventRecordProjectMapper.insertSelective(eventRecordProject);
        if (result1 == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }
        saveTicketRecordUrl(eventRecord.getId());
        return eventRecord;

    }

    /**
     * 用户主动取消报名
     *
     * @param recordId
     * @return
     */

    @Transactional
    public void signUpCancel(Integer recordId) {
        if (!NumberUtil.isValidId(recordId)) {
            throw ExceptionUtil.createParamException("recordId参数错误");
        }
        EventRecord record = eventRecordMapper.selectByPrimaryKey(recordId);
        Event event = eventMapper.selectByPrimaryKey(record.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (record == null) {
            throw ExceptionUtil.createParamException("活动记录不存在");
        }

        //活动已经结束，不能取消
        if (event.getEndTime() < DateUtil.getNowDate().getTime()) {
            throw ExceptionUtil.createParamException("活动已结束不能取消");
        }
        //活动已经开始不能取消
        if (event.getStartTime() <= System.currentTimeMillis()) {
            throw ExceptionUtil.createParamException("活动已经开始不能取消");
        }

        //只有审核中和已经通过的状态会修改状态后计数
        if (record.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue() ||
                record.getState() == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue()) {

            record.setState(SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue());
            eventRecordMapper.updateByPrimaryKeySelective(record);

            EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
            eventTicketOrderExample.createCriteria().andEventRecordIdEqualTo(recordId);
            List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(eventTicketOrderExample);

            //只有免费活动
            if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) {
                EventStateCustomExample example = new EventStateCustomExample();
                example.createCriteria().andEventIdEqualTo(event.getId());
                List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(example);
                if (eventStateCustoms != null && eventStateCustoms.size() > 0) {
                    EventStateCustom eventStateCustom = eventStateCustoms.get(0);
                    if (eventStateCustom.getCostType() == SqlEnum.CostType.TYPE_CHARGE.getByteValue()) {
                        throw ExceptionUtil.createParamException("收费活动不能取消");
                    } else {
                        if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                            EventTicketOrder eventTicketOrder = eventTicketOrders.get(0);
                            if (eventTicketOrder.getSignState() == SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue()) {
                                throw ExceptionUtil.createParamException("已签到不能取消");
                            }
                        }
                        int result = eventStateCustomMapper.updateRecordCount(eventStateCustom.getEventId(), -1);
                        if (result == 0) {
                            throw ExceptionUtil.createParamException("更新报名数量失败");
                        }

                        solrService.updateSolrInfoById(event.getId().toString(), "joined_count", eventStateCustom
                                .getJoinedCount
                                () - 1);
                    }
                }
            } else {
                //报名人数减一
                //int count = (int) getEventRecordCount(event.getId());
                EventStateProjectExample eventStateExample = new EventStateProjectExample();
                eventStateExample.createCriteria().andEventIdEqualTo(event.getId());
                EventStateProject eventStateProject = eventStateProjectMapper.selectByExample(eventStateExample).get(0);
                stateProjectModuleService.updateRecordCount(eventStateProject, -1);

                solrService.updateSolrInfoById(event.getId().toString(), "joined_count", eventStateProject
                        .getJoinedCount
                        () - 1);

            }
            EventTicketOrderExample ex = new EventTicketOrderExample();
            ex.createCriteria().andEventRecordIdEqualTo(recordId);
            Integer ticketCount = (int) eventTicketOrderMapper.countByExample(ex);
            //统计报名记录表里减
            eventStatisticService.countChangeAfterProjectsJoin(record, -1, -ticketCount);


            //调用商品接口把库存释放
            CustomServiceReq req = new CustomServiceReq();
            req.commodityOrderNo = record.getTradeNo();
            req.customServiceDetailReqs = new ArrayList<>();
            req.price = Constant.FREE_PRICE_LONG;
            req.uid = record.getUid();
            CustomServiceDetailReq customServiceDetailReq;
            for (EventTicketOrder eventTicketOrder : eventTicketOrders) {
                customServiceDetailReq = new CustomServiceDetailReq();
                customServiceDetailReq.commodityOrderDetailId = eventTicketOrder.getCommodityDetailId();
                //免费票传0
                customServiceDetailReq.price = Constant.FREE_PRICE_LONG;
                req.customServiceDetailReqs.add(customServiceDetailReq);
            }
            customServiceClient.save(req);
        }

    }

    /**
     * 3.4.0后兼容创业活动，去掉SignUpType为project条件
     *
     * @param eventId
     * @param page
     * @param size
     * @param orderClause
     * @param level
     * @return
     */

    public List<EventRecord> getRecordListByEventIdAndLevel(Integer eventId, Integer page,
                                                            Integer size, String orderClause, ParamEnum
                                                                    .ProjectRecordListGetType level) {
        List<Byte> integers = getStatesByLevel(level);
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andEventIdEqualTo(eventId).andStateIn(integers).
                andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        if (NumberUtil.isValidId(page) && NumberUtil.isValidId(size)) {
            eventRecordExample.setLimit(size);
            eventRecordExample.setOffset(PagingUtil.getStart(page, size));
        }
        if (!StringUtil.isEmpty(orderClause)) {
            eventRecordExample.setOrderByClause(orderClause);
        }
        return eventRecordMapper.selectByExample(eventRecordExample);
    }


    private ProjectInfoFromOtherServiceRep getFromOtherServer(ProjectSignUpReq req, Integer extensionLinkId,
                                                              EventStateProject eventStateProject) {
        HttpProjectCreateOrUpdateReq httpProjectCreateOrUpdateReq = new HttpProjectCreateOrUpdateReq();
        httpProjectCreateOrUpdateReq.extensionLinkId = extensionLinkId;
        httpProjectCreateOrUpdateReq.type = req.type;
        httpProjectCreateOrUpdateReq.user = req.user;
        httpProjectCreateOrUpdateReq.bp = req.bp;
        if (SqlEnum.ProjectModuleType.TYPE_DETAIL.getByteValue().byteValue() == eventStateProject.getProjectInputType
                ()) {
            httpProjectCreateOrUpdateReq.financingInfo = req.financingInfo;
            httpProjectCreateOrUpdateReq.project = req.project;
//            BaseResult<ProjectInfoFromOtherServiceRep> baseResult =
//                    projectClientFromServer.createOrUpdate(httpProjectCreateOrUpdateReq);
//            if (!baseResult.isSuccess() || baseResult.getData() == null
//                    || baseResult.getData().pid == null) {
//                throw ExceptionUtil.createParamException(baseResult.getErrormsg());
//
//            }
//            return baseResult.getData();
            return projectService.createOrUpdateDetailed(httpProjectCreateOrUpdateReq);
        } else if (SqlEnum.ProjectModuleType.TYPE_SIMPLE.getByteValue().byteValue() == eventStateProject
                .getProjectInputType()) {
            httpProjectCreateOrUpdateReq.project = new ProjectBaseReq();
            httpProjectCreateOrUpdateReq.project.pid = req.project.pid;
            httpProjectCreateOrUpdateReq.project.name = req.project.name;
            httpProjectCreateOrUpdateReq.project.intro = req.project.intro;
            httpProjectCreateOrUpdateReq.project.logo = req.project.logo;
            httpProjectCreateOrUpdateReq.project.projectStage = req.project.projectStage;
            httpProjectCreateOrUpdateReq.project.website = req.project.website;
//            BaseResult<ProjectInfoFromOtherServiceRep> baseResult =
//                    projectClientFromServer.createOrUpdate2(httpProjectCreateOrUpdateReq);
//            if (!baseResult.isSuccess() || baseResult.getData() == null
//                    || baseResult.getData().pid == null) {
//                throw ExceptionUtil.createParamException(baseResult.getErrormsg());
//
//            }
//            return baseResult.getData();
            return projectService.createOrUpdateSimple(httpProjectCreateOrUpdateReq);
        } else {
            throw ExceptionUtil.createParamException("项目模板异常");
        }

    }

    /**
     * 更新项目备份表
     *
     * @param moduleType 活动中项目的模板类型,如果是简单版的，需要对传入的数据部分存入
     */
    public void saveProjectBackUp(ProjectSignUpReq req, Integer eventRecordId, Integer moduleType) {
        ProjectBackupInfoExample example = new ProjectBackupInfoExample();
        example.createCriteria().andPidEqualTo(req.project.pid).andEventRecordIdEqualTo(eventRecordId);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(example);
        ProjectBackupInfo projectBackupInfo = new ProjectBackupInfo();
        projectBackupInfo.setName(req.project.name);
        projectBackupInfo.setIntro(req.project.intro);
        projectBackupInfo.setLogo(req.project.logo);
        if (moduleType == SqlEnum.ProjectModuleType.TYPE_DETAIL.getValue().intValue()) {
            if (req.project == null || req.financingInfo == null || req.user == null) {
                throw ExceptionUtil.createParamException("备份参数错误");
            }
            projectBackupInfo.setCityId(req.project.cityId);
            projectBackupInfo.setCityName(req.project.cityName);
            projectBackupInfo.setIndustryId(req.project.industryFirstId);
            projectBackupInfo.setIndustryName(req.project.industryFirstName);
            projectBackupInfo.setProjectAmount(req.financingInfo.amount);
            projectBackupInfo.setProjectAmountUnit((byte) (int) req.financingInfo.amountUnit);
            projectBackupInfo.setProjectShare(req.financingInfo.share);
            projectBackupInfo.setProjectStage(req.financingInfo.stage);
        } else {
            if (req.project == null || req.user == null) {
                throw ExceptionUtil.createParamException("备份参数错误");
            }
        }
        projectBackupInfo.setProjectCreateUid(req.user.uid);
        projectBackupInfo.setModifyTime(System.currentTimeMillis());
        projectBackupInfo.setEventRecordId(eventRecordId);
        if (req.bp != null) {
            projectBackupInfo.setProjectSignBpId(req.bp.bpId);
            projectBackupInfo.setProjectSignBpName(req.bp.bpName);
            projectBackupInfo.setProjectSignBpUrl(req.bp.bpUrl);
        }
        if (projectBackupInfos == null || projectBackupInfos.isEmpty()) {
            projectBackupInfo.setPid(req.project.pid);
            projectBackupInfo.setCreateTime(System.currentTimeMillis());
            int result = projectBackupInfoMapper.insertSelective(projectBackupInfo);
            if (result == 0) {
                throw ExceptionUtil.createParamException("保存项目备份失败");
            }
        } else {
            ProjectBackupInfoExample backupInfoExample = new ProjectBackupInfoExample();
            backupInfoExample.createCriteria().andPidEqualTo(req.project.pid).andEventRecordIdEqualTo(eventRecordId);
            int result = projectBackupInfoMapper.updateByExampleSelective(projectBackupInfo, backupInfoExample);
            if (result == 0) {
                throw ExceptionUtil.createParamException("修改项目备份失败");
            }
        }
    }


    /**
     * 判断活动的状态
     *
     * @param event
     * @return
     */
    public Integer judgeActivityState(Event event) {
        Integer activityType = ParamEnum.EventCanSignStateType.TYPE_ERROR.getValue();
        if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getValue().intValue()) {
            activityType = ParamEnum.EventCanSignStateType.TYPE_CANCEL.getValue();
            return activityType;
        }
        if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
            activityType = ParamEnum.EventCanSignStateType.TYPE_CANCEL.getValue();
            return activityType;
        }
        if (event.getEndTime() != null && event.getEndTime() < System.currentTimeMillis()) {
            activityType = ParamEnum.EventCanSignStateType.TYPE_FINISHED.getValue();
            return activityType;
        }
        //判断活动时间是否有效
        if (event.getDeadlineTime() != null) {
            if (event.getDeadlineTime() < System.currentTimeMillis()) {
                activityType = ParamEnum.EventCanSignStateType.TYPE_DEADLINE.getValue();
            } else {
                //项目报名
                //查找总共的报名人数
                if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue()) ||
                        event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue())) {
                    long count = getRecordCountByEventIdAndLevel(event.getId(),
                            ParamEnum.ProjectRecordListGetType.TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
                    //查询票的总数量
                    List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
                    if (tickets == null || tickets.isEmpty()) {
                        throw ExceptionUtil.createParamException("找不到票");
                    }
                    Long ticketCount = tickets.get(0).count.longValue();
                    if (ticketCount.equals(Constant.NO_LIMIT_NUMBER.longValue())) {
                        activityType = ParamEnum.EventCanSignStateType.TYPE_CAN_SIGN_UP.getValue();
                        return activityType;
                    }
                    if (ticketCount < count) {
                        activityType = ParamEnum.EventCanSignStateType.TYPE_ERROR.getValue();
                        return activityType;
                    } else if (ticketCount == count) {
                        activityType = ParamEnum.EventCanSignStateType.TYPE_TICKET_NO.getValue();
                        return activityType;
                    } else if (ticketCount > count) {
                        activityType = ParamEnum.EventCanSignStateType.TYPE_CAN_SIGN_UP.getValue();
                        return activityType;
                    }
                } else if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                    List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
                    if (StringUtil.isEmpty(tickets)) {
                        throw ExceptionUtil.createParamException("找不到票");
                    }
                    activityType = ParamEnum.EventCanSignStateType.TYPE_CAN_SIGN_UP.getValue();
                    return activityType;
                }
            } ///else 结束
            ///
        }
        return activityType;
    }

    public void judgeActivityStateToThrow(Event event) {
        if( SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue().equals(event.getState())){
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ,"error_custom_signup_event_deleted");
        }
        if( !SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue().equals(event.getState()) ){
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ,"error_custom_signup_event_not_publish");
        }
        //判断活动时间是否有效
        if (event.getDeadlineTime() != null) {
            if (event.getDeadlineTime() < redisService.getCurrentTime()) {
                throw ExceptionUtil.createParamException("活动报名已截止，无法报名");
            } else {
                List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
                if (tickets.size() != 1) {
                    throw ExceptionUtil.createParamException("商品服务返回的规格信息异常");
                }
                Ticket ticket = tickets.get(0);
                if (ticket.remaindCount == 0) {
                    throw ExceptionUtil.createParamException("此活动报名已满，无法报名");
                }

            }
        }
    }
}
