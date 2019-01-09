package com.welian.service.impl;

import com.welian.beans.account.AuthPageData;
import com.welian.beans.account.Member;
import com.welian.beans.account.Organization;
import com.welian.beans.account.OrganizationAuthMaterial;
import com.welian.beans.account.Package;
import com.welian.beans.account.Role;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.org.CityResp;
import com.welian.beans.cloudevent.org.CreateOrUpdateOrgReq;
import com.welian.beans.cloudevent.org.EventBillsResp;
import com.welian.beans.cloudevent.org.EvidencesResp;
import com.welian.beans.cloudevent.org.MemberInfoResp;
import com.welian.beans.cloudevent.org.MembersReq;
import com.welian.beans.cloudevent.org.OrderChannelListReq;
import com.welian.beans.cloudevent.org.OrderChannelListResultResp;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgAuthResp;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.org.OrgListBeanResp;
import com.welian.beans.cloudevent.org.OrgListResp;
import com.welian.beans.cloudevent.org.OrgMemberDataResp;
import com.welian.beans.cloudevent.org.OrgPackagePageReq;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.org.PackageResp;
import com.welian.beans.cloudevent.sys.SysUserRelationReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.commodity.beans.Goods;
import com.welian.commodity.beans.Liquidation;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.account.EnumAuthSource;
import com.welian.enums.account.EnumSource;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventOrderMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventSysMessageMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventOrder;
import com.welian.pojo.EventOrderExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventSysMessage;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.service.CommodityRemoteService;
import com.welian.service.UserService;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by memorytale on 2017/7/5.
 */
@Service("orgService")
public class OrgServiceImpl{
    @Autowired
    private UserService userService;
    @Autowired
    private OrgPrivilegeRemoteServiceImpl orgPrivilegeRemoteService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private EventSysMessageMapper eventSysMessageMapper;
    @Autowired
    private SysMsgServiceImpl sysMsgService;
    @Autowired
    private EventOrderMapper eventOrderMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;



    public Object createOrUpdate(CreateOrUpdateOrgReq req) {
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数错误");
        }
        if (NumberUtil.isValidId(req.orgId)) {
            if (StringUtil.isEmpty(req.logo)) {
                throw ExceptionUtil.createParamException("请上传logo");
            }
            if (StringUtil.isEmpty(req.name)) {
                throw ExceptionUtil.createParamException("请填写机构名称");
            }
            if (req.name.trim().length() > 20) {
                throw ExceptionUtil.createParamException("机构名称不能超过20字");
            }
            if (StringUtil.isEmpty(req.intro)) {
                throw ExceptionUtil.createParamException("请填写机构简介");
            }
            if (req.intro.trim().length() > 50) {
                throw ExceptionUtil.createParamException("机构简介不能超过50字");
            }
            switch (req.type) {
                case 1://运营后台修改机构
                    if (!StringUtil.isMobile(req.mobile)) {
                        throw ExceptionUtil.createParamException("请传入正确格式的手机号码");
                    }
                    return updateByOwn(req);
                case 2://用户修改机构
                    if (!StringUtil.isEmpty(req.website) && req.website.trim().length() > 200) {
                        throw ExceptionUtil.createParamException("机构网址不能超过200字");
                    }
                    if (!StringUtil.isEmpty(req.address) && req.address.trim().length() > 200) {
                        throw ExceptionUtil.createParamException("机构地址不能超过200字");
                    }
                    return updateByUserSelf(req);
                default:
                    throw ExceptionUtil.createParamException("type参数异常");
            }
        } else {
            if (StringUtil.isEmpty(req.logo)) {
                throw ExceptionUtil.createParamException("请上传logo");
            }
            if (StringUtil.isEmpty(req.name)) {
                throw ExceptionUtil.createParamException("请填写机构名称");
            }
            if (req.name.trim().length() > 20) {
                throw ExceptionUtil.createParamException("机构名称不能超过20字");
            }
            if (StringUtil.isEmpty(req.intro)) {
                throw ExceptionUtil.createParamException("请填写机构简介");
            }
            if (req.intro.trim().length() > 50) {
                throw ExceptionUtil.createParamException("机构简介不能超过50字");
            }
            switch (req.type) {
                case 1://运营后台添加机构
                    if (!StringUtil.isMobile(req.mobile)) {
                        throw ExceptionUtil.createParamException("请传入正确格式的手机号码");
                    }
                    return createByOwn(req);
                case 2://用户创建机构
                    return createByUserSelf(req);
                default:
                    throw ExceptionUtil.createParamException("type参数异常");
            }
        }

    }



    public Object auth(OrgPageReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.verifyStatus)) {
            throw ExceptionUtil.createParamException("verifyStatus参数错误");
        }
        if (StringUtil.isEmpty(req.evidences)) {
            throw ExceptionUtil.createParamException("请上传证明材料");
        }
        if (req.evidences.size() > 4) {
            throw ExceptionUtil.createParamException("最多支持上传4张证明材料");
        }
        if (req.verifyStatus != EnumAuthSource.TYPE_WAIT_VERIFY.getValue().byteValue() &&
                req.verifyStatus != EnumAuthSource.TYPE_DEFAULT.getValue().byteValue()) {
            throw ExceptionUtil.createParamException("无权操作");
        }
        orgPrivilegeRemoteService.updateVerify(req);
        return null;
    }


    /**
     * 账单管理
     *
     * @param req
     * @return
     */

    public Object bills(OrderChannelReq req) {
        EventBillsResp eventBillsRespR = new EventBillsResp();
        eventBillsRespR.list = new ArrayList<>();

        List<Event> eventList = eventMapper.selectEndEvent(req.orgId, System.currentTimeMillis());
        if (eventList == null || eventList.size() == 0) {
            return eventBillsRespR;
        }

        List<Integer> ids = new ArrayList<>();
        for (Event event : eventList) {
            ids.add(event.getCommodityId());
        }

        Long incomeSum = Long.valueOf(0);
        List<Goods> goods = commodityRemoteService.getCommodityGoods(ids);
        Map<Integer, Long> incomeMap = new HashMap<>();
        for (Goods good : goods) {
            if (good.income == null) {
                good.income = Long.valueOf(0);
            }
            incomeMap.put(good.id, good.income);
            incomeSum = incomeSum + good.income;
        }

        Map<Integer, String> orderMap = new HashMap<>();
        EventOrderExample eventOrderEm = new EventOrderExample();
        eventOrderEm.createCriteria().andCommodityIdIn(ids);
        List<EventOrder> eventOrders = eventOrderMapper.selectByExample(eventOrderEm);
        for (EventOrder eventOrder : eventOrders) {
            orderMap.put(eventOrder.getCommodityId(), eventOrder.getBatchnum());
        }


        for (Event event : eventList) {
            Long income = incomeMap.get(event.getCommodityId());
            if (income == null || income == 0) {
                continue;
            }

            EventBillsResp eventBillsResp = new EventBillsResp();
            eventBillsResp.event = new EventPara();
            eventBillsResp.event.id = event.getId();
            eventBillsResp.event.title = event.getTitle();
            eventBillsResp.event.endTime = event.getEndTime();

            eventBillsResp.totalAmount = income.toString();
            if (income >= 10000){ //100元以下1元
                Double serviceAmount = income * cloudEventConfig.getPoundage();
                Long receiptAmount = income - Math.round(serviceAmount);
                eventBillsResp.receiptAmount = receiptAmount.toString();
            }else if (income >= 100 && income < 10000){
                eventBillsResp.receiptAmount = String.valueOf(income - 100);
            }else {
                eventBillsResp.receiptAmount = "0";
            }

            boolean contains = orderMap.containsKey(event.getCommodityId());
            if (contains) {
                //有批次号
                Liquidation liquidation = commodityRemoteService.getLiquidations(orderMap.get(event.getCommodityId()));
                if (liquidation == null) {
                    eventBillsResp.state = 0;
                } else {
                    //1申请中 2成功 3失败
                    eventBillsResp.state = liquidation.status;
                    eventBillsResp.reason = liquidation.note;
                }
            } else {
                eventBillsResp.state = 0;
            }

            eventBillsRespR.list.add(eventBillsResp);
        }

        eventBillsRespR.orgTotalAmount = incomeSum.toString();
        return eventBillsRespR;
    }

    @Transactional

    public Object settlementapply(Integer eventId, Integer uid ,  String intro) {

        Event event = eventService.getEvent(eventId);
        if (event.getOldEventId() != 0) {
            throw ExceptionUtil.createParamException("老活动数据不能结算");
        }

        EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
        eventStateCustomExample.createCriteria().andEventIdEqualTo(eventId);
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(eventStateCustomExample);

        if (event.getState() != SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue()) {
            throw ExceptionUtil.createParamException("未发布的活动不能结算");
        } else if (event.getEndTime() > System.currentTimeMillis()) {
            throw ExceptionUtil.createParamException("未结束的活动不能结算");
        } else if (eventStateCustoms.get(0).getCostType() == SqlEnum.CostType.TYPE_CHARGE_NOT.getByteValue()) {
            throw ExceptionUtil.createParamException("收费活动才能结算");
        }

        User user = getAdminUser(event.getOrgId());
        if (user == null || user.id == null) {
            throw ExceptionUtil.createParamException("机构管理员错误");
        }
        Goods goods = commodityRemoteService.getCommodityGood(event.getCommodityId());
        if (goods.income <= 100) {
            throw ExceptionUtil.createParamException("结算金额必须大于￥1");
        }

        BaseResult<String> baseResult  = commodityRemoteService.settlementapply(event.getCommodityId(), uid, user.id,
                goods.income , intro);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        String batchnum = baseResult.getData();

        if (batchnum == null || batchnum.length() == 0) {
            throw ExceptionUtil.createParamException("结算错误,请重试");
        }

        EventOrder eventOrder = new EventOrder();
        eventOrder.setBatchnum(batchnum);
        eventOrder.setEventId(eventId);
        eventOrder.setCommodityId(event.getCommodityId());
        eventOrder.setCreateTime(System.currentTimeMillis());
        eventOrder.setModifyTime(System.currentTimeMillis());
        eventOrderMapper.insertSelective(eventOrder);

        return null;
    }


    /**
     * 获取机构管理员信息
     *
     * @param
     * @return
     */

    public User getAdminUser(Integer orgId) {

        Organization organization = orgPrivilegeRemoteService.getOrgById(orgId);
        if (organization == null || organization.admin == null) {
            return null;
        }
        User user = organization.admin;
        return user;
    }

    public Map<Integer,User> getAdminsUser(List<Integer> orgIds){
        Map<Integer,User> returnMap=new HashMap<>();
        List<Organization> orgList=orgPrivilegeRemoteService.getOrgsByIds(orgIds);
        for(Organization org:orgList){
            returnMap.put(org.id,org.admin);
        }
        return returnMap;
    }

    /**
     * 根据渠道的id列表获取渠道的信息列表
     * <p>
     * 功能用途：在app-server项目收件箱页面用来给列表接口获取渠道展示的名称
     *
     * @param req
     * @return
     */

    public OrderChannelListResultResp getInfoListByIds(OrderChannelListReq req) {
        OrderChannelListResultResp resultResp = new OrderChannelListResultResp();
        resultResp.list = new ArrayList<>();
        resultResp.map = new HashMap<>();
        if (!StringUtil.isEmpty(req.orgIds)) {
            OrgInfoExample example = new OrgInfoExample();
            example.createCriteria().andNewIdIn(req.orgIds);
            List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(example);
            for (OrgInfo orgInfo : orgInfos) {
                if (orgInfo != null) {
                    OrgListBeanResp resp = new OrgListBeanResp();
                    resp.orgId = orgInfo.getNewId();
                    resp.name = orgInfo.getName();
                    resultResp.list.add(resp);
                    resultResp.map.put(resp.orgId, resp);
                }
            }
            resultResp.count = resultResp.list.size();
        }
        return resultResp;

    }

    /**
     * 机构方查询自己的机构信息时调用
     *
     * @param req
     * @return
     */

    public OrgInfoResp getInfoById(OrderChannelReq req) {
        Integer uid = RequestHolder.getVerifiedUid();
        Organization organization = orgPrivilegeRemoteService.getOrgById(req.orgId);
        OrgInfoResp orgInfoResp = new OrgInfoResp();
        orgInfoResp.id = organization.id;
        orgInfoResp.name = organization.name;
        orgInfoResp.city = new CityResp();
        orgInfoResp.city.id = organization.cityId;
        orgInfoResp.city.name = organization.cityName;
        orgInfoResp.logo = organization.logo;
        orgInfoResp.address = organization.address;
        orgInfoResp.website = organization.website;
        orgInfoResp.intro = organization.intro;
        if (req.withRoles != null && req.withRoles == true) {
            orgInfoResp.userRole = orgPrivilegeRemoteService.getRole(uid, req.orgId);
        }
        orgInfoResp.auth = new OrgAuthResp();
        if (organization.authInfo == null || organization.authInfo.verifyStatus == null) {
            orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
        } else {
            orgInfoResp.auth.verifyStatus = organization.authInfo.verifyStatus;
            orgInfoResp.auth.reason = organization.authInfo.reason;
            if (!StringUtil.isEmpty(organization.authInfo.materials)) {
                orgInfoResp.auth.evidences = new ArrayList<>();
                EvidencesResp evidencesResp;
                for (OrganizationAuthMaterial material : organization.authInfo.materials) {
                    evidencesResp = new EvidencesResp();
                    evidencesResp.title = material.name;
                    evidencesResp.url = material.url;
                    orgInfoResp.auth.evidences.add(evidencesResp);
                }
            }
        }
        orgInfoResp._package = new PackageResp();
        if (organization.packages != null && !organization.packages.isEmpty()) {
            orgInfoResp._package.id = organization.packages.get(0).id;
            orgInfoResp._package.name = organization.packages.get(0).name;
        } else {
            orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
            orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
        }
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(req.orgId).andStateEqualTo((byte) 0);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (orgInfos == null || orgInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        OrgInfo orgInfo = orgInfos.get(0);
        //用户自己修改机构，一个月只能修改一次
        if (orgInfo.getUserModifyTime() != null
                && orgInfo.getUserModifyTime() != 0
                && System.currentTimeMillis() - orgInfo.getUserModifyTime() <
                cloudEventConfig.getOrg_revise_day() * Constant.DAY_LONG) {
            Double time = cloudEventConfig.getOrg_revise_day() -
                    Math.floor((System.currentTimeMillis() - orgInfo.getUserModifyTime()) / (Constant.DAY_LONG));
            orgInfoResp.reviseTimeTextTip = "名称1个月只能修改1次（" + time.intValue() + "天后可以重新修改），不建议频繁修改。";
            orgInfoResp.canRevise = SqlEnum.CanReviseType.CANT.getValue();
        } else {
            orgInfoResp.reviseTimeTextTip = "名称1个月只能修改1次，不建议频繁修改。";
            orgInfoResp.canRevise = SqlEnum.CanReviseType.CAN.getValue();
        }
        return orgInfoResp;
    }

    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param uid
     * @return
     */

    public Object getListByUid(Integer uid) {
        User user = new User();
        user.id = uid;
        User pageData = orgPrivilegeRemoteService.getOrgsByUid(user);
        PageData<OrgInfoResp> orgInfoRespPageData = new PageData<>();
        //如果没有，为用户创建一个默认的机构
        if (StringUtil.isEmpty(pageData.orgs)) {
            Organization organization = createDefaultOrg(uid);
            orgInfoRespPageData.count = 1;
            orgInfoRespPageData.list = new ArrayList<>();
            OrgInfoResp orgInfoResp;
            orgInfoResp = new OrgInfoResp();
            orgInfoResp.id = organization.id;
            orgInfoResp.logo = organization.logo;
            orgInfoResp.name = organization.name;
            orgInfoResp.auth = new OrgAuthResp();
            orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
            orgInfoResp._package = new PackageResp();
            orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
            orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
            orgInfoRespPageData.list.add(orgInfoResp);
        } else {
            orgInfoRespPageData.count = pageData.orgs.size();
            orgInfoRespPageData.list = new ArrayList<>();
            OrgInfoResp orgInfoResp;
            for (Organization organization : pageData.orgs) {
                orgInfoResp = new OrgInfoResp();
                orgInfoResp.id = organization.id;
                orgInfoResp.logo = organization.logo;
                orgInfoResp.name = organization.name;
                orgInfoResp.auth = new OrgAuthResp();
                if (organization.authInfo == null || organization.authInfo.verifyStatus == null) {
                    orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
                } else {
                    orgInfoResp.auth.verifyStatus = organization.authInfo.verifyStatus;
                }
                orgInfoResp._package = new PackageResp();
                if (organization.packages != null && !organization.packages.isEmpty()) {
                    orgInfoResp._package.id = organization.packages.get(0).id;
                    orgInfoResp._package.name = organization.packages.get(0).name;
                } else {
                    orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
                    orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
                }
                orgInfoRespPageData.list.add(orgInfoResp);
                addOrUpdateOrg(organization);
            }
        }
        return orgInfoRespPageData;
    }

    private void addOrUpdateOrg(Organization organization) {
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(organization.id);
        //本地保存一个
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setName(organization.name);
        orgInfo.setLogo(organization.logoNoImagePrefix);
        orgInfo.setWebsite(organization.website);
        orgInfo.setAddress(organization.address);
        int result = orgInfoMapper.updateByExampleSelective(orgInfo, orgInfoExample);
        if (result == 0) {
            orgInfo.setNewId(organization.id);
            orgInfo.setCreateTime(System.currentTimeMillis());
            orgInfo.setModifyTime(System.currentTimeMillis());
            orgInfoMapper.insertSelective(orgInfo);
        }
    }

    /**
     * 获取机构列表（运营后台使用）
     *
     * @param req
     * @return
     */

    public Object getOrgList(OrgPageReq req) {
        AuthPageData authResultListResp = orgPrivilegeRemoteService.getOrgList(req);
        OrgListResp orgListResp = new OrgListResp();
        if (authResultListResp != null) {
            orgListResp.verifyErrorCount = authResultListResp.verifyErrorCount;
            orgListResp.verifySuccessCount = authResultListResp.verifySuccessCount;
            orgListResp.waitVerifyCount = authResultListResp.waitVerifyCount;
            orgListResp.count = (int) authResultListResp.count;
            orgListResp.totalCount = authResultListResp.totalCount;
            List<OrgInfoResp> listResps = new ArrayList();
            if (authResultListResp.list != null && !authResultListResp.list.isEmpty()) {
                for (Organization authListResp : authResultListResp.list) {
                    OrgInfoResp orgInfoListResp = new OrgInfoResp();
                    orgInfoListResp.id = authListResp.orgId;
                    orgInfoListResp.address = authListResp.address;
                    orgInfoListResp.city = new CityResp();
                    orgInfoListResp.city.id = authListResp.cityId;
                    orgInfoListResp.city.name = authListResp.cityName;
                    orgInfoListResp.intro = authListResp.intro;
                    orgInfoListResp.logo = authListResp.logo;
                    orgInfoListResp.name = authListResp.name;
                    orgInfoListResp.website = authListResp.website;
                    UserResp userResp = new UserResp();
                    if (authListResp.admin != null) {
                        userResp.mobile = authListResp.admin.phone;
                        userResp.name = authListResp.admin.name;
                        userResp.uid = authListResp.admin.uid;
                    }
                    orgInfoListResp.user = userResp;

                    orgInfoListResp.auth = new OrgAuthResp();
                    if (authListResp.authInfo != null) {
                        if (authListResp.authInfo.applyTime != null) {
                            orgInfoListResp.auth.applyTime = authListResp.authInfo.applyTime.getTime();
                        }
                        if (authListResp.authInfo.verifyStatus == null) {
                            orgInfoListResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
                        } else {
                            orgInfoListResp.auth.verifyStatus = authListResp.authInfo.verifyStatus;
                        }
                        List<OrganizationAuthMaterial> materialList = authListResp.authInfo.materials;
                        if (materialList != null && !materialList.isEmpty()) {
                            List<EvidencesResp> list = new ArrayList<>();
                            for (OrganizationAuthMaterial material : materialList) {
                                EvidencesResp evidencesResp = new EvidencesResp();
                                evidencesResp.title = material.name;
                                evidencesResp.url = material.url;
                                list.add(evidencesResp);
                            }
                            orgInfoListResp.auth.evidences = list;
                        } else {
                            orgInfoListResp.auth.evidences = new ArrayList<>();
                        }
                        listResps.add(orgInfoListResp);
                    }
                }
            }
            orgListResp.list = listResps;
        }
        return orgListResp;
    }

    /**
     * 套餐管理列表（运营后台使用）
     *
     * @param req
     * @return
     */

    public Object getPackageList(OrgPackagePageReq req) {
        PageData<Organization> pageData =
                orgPrivilegeRemoteService.getPackageList(req);
        PageData<OrgInfoResp> orgPackagePageRespPageData = new PageData<>();
        if (pageData.getList() == null || pageData.getList().isEmpty()) {
            orgPackagePageRespPageData.list = new ArrayList<>();
            orgPackagePageRespPageData.count = 0;
            return orgPackagePageRespPageData;
        }
        OrgInfoResp orgInfoResp;
        orgPackagePageRespPageData.list = new ArrayList<>();
        for (Organization organization : pageData.list) {
            orgInfoResp = new OrgInfoResp();
            orgInfoResp.id = organization.orgId;
            orgInfoResp.name = organization.name;
            orgInfoResp.logo = organization.logo;
            orgInfoResp.intro = organization.intro;
            if (organization.admin != null) {
                orgInfoResp.user = new UserResp();
                orgInfoResp.user.uid = organization.admin.id;
                orgInfoResp.user.name = organization.admin.name;
                orgInfoResp.user.company = organization.admin.company;
                orgInfoResp.user.position = organization.admin.position;
                orgInfoResp.user.mobile = organization.admin.phone;
                orgInfoResp.user.avatar = organization.admin.avatar;
            }
            orgInfoResp._package = new PackageResp();
            if (organization.packages != null && !organization.packages.isEmpty()) {
                Package packageResp = organization.packages.get(0);
                orgInfoResp._package.id = packageResp.id;
                orgInfoResp._package.name = packageResp.name;
                if (packageResp.id != null && SqlEnum.PackageType.TYPE_ADVANCE.getId().intValue() == packageResp.id) {
                    orgInfoResp._package.style = SqlEnum.PackageType.TYPE_ADVANCE.getStyle();
                } else {
                    orgInfoResp._package.style = SqlEnum.PackageType.TYPE_BASE.getStyle();
                }
                orgInfoResp._package.openTime = packageResp.startTime;
            } else {
                orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
                orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
                orgInfoResp._package.style = SqlEnum.PackageType.TYPE_BASE.getStyle();
            }
            orgInfoResp.auth = new OrgAuthResp();
            if (organization.authInfo == null || organization.authInfo.verifyStatus == null) {
                orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
            } else {
                orgInfoResp.auth.verifyStatus = organization.authInfo.verifyStatus;
            }
            orgInfoResp.eventCount = (int) eventService.getAllEventCountByOrgId(organization.orgId);
            orgPackagePageRespPageData.list.add(orgInfoResp);
        }
        orgPackagePageRespPageData.count = pageData.count;
        return orgPackagePageRespPageData;
    }

    /**
     * 运营后台手动修改认证状态
     *
     * @param req
     */

    @Transactional
    public Object updateVerify(OrgPageReq req) {
        if (req.verifyStatus != EnumAuthSource.TYPE_FAILED_VERIFY.getValue() &&
                req.verifyStatus != EnumAuthSource.TYPE_SUCCESS_VERIFY.getValue()) {
            throw ExceptionUtil.createParamException("verifyStatus参数异常");
        }
        //未通过，必须传理由
        if (req.verifyStatus == EnumAuthSource.TYPE_FAILED_VERIFY.getValue()
                && StringUtil.isEmpty(req.reason)) {
            throw ExceptionUtil.createParamException("请输入未通过理由");
        }
        orgPrivilegeRemoteService.updateVerify(req);
        //操作成功，插入云活动消息
        saveSysMessage(req);
        //发送app消息
//        saveAppSms(req);
        return null;
    }

//    /**
//     * 发送app消息
//     * @param req
//     */
//    private void saveAppSms(OrgPageReq req) {
//        Integer fromUid = EnumIMType.ACTIVE.getValue();
//        Integer toUid = getAdminUid(req);
//        String content="";
//        if(req.verifyStatus.equals(EnumAuthSource.TYPE_SUCCESS_VERIFY.getValue())){
//            content="恭喜你，你的机构申请认证已通过";
//        }else if(req.verifyStatus.equals(EnumAuthSource.TYPE_FAILED_VERIFY.getValue())){
//            content="抱歉，你的机构申请认证未通过，请登录web查看原因";
//        }
//        smsRemoteService.sendAppMsg(content, fromUid, toUid);
//    }

    /**
     * 保存云活动平台系统消息
     *
     * @param req
     */
    @Transactional
    public void saveSysMessage(OrgPageReq req) {
        EventSysMessage message = new EventSysMessage();
        message.setEventId(0);
        message.setCreateTime(DateUtil.getNowDate().getTime());
        message.setModifyTime(DateUtil.getNowDate().getTime());
        message.setOperatorUid(req.adminUid);
        message.setIsread(SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getByteValue());
        message.setState(SqlEnum.SysMsgState.TYPE_NORMAL.getByteValue());
        message.setOrgId(req.orgId);
        message.setSendUid(getAdminUid(req));
        if (req.verifyStatus == EnumAuthSource.TYPE_FAILED_VERIFY.getValue()) {
            message.setConfirmType(SqlEnum.ConfirmType.TYPE_CHECK_FAIL.getByteValue());
            message.setReason(req.reason);
        } else if (req.verifyStatus == EnumAuthSource.TYPE_SUCCESS_VERIFY.getValue()) {
            message.setConfirmType(SqlEnum.ConfirmType.TYPE_CHECK_PASS.getByteValue());
        }
        eventSysMessageMapper.insertSelective(message);
        SysUserRelationReq relationReq = new SysUserRelationReq();
        relationReq.orgId = req.orgId;
        relationReq.messageId = message.getId();
        sysMsgService.insertRelation(relationReq);
    }

    /**
     * 拿到机构下管理员的uid
     *
     * @param req
     * @return
     */
    private Integer getAdminUid(OrgPageReq req) {
        Integer adminUid = 0;
        req.size = 10;
        req.page = 1;
        PageData<User> userPageData = orgPrivilegeRemoteService.getOrgMemberList(req);
        if (userPageData != null && userPageData.list != null) {
            for (User user : userPageData.list) {
                if (user.roles != null && !user.roles.isEmpty()) {
                    int roleType = user.roles.get(0).id;
                    if (roleType == cloudEventConfig.getDefault_admin_id()) {
                        adminUid = user.id;
                    }
                }
            }
        }
        return adminUid;
    }


    public Object updatePackage(OrgPackagePageReq req) {
        return orgPrivilegeRemoteService.updatePackage(req);
    }

    /**
     * 获取团队成员列表
     *
     * @param req
     * @return
     */

    public Object getOrgMemberList(OrgPageReq req) {

        PageData<User> userPageData = orgPrivilegeRemoteService.getOrgMemberList(req);

        OrgMemberDataResp orgMemberDataResp = new OrgMemberDataResp();

        List<MemberInfoResp> list = new ArrayList<>();
        if (userPageData != null && userPageData.list != null) {
            orgMemberDataResp.count = (int) userPageData.count;
            for (User user : userPageData.list) {
                MemberInfoResp memberInfoResp = new MemberInfoResp();
                memberInfoResp.createTime = user.joinTime;
                if (user.roles != null && !user.roles.isEmpty()) {
                    int roleType = user.roles.get(0).id;
                    if (roleType == cloudEventConfig.getDefault_admin_id()) {
                        memberInfoResp.roleStatus = 1;
                    } else if (roleType == cloudEventConfig.getDefault_normal_user_id()) {
                        memberInfoResp.roleStatus = 2;
                    }
                }
                UserResp userInfoResp = new UserResp();
                userInfoResp.avatar = user.avatar;
                userInfoResp.company = user.company;
                userInfoResp.name = user.name;
                userInfoResp.position = user.position;
                userInfoResp.uid = user.uid;
                if (user.account != null && user.uid == null) {
                    userInfoResp.uid = user.account.id;
                }

                memberInfoResp.user = userInfoResp;
                list.add(memberInfoResp);
            }
        }

        //根据createTime先排序
        Collections.sort(list, new Comparator<MemberInfoResp>() {
            public int compare(MemberInfoResp resp1, MemberInfoResp resp2) {
                Long createTime1 = resp1.createTime;
                Long createTime2 = resp2.createTime;
                return createTime2.compareTo(createTime1);
            }
        });

        //把管理员拿出来置顶
        MemberInfoResp newMem = null;
        for (MemberInfoResp respect : list) {
            if (respect.roleStatus.intValue() == 1) {
                newMem = respect;
            }
        }
        if (newMem != null) {
            list.remove(newMem);
            list.add(0, newMem);
        }
        orgMemberDataResp.list = list;


        return orgMemberDataResp;
    }

    /**
     * 批量删除团队成员
     *
     * @param req
     * @return
     */

    public Object deleteMemberBatch(MembersReq req, Integer uid) {
        orgPrivilegeRemoteService.deleteMemberBatch(req, uid);
        return null;
    }

    /**
     * 创建团队成员
     *
     * @param req
     * @return
     */

    public Object saveMember(MembersReq req, Integer uid) {
        OrgPageReq orgPageReq = new OrgPageReq();
        orgPageReq.orgId = req.orgId;
        if (orgPrivilegeRemoteService.getOrgMemberList(orgPageReq).count > 4) {
            throw ExceptionUtil.createParamException("成员数量不能超过5个");
        }
        orgPrivilegeRemoteService.createMember(req, uid);
        return null;
    }

    /**
     * 获得所有套餐信息
     */

    public Object getPackageInfo() {
        Integer sourceId = EnumSource.CLOUD_ACTIVITY.getValue();
        return orgPrivilegeRemoteService.getPackageInfo(sourceId);
    }


    public Object getModules(OrgPageReq req) {
        return orgPrivilegeRemoteService.getModules(req.orgId);
    }


    /**
     * 外部调用放放与内部调用方法分割线
     ******************************************************************************/
    /**
     * 机构管理员自己修改账号名称字段时的方法，一个月只能修改一次
     *
     * @param req
     * @return
     */
    @Transactional
    public Object updateByUserSelf(CreateOrUpdateOrgReq req) {
        //获取机构信息
        Organization organizationRemote = orgPrivilegeRemoteService.getOrgById(req.orgId);

        //用户自己修改机构的名称字段，一个月只能修改一次
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(req.orgId).andStateEqualTo((byte) 0);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (orgInfos == null || orgInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        OrgInfo orgInfo = orgInfos.get(0);
        if (organizationRemote != null && !req.name.equals(organizationRemote.name) &&
                orgInfo.getUserModifyTime() != null && orgInfo.getUserModifyTime() != 0
                && System.currentTimeMillis() - orgInfo.getUserModifyTime() <
                cloudEventConfig.getOrg_revise_day() * Constant.DAY_LONG) {
            throw ExceptionUtil.createParamException("机构名称1个月只能修改1次");
        }
        Organization organization = new Organization();
        organization.id = req.orgId;
        organization.name = req.name;
        organization.intro = req.intro;
        organization.logo = req.logo;
        if (req.city != null) {
            organization.cityId = req.city.id;
        }
        organization.address = req.address;
        organization.website = req.website;
        orgPrivilegeRemoteService.modifyOrg(organization);

        //更新时间
        OrgInfo org = new OrgInfo();
        org.setAddress(organization.address);
        org.setName(organization.name);
        org.setIntro(organization.intro);
        org.setLogo(organization.logo);
        org.setWebsite(organization.website);
        if (req.city != null) {
            org.setCityId(organization.cityId);
        }
        org.setModifyTime(System.currentTimeMillis());
        //名字改了修改认证状态
        if (orgInfo.getName() != null && !orgInfo.getName().equals(req.name)) {
            OrgPageReq orgPageReq = new OrgPageReq();
            orgPageReq.orgId = req.orgId;
            orgPageReq.verifyStatus = SqlEnum.OrgVerifyStatus.CHECK_NOT.getValue();
            orgPrivilegeRemoteService.updateVerify(orgPageReq);
            org.setUserModifyTime(System.currentTimeMillis());
        }
        OrgInfoExample orgInfoExample1 = new OrgInfoExample();
        orgInfoExample1.createCriteria().andNewIdEqualTo(req.orgId);
        orgInfoMapper.updateByExampleSelective(org, orgInfoExample1);
        return null;
    }


    public Organization createDefaultOrg(Integer uid) {
        User profile = userService.getUserInfoById(uid);
        Organization organization = new Organization();
        organization.packageId = SqlEnum.PackageType.TYPE_BASE.getId();
        organization.uid = uid;
        organization.intro = profile.name + "的机构";
        organization.name = profile.name + "的机构" + StringUtil.getRandomLetter(7);
        organization.logo = profile.avatar;
        return createOrg(organization);
    }


    @Transactional
    public Organization createOrg(Organization organization) {
        organization.roles = new ArrayList<>();
        //加入默认超级管理员
        Role role = new Role();
        role.id = cloudEventConfig.getDefault_admin_id();
        role.member = new Member();
        role.member.uid = organization.uid;
        role.member.joinTime = System.currentTimeMillis();
        organization.roles.add(role);
        //加入普通成员角色
        Role roleCommon = new Role();
        roleCommon.id = cloudEventConfig.getDefault_normal_user_id();
        organization.roles.add(roleCommon);
        //
        Integer orgId = orgPrivilegeRemoteService.addOrg(organization);
        organization.id = orgId;

        //本地保存一个
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setName(organization.name);
        orgInfo.setCreateTime(System.currentTimeMillis());
        orgInfo.setModifyTime(System.currentTimeMillis());
        orgInfo.setLogo(organization.logo);
        orgInfo.setWebsite(organization.website);
        orgInfo.setAddress(organization.address);
        orgInfo.setNewId(organization.id);
        int result = orgInfoMapper.insertSelective(orgInfo);
        if (result == 0) {
            throw ExceptionUtil.createParamException("创建机构失败");
        }
        return organization;
    }


    /**
     * 在运营后台创建账号时调用的方法
     *
     * @param req
     * @return
     */

    private OrgInfoResp createByOwn(CreateOrUpdateOrgReq req) {
        //如果是预注册用户，也不能添加机构账号，因为就算是添加了机构账号，此用户也不能登录，没哟意义
        User account = userService.getUserAccountByMobile(req.mobile);
        //'0:正常, 1:预注册'
        if (account.status == 1) {
            throw ExceptionUtil.createParamException("此用户为预注册用户，不能添加或修改");
        }
        Organization organization = new Organization();
        organization.uid = account.id;
        //运营后台创建机构，默认套餐是高级版的
        organization.packageId = SqlEnum.PackageType.TYPE_ADVANCE.getId();
        organization.name = req.name;
        organization.intro = req.intro;
        organization.logo = req.logo;
        organization.website = req.website;
        organization.address = req.address;
        if (req.city != null) {
            organization.cityId = req.city.id;
        }
        createOrg(organization);
        OrgInfoResp orgInfoResp;
        orgInfoResp = new OrgInfoResp();
        orgInfoResp.id = organization.id;
        orgInfoResp.name = organization.name;
        orgInfoResp.intro = organization.intro;
        orgInfoResp.logo = organization.logo;
        orgInfoResp.website = organization.website;
        orgInfoResp.auth = new OrgAuthResp();
        orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
        orgInfoResp._package = new PackageResp();
        orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
        orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
        return orgInfoResp;
    }

    /**
     * 用户自己创建机构
     *
     * @param req
     * @return
     */
    private OrgInfoResp createByUserSelf(CreateOrUpdateOrgReq req) {
        Integer uid = RequestHolder.getVerifiedUid();
        Organization organization1 = new Organization();
        organization1.uid = uid;
        //用户自己创建机构，默认套餐是基础版的
        organization1.packageId = SqlEnum.PackageType.TYPE_BASE.getId();
        organization1.name = req.name;
        organization1.intro = req.intro;
        organization1.logo = req.logo;
        organization1.website = req.website;
        organization1.address = req.address;
        if (req.city != null) {
            organization1.cityId = req.city.id;
        }
        createOrg(organization1);
        OrgInfoResp orgInfoResp;
        orgInfoResp = new OrgInfoResp();
        orgInfoResp.id = organization1.id;
        orgInfoResp.name = organization1.name;
        orgInfoResp.intro = organization1.intro;
        orgInfoResp.logo = organization1.logo;
        orgInfoResp.website = organization1.website;
        orgInfoResp.auth = new OrgAuthResp();
        orgInfoResp.auth.verifyStatus = EnumAuthSource.TYPE_DEFAULT.getValue();
        orgInfoResp._package = new PackageResp();
        orgInfoResp._package.id = SqlEnum.PackageType.TYPE_BASE.getId();
        orgInfoResp._package.name = SqlEnum.PackageType.TYPE_BASE.getValue();
        return orgInfoResp;

    }

    private Object updateByOwn(CreateOrUpdateOrgReq req) {
        Organization organization = orgPrivilegeRemoteService.getOrgById(req.orgId);
        if (organization.admin == null) {
            throw ExceptionUtil.createParamException("获取用户信息失败");
        }
        Organization organizationNew = new Organization();
        organizationNew.id = req.orgId;
        organizationNew.name = req.name;
        organizationNew.intro = req.intro;
        if (req.city != null) {
            organizationNew.cityId = req.city.id;
        }
        organizationNew.address = req.address;
        organizationNew.website = req.website;
        organizationNew.logo = req.logo;
        orgPrivilegeRemoteService.modifyOrg(organizationNew);

        //更新本地
        OrgInfo org = new OrgInfo();
        org.setAddress(organizationNew.address);
        org.setName(organizationNew.name);
        org.setIntro(organizationNew.intro);
        org.setLogo(organizationNew.logo);
        org.setWebsite(organizationNew.website);
        if (req.city != null) {
            org.setCityId(organizationNew.cityId);
        }
        org.setModifyTime(System.currentTimeMillis());
        OrgInfoExample orgInfoExample1 = new OrgInfoExample();
        orgInfoExample1.createCriteria().andNewIdEqualTo(req.orgId);
        orgInfoMapper.updateByExampleSelective(org, orgInfoExample1);

        //如果修改了手机号码，等同于更换超级管理员
        if (!req.mobile.equals(organization.admin.phone)) {
            User account = userService.getUserAccountByMobile(req.mobile);
            //'0:正常, 1:预注册'
            if (account.status == 1) {
                throw ExceptionUtil.createParamException("此用户为预注册用户，不能添加或修改");
            }
            orgPrivilegeRemoteService.refreshOrgMember(req.orgId, account.id);
        }


        return null;
    }
}
