package com.welian.service;

import com.welian.beans.BpResp;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.CommonFrontParams;
import com.welian.beans.cloudevent.FrontParams;
import com.welian.beans.cloudevent.ProjectScreenParams;
import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.beans.cloudevent.http.HttpProjectCreateOrUpdateReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectFeedbackBeanListResp;
import com.welian.beans.cloudevent.project.ProjectFeedbackResultResp;
import com.welian.beans.cloudevent.project.ProjectFinancingInfoReq;
import com.welian.beans.cloudevent.project.ProjectInfoFromOtherServiceRep;
import com.welian.beans.cloudevent.project.ProjectListBeanResp;
import com.welian.beans.cloudevent.project.ProjectListResultResp;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.project.ProjectScreenResponseCondition;
import com.welian.beans.cloudevent.project.ThreeProjectInfoResp;
import com.welian.beans.cloudevent.user.EventUserSignUpResp;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import org.sean.framework.exception.CodeException;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordProjectMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.mapper.ProjectFeedbackBackupMapper;
import com.welian.mapper.ProjectListMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordProject;
import com.welian.pojo.EventRecordProjectExample;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.pojo.ProjectFeedbackBackup;
import com.welian.pojo.ProjectFeedbackBackupExample;
import com.welian.pojo.ProjectList;
import com.welian.service.impl.StateProjectModuleServiceImpl;
import com.welian.service.task.ScheduledTask;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectFeedbackBackupMapper projectFeedbackBackupMapper;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventRecordProjectMapper eventRecordProjectMapper;
    @Autowired
    private ProjectListMapper projectListMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private ScheduledTask scheduledTask;
    @Autowired
    private BpRemoteService bpRemoteService;
    @Autowired
    private IndustryService industryService;

    /**
     * 通过api获取项目列表
     *
     * @param req
     * @return
     */
    public Object getProjectListByAppKey(AppKeyReq req) {
        if (StringUtil.isEmpty(req.token)) {
            throw ExceptionUtil.createParamException("token为空");
        }

        return null;
    }

    /**
     * 根据渠道id获取项目列表
     *
     * @param req
     * @return
     */
    public Object getProjectList(OrderChannelReq req, ProjectScreenResponseCondition projectScreenResponseCondition) {
        ProjectListResultResp resultResp = new ProjectListResultResp();
        //取出的项目一定是活动还存在的
        EventExample eventExample = new EventExample();
        List<Byte> bytes = new ArrayList<>();
        bytes.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getByteValue());
        bytes.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue());
        eventExample.createCriteria().andOrgIdEqualTo(req.orgId).andStateIn(bytes);
        List<Event> events = eventMapper.selectByExample(eventExample);
        if (events == null || events.isEmpty()) {
            resultResp.list = new ArrayList<>();
            resultResp.count = 0;
            return resultResp;
        }
        String eventIds = "";
        for (int i = 0, size = events.size(); i < size; i++) {
            eventIds = eventIds + events.get(i).getId();
            if (i != size - 1) {
                eventIds = eventIds + ",";
            }
        }
        String condition = null;
        try {
            condition = getScreenCondition(req);
        } catch (CodeException e) {
            e.printStackTrace();
            resultResp.list = new ArrayList<>();
            resultResp.count = 0;
            return resultResp;
        }
        List<ProjectListBeanResp> projectListBeanResps = getList(eventIds, req, condition,
                projectScreenResponseCondition);
        int count = projectListMapper.selectProjectCount(eventIds, req.orgId, condition);
        resultResp.list = projectListBeanResps;
        resultResp.count = count;
        return resultResp;
    }

    /**
     * 拼接sql
     * @param req
     * @return
     * @throws CodeException
     */
    private String getScreenCondition(OrderChannelReq req) throws CodeException {
        StringBuffer sql = new StringBuffer();
        if ("".equals(req.keyword)) {
            req.keyword = null;
        }
        switch (req.type) {
            case 1://根据项目名称
                if (!StringUtil.isEmpty(req.keyword)) {
                    sql.append(" and ps.name like '%");
                    sql.append(req.keyword);
                    sql.append("%'");
                }
                break;
            case 2://根据用户名称
                if (!StringUtil.isEmpty(req.keyword)) {
                    List<EventRecordUser> users = eventRecordUserMapper.selectByName(req.keyword, req.orgId);
                    if (users.size() != 0) {
                        String keyword = "";
                        for (int i = 0, size = users.size(); i < size; i++) {
                            if (users.get(i) != null && NumberUtil.isValidId(users.get(i).getEventRecordId())) {
                                keyword = keyword + users.get(i).getEventRecordId();
                            }
                            if (i != size - 1) {
                                keyword = keyword + ",";
                            }
                        }
                        sql.append(" and ps.event_record_id in ( ");
                        sql.append(keyword);
                        sql.append(" ) ");
                    } else {
                        throw ExceptionUtil.createParamException("找不到报名用户");
                    }
                }
                break;
            case 3://根据备注
                if (!StringUtil.isEmpty(req.keyword)) {
                    sql.append(" and ps.remark like '%");
                    sql.append(req.keyword);
                    sql.append("%'");
                }
                break;
        }
        //领域条件
        if (NumberUtil.isValidId(req.industryId)) {
            sql.append(" and ps.industry_id = ");
            sql.append(req.industryId);
        }
        //城市条件
        if (req.cityId != null) {
            if (req.cityId == -2) {

            } else if (req.cityId == -1) {
                sql.append(" and ps.city_id not in ( ");
                for (int i = 0, count = Constant.CONSTANT_CITY_ID.length; i < count; i++) {
                    if (Constant.CONSTANT_CITY_ID[i] > 0) {
                        sql.append(Constant.CONSTANT_CITY_ID[i]);
                        sql.append(",");
                    }
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(" ) ");
            } else {
                sql.append(" and ps.city_id = ");
                sql.append(req.cityId);
            }

        }
        if (req.stageId != null) {
            if (req.stageId == -2) {

            } else if (req.stageId == -1) {
                sql.append(" and ps.project_stage not in ( ");
                for (int i = 0, count = Constant.CONSTANT_STAGE_ID_SCREEN.length; i < count; i++) {
                    if (Constant.CONSTANT_STAGE_ID_SCREEN[i] > 0) {
                        sql.append(Constant.CONSTANT_STAGE_ID_SCREEN[i]);
                        sql.append(",");
                    }
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(" ) ");
            } else {
                sql.append(" and ps.project_stage = ");
                sql.append(req.stageId);
            }

        }
        //星级
        if (req.starId != null) {
            if (req.starId == -2) {

            } else {
                sql.append(" and ps.star_level = ");
                sql.append(req.starId);
            }
        }
        //
        if (NumberUtil.isValidId(req.days)) {
            if (req.days == -2) {

            } else {
                Long nowTime = System.currentTimeMillis();
                Long time = DateUtil.convert2long(DateUtil.convert2String(nowTime - req.days * Constant.DAY_LONG,
                        DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
                sql.append(" and ( ps.sign_up_time between ");
                sql.append(time);
                sql.append(" and ");
                sql.append(nowTime);
                sql.append(" ) ");
            }
        }
        System.out.print(sql.toString());
        if (sql.length() > 0) {
            return sql.toString();
        } else {
            return null;
        }
    }

    public ProjectListBeanResp getProjectInfoByIdSimple(Integer signUpId) {
        ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
        projectBackupInfoExample.createCriteria().andEventRecordIdEqualTo(signUpId);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(projectBackupInfoExample);
        if (projectBackupInfos == null || projectBackupInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("获取项目失败");
        }
        ProjectBackupInfo projectBackupInfo = projectBackupInfos.get(0);
        ProjectListBeanResp projectListBeanResp = new ProjectListBeanResp();
        //返回项目信息
        ProjectResp projectResp = new ProjectResp();
        projectResp.pid = projectBackupInfo.getPid();
        projectResp.name = projectBackupInfo.getName();
        projectResp.intro = projectBackupInfo.getIntro();
        projectResp.cityId = projectBackupInfo.getCityId();
        projectResp.cityName = projectBackupInfo.getCityName();
        projectResp.industryId = projectBackupInfo.getIndustryId();
        projectResp.industryName = projectBackupInfo.getIndustryName();
        projectResp.stage = projectBackupInfo.getProjectStage();
        projectResp.amount = projectBackupInfo.getProjectAmount();
        projectResp.amountUnit = projectBackupInfo.getProjectAmountUnit();
        projectResp.share = projectBackupInfo.getProjectShare();
        projectResp.bp = getBpPara(projectBackupInfo);
        projectListBeanResp.project = projectResp;
        projectListBeanResp.signUpId = signUpId;
        return projectListBeanResp;
    }

    private BPResp getBpPara(ProjectBackupInfo projectBackupInfo) {
        BPResp bpResp = new BPResp();
        if (projectBackupInfo.getProjectSignBpId() != null) {
            bpResp.bpId = projectBackupInfo.getProjectSignBpId();
            bpResp.bpName = projectBackupInfo.getProjectSignBpName();
            bpResp.bpUrl = "";
            if (!StringUtil.isEmpty(projectBackupInfo.getProjectSignBpUrl())) {
                bpResp.bpUrl = cloudEventConfig.getBp_prefix() + projectBackupInfo.getProjectSignBpUrl();
            }
        }
        return bpResp;
    }

    public Map<Integer, ProjectListBeanResp> getProjectInfoMapByIdsSimple(List<Integer> signUpIds) {
        ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
        projectBackupInfoExample.createCriteria().andEventRecordIdIn(signUpIds);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(projectBackupInfoExample);
        if (projectBackupInfos == null || projectBackupInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("获取项目失败");
        }
        //recordId,ProjectListBeanResp
        Map<Integer, ProjectListBeanResp> returnMap = new HashMap<>();
        ProjectListBeanResp projectListBeanResp;
        ProjectResp projectResp;
        for (ProjectBackupInfo projectBackupInfo : projectBackupInfos) {
            projectListBeanResp = new ProjectListBeanResp();
            //返回项目信息
            projectResp = new ProjectResp();
            projectResp.pid = projectBackupInfo.getPid();
            projectResp.name = projectBackupInfo.getName();
            projectResp.intro = projectBackupInfo.getIntro();
            projectResp.cityId = projectBackupInfo.getCityId();
            projectResp.cityName = projectBackupInfo.getCityName();
            projectResp.industryId = projectBackupInfo.getIndustryId();
            projectResp.industryName = projectBackupInfo.getIndustryName();
            projectResp.stage = projectBackupInfo.getProjectStage();
            projectResp.amount = projectBackupInfo.getProjectAmount();
            projectResp.amountUnit = projectBackupInfo.getProjectAmountUnit();
            projectResp.share = projectBackupInfo.getProjectShare();
            projectResp.bp = getBpPara(projectBackupInfo);
            projectListBeanResp.project = projectResp;
            projectListBeanResp.signUpId = projectBackupInfo.getEventRecordId();
            returnMap.put(projectBackupInfo.getEventRecordId(), projectListBeanResp);
        }
        return returnMap;
    }


    /**
     * 根据报名id获取项目详情
     *
     * @param signUpId
     * @return
     */
    public ProjectListBeanResp getProjectInfoById(Integer signUpId) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(signUpId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("获取报名记录失败");
        }
        Event event = eventMapper.selectByPrimaryKey(eventRecord.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());

        EventRecordProjectExample eventRecordProjectExample = new EventRecordProjectExample();
        eventRecordProjectExample.createCriteria().andEventRecordIdEqualTo(signUpId);
        List<EventRecordProject> eventRecordProjects = eventRecordProjectMapper.selectByExample
                (eventRecordProjectExample);
        if (StringUtil.isEmpty(eventRecordProjects)) {
            throw ExceptionUtil.createParamException("获取报名记录失败");
        }
        EventRecordProject eventRecordProject = eventRecordProjects.get(0);

        ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
        projectBackupInfoExample.createCriteria().andEventRecordIdEqualTo(signUpId);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(projectBackupInfoExample);
        if (projectBackupInfos == null || projectBackupInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("获取项目失败");
        }
        ProjectBackupInfo projectBackupInfo = projectBackupInfos.get(0);
        ProjectListBeanResp projectListBeanResp = new ProjectListBeanResp();
        //返回项目信息
        ProjectResp projectResp = new ProjectResp();
        projectResp.pid = projectBackupInfo.getPid();
        projectResp.name = projectBackupInfo.getName();
        projectResp.intro = projectBackupInfo.getIntro();
        if (!StringUtil.isEmpty(projectBackupInfo.getLogo())) {
            projectResp.logo = cloudEventConfig.getImage_prefix() + projectBackupInfo.getLogo();
        }
        projectResp.cityId = projectBackupInfo.getCityId();
        projectResp.cityName = projectBackupInfo.getCityName();
        projectResp.industryId = projectBackupInfo.getIndustryId();
        projectResp.industryName = projectBackupInfo.getIndustryName();
        projectListBeanResp.project = projectResp;
        //返回bp信息
        BPResp bpResp = new BPResp();
        bpResp.bpId = projectBackupInfo.getProjectSignBpId();
        bpResp.bpName = projectBackupInfo.getProjectSignBpName();
        if (!StringUtil.isEmpty(projectBackupInfo.getProjectSignBpUrl())) {
            bpResp.bpUrl = cloudEventConfig.getBp_prefix() + projectBackupInfo.getProjectSignBpUrl();
        }
        projectResp.bp = bpResp;

        ProjectFeedbackBackupExample example = new ProjectFeedbackBackupExample();
        example.createCriteria().andEventRecordIdEqualTo(signUpId).andStateEqualTo(1);
        int feedback = (int) projectFeedbackBackupMapper.countByExample(example);
        example = new ProjectFeedbackBackupExample();
        example.createCriteria().andEventRecordIdEqualTo(signUpId).andStateEqualTo(2);
        int interview = (int) projectFeedbackBackupMapper.countByExample(example);

        projectListBeanResp.investorFeedbackCount = feedback + interview;
        projectListBeanResp.investorInterviewCount = interview;
        projectListBeanResp.investorReceiveCount = eventRecordProject.getDeliveryCount();
        projectListBeanResp.orgId = event.getOrgId();
        projectListBeanResp.signUpId = eventRecordProject.getEventRecordId();

        if (eventStateProject.getIsOpenFinancing() != null) {
            projectListBeanResp.isOpenFinancingService = (int) eventStateProject.getIsOpenFinancing();
        }

        if (eventRecordProject.getStarLevel() != null) {
            projectListBeanResp.star = (int) eventRecordProject.getStarLevel();
        } else {
            projectListBeanResp.star = 0;
        }
        projectListBeanResp.remark = eventRecordProject.getRemark();
        projectListBeanResp.eventType = event.getTemplateId();
        return projectListBeanResp;
    }

    /**
     * 对报名项目进行操作
     *
     * @param req
     * @return
     */
    public Object operateToProject(OrderChannelReq req) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(req.signUpId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("报名信息不存在");
        }
        if (eventRecord.getOrgId() != req.orgId.intValue()) {
            throw ExceptionUtil.createNoPriviligeException("你无权查看此数据");
        }
        EventRecordProjectExample example = new EventRecordProjectExample();
        example.createCriteria().andEventRecordIdEqualTo(req.signUpId);
        EventRecordProject eventRecordProject = new EventRecordProject();
        if (req.star != null && req.star < 6) {
            eventRecordProject.setStarLevel((byte) ((int) req.star));
        }
        eventRecordProject.setRemark(req.remark);
        int result = eventRecordProjectMapper.updateByExampleSelective(eventRecordProject, example);
        if (result == 0) {
            throw ExceptionUtil.createParamException("更新失败");
        }
        return null;
    }

    /**
     * 获取反馈列表
     *
     * @param req
     * @return
     */
    public Object getFeedbackList(OrderChannelReq req) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(req.signUpId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("报名信息不存在");
        }
        if (eventRecord.getOrgId().equals(req.orgId)) {
            throw ExceptionUtil.createNoPriviligeException("你无权查看此数据");
        }
        List<ProjectFeedbackBackup> projectFeedbackBackups = projectFeedbackBackupMapper.getFeedbackList
                (req.signUpId, PagingUtil.getStart(req.page, req.size), req.size);
        List<ProjectFeedbackBeanListResp> projectFeedbackBeanListResps = new ArrayList();
        if (projectFeedbackBackups == null || projectFeedbackBackups.isEmpty()) {
            return projectFeedbackBeanListResps;
        }
        List<Integer> investorUids = new ArrayList<>();
        for (ProjectFeedbackBackup feedbackBackup : projectFeedbackBackups) {
            investorUids.add(feedbackBackup.getInvestorId());
        }
        //批量查询投资人的信息列表
        Map<Integer, User> map = userService.getUserInfoListByIds(investorUids);

        ProjectFeedbackBeanListResp projectFeedbackBeanListResp;
        for (ProjectFeedbackBackup projectFeedbackBackup : projectFeedbackBackups) {
            projectFeedbackBeanListResp = new ProjectFeedbackBeanListResp();
            projectFeedbackBeanListResp.feedbackContent = "";
            //反馈的状态，2.9.17.1之后只有待反馈和已回复这两种状态了
            projectFeedbackBeanListResp.feedbackState = projectFeedbackBackup.getState();
            //如果是已回复或者已约谈，则展示反馈的时间，反之展示投递的时间
            if (SqlEnum.ProjectFeedbackState.TYPE_ANSWER.getValue().equals(projectFeedbackBackup.getState()) ||
                    SqlEnum.ProjectFeedbackState.TYPE_INTERVIEW.getValue().equals(projectFeedbackBackup.getState())) {
                projectFeedbackBeanListResp.time = projectFeedbackBackup.getFeedbackTime();
            } else {
                projectFeedbackBeanListResp.time = projectFeedbackBackup.getOrderTime();

            }
            //返回用户信息
            if (map.containsKey(projectFeedbackBackup.getInvestorId())) {
                UserResp userResp = new UserResp();
                User profile = map.get(projectFeedbackBackup.getInvestorId());
                userResp.uid = profile.uid;
                userResp.name = profile.name;
                userResp.company = profile.company;
                userResp.position = profile.position;
                userResp.avatar = cloudEventConfig.getImage_prefix() + profile.avatar;
                userResp.investorAuth = profile.authStatus;
                projectFeedbackBeanListResp.investor = userResp;
            }
            projectFeedbackBeanListResps.add(projectFeedbackBeanListResp);
        }
        ProjectFeedbackResultResp resultResp = new ProjectFeedbackResultResp();
        resultResp.list = projectFeedbackBeanListResps;
        return resultResp;
    }

    /**
     * 获取对项目的反馈列表
     *
     * @param req
     * @return
     */
    public Object getThreeProject(OrderChannelReq req) {
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(req.signUpId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("报名信息不存在");
        }
        if (eventRecord.getOrgId() != req.orgId.intValue()) {
            throw ExceptionUtil.createNoPriviligeException("你无权查看此数据");
        }
        ThreeProjectInfoResp threeProjectInfoResp = new ThreeProjectInfoResp();
        ProjectListBeanResp currentProject = getProjectInfoById(req.signUpId);
        if (currentProject != null) {
            threeProjectInfoResp.currentProject = currentProject;
            EventRecord lastSignUpProjectInfo = eventRecordMapper.getLastRecordInfo(req.signUpId, currentProject.orgId);
            EventRecord nextSignUpProjectInfo = eventRecordMapper.getNextRecordInfo(req.signUpId, currentProject.orgId);
            if (lastSignUpProjectInfo != null) {
                threeProjectInfoResp.preProject = getProjectInfoById(lastSignUpProjectInfo.getId());
            }
            if (nextSignUpProjectInfo != null) {
                threeProjectInfoResp.nextProject = getProjectInfoById(nextSignUpProjectInfo.getId());
            }
        }
        return threeProjectInfoResp;
    }

    /**
     * 外部调用放放与内部调用方法分割线
     ******************************************************************************/

    /**
     * 获取收到的项目列表
     *
     * @param req
     * @param projectScreenResponseCondition
     * @return
     */
    private List<ProjectListBeanResp> getList(String eventIds, OrderChannelReq req, String condition,
                                              ProjectScreenResponseCondition projectScreenResponseCondition) {
        List<ProjectListBeanResp> projectListBeanRespList = new ArrayList<ProjectListBeanResp>();
        List<ProjectList> projectLists = projectListMapper.selectProject(eventIds, req.orgId, PagingUtil.getStart(req
                        .page, req.size),
                req.size, condition);
        if (projectLists == null || projectLists.isEmpty()) {
            return projectListBeanRespList;
        }
        List<Integer> recordIds = new ArrayList<>();

        for (ProjectList projectList : projectLists) {
            recordIds.add(projectList.getEventRecordId());
        }
        Map<Integer, EventRecordUser> eventUserMap = new HashMap<>();
        if (!StringUtil.isEmpty(recordIds)) {
            EventRecordUserExample userExample = new EventRecordUserExample();
            userExample.createCriteria().andEventRecordIdIn(recordIds);
            List<EventRecordUser> recordUsers = eventRecordUserMapper.selectByExample(userExample);
            for (EventRecordUser recordUser : recordUsers) {
                eventUserMap.put(recordUser.getEventRecordId(), recordUser);
            }
        }
        ProjectListBeanResp projectListBeanResp;
        for (ProjectList projectList : projectLists) {
            projectListBeanResp = new ProjectListBeanResp();
            //返回项目信息
            ProjectResp projectResp = new ProjectResp();
            if (NumberUtil.isValidId(projectList.getProjectAmount())) {
                projectResp.amount = projectList.getProjectAmount();
            }
            if (projectList.getProjectAmountUnit() != null && projectList.getProjectAmountUnit() != 0) {
                projectResp.amountUnit = projectList.getProjectAmountUnit();
            }
            if (projectList.getProjectShare() != null && projectList.getProjectShare() != 0) {
                projectResp.share = projectList.getProjectShare();
            }
            if (projectList.getProjectStage() != null && projectList.getProjectStage() != -1) {
                projectResp.stage = projectList.getProjectStage();
            }
            projectResp.pid = projectList.getPid();
            projectResp.name = projectList.getName();
            projectResp.intro = projectList.getIntro();
            projectResp.cityId = projectList.getCityId();
            projectResp.cityName = projectList.getCityName();
            projectResp.industryId = projectList.getIndustryId();
            projectResp.industryName = projectList.getIndustryName();
            projectListBeanResp.project = projectResp;
            //返回bp信息
            BPResp bpResp = new BPResp();
            bpResp.bpId = projectList.getProjectSignBpId();
            bpResp.bpName = projectList.getProjectSignBpName();
            if (!StringUtil.isEmpty(projectList.getProjectSignBpUrl())) {
                bpResp.bpUrl = cloudEventConfig.getBp_prefix() + projectList.getProjectSignBpUrl();
            }
            projectResp.bp = bpResp;

            //获取用户信息
            if (eventUserMap.containsKey(projectList.getEventRecordId())) {
                UserResp userResp = new UserResp();
                EventRecordUser user = eventUserMap.get(projectList.getEventRecordId());
                userResp.uid = user.getUid();
                userResp.name = user.getName();
                userResp.company = user.getCompany();
                userResp.position = user.getPosition();
                if (projectScreenResponseCondition.needProjectCreateUserAvatar) {
                    userResp.avatar = "";
                }
                userResp.mobile = user.getPhone();
                projectListBeanResp.user = userResp;
            }
            ProjectFeedbackBackupExample example = new ProjectFeedbackBackupExample();
            example.createCriteria().andEventRecordIdEqualTo(projectList.getEventRecordId()).andStateEqualTo(1);
            int feedback = (int) projectFeedbackBackupMapper.countByExample(example);
            example = new ProjectFeedbackBackupExample();
            example.createCriteria().andEventRecordIdEqualTo(projectList.getEventRecordId()).andStateEqualTo(2);
            int interview = (int) projectFeedbackBackupMapper.countByExample(example);

            if (projectScreenResponseCondition.needFeedbackCount) {
                projectListBeanResp.investorFeedbackCount = feedback + interview;
                projectListBeanResp.investorInterviewCount = interview;
                projectListBeanResp.investorReceiveCount = projectList.getDeliveryCount();
            }

            projectListBeanResp.activitySignUpTime = projectList.getSignUpTime();

            if (projectScreenResponseCondition.needSignUpId) {
                projectListBeanResp.signUpId = projectList.getEventRecordId();
            }

            if (projectScreenResponseCondition.needStar) {
                if (projectList.getStarLevel() != null) {
                    projectListBeanResp.star = (int) projectList.getStarLevel();
                } else {
                    projectListBeanResp.star = 0;
                }
            }
            projectListBeanResp.remark = projectList.getRemark();
            ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(projectList.getExtensionLinkId());
            if (extensionLink != null) {
                Event event = eventMapper.selectByPrimaryKey(extensionLink.getEventId());
                if (event != null) {
                    projectListBeanResp.sourceActivityName = event.getTitle();
                    projectListBeanResp.sourceLinkName = extensionLink.getLinkName();
                }
            }
            projectListBeanRespList.add(projectListBeanResp);
        }
        return projectListBeanRespList;
    }


    public Object task() {
        scheduledTask.taskDoFeedback();
        return null;
    }


    /**
     * App中bp-delver接口
     ******************************************************************************/


    /**
     * 创建或修改项目,用于创建或修改详细版的项目时需要调用的接口
     * 改为调用bp-delver接口
     *
     * @param req
     * @return
     */
    public ProjectInfoFromOtherServiceRep createOrUpdateDetailed(HttpProjectCreateOrUpdateReq req) {

        checkReqForDetail(req);
        return bpRemoteService.createOrUpdateToChooseInvestors(req);
    }


    /**
     * 创建或修改项目,用于创建或修改简单版的项目时需要调用的接口
     * 改为调用bp-deliver接口
     *
     * @param req
     * @return
     */
    public ProjectInfoFromOtherServiceRep createOrUpdateSimple(HttpProjectCreateOrUpdateReq req) {
        checkReq(req);
        return bpRemoteService.createOrUpdateToChooseInvestors(req);
    }


    private void checkReqForDetail(HttpProjectCreateOrUpdateReq req) {
        checkReq(req);
        checkFinancingInfo(req.financingInfo);
    }


    /**
     * 验证入参
     * @param req
     */
    private void checkReq(HttpProjectCreateOrUpdateReq req) {
        if (req.extensionLinkId == null) {
            throw ExceptionUtil.createParamException("推广链接参数校验失败");
        }
        if (NumberUtil.isInValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数校验失败");
        }
        if (req.user == null) {
            throw ExceptionUtil.createParamException("用户参数异常");
        }
        if (req.project == null) {
            throw ExceptionUtil.createParamException("请填写项目信息");
        }
        if (req.bp == null) {
            throw ExceptionUtil.createParamException("请上传BP信息");
        }
        if (req.type.equals(1)) {
            if (req.bp.bpId == null || req.bp.bpId == 0) {
                if (req.bp.bpSize == null || StringUtil.isEmpty(req.bp.bpName) || StringUtil.isEmpty(req.bp.bpUrl)) {
                    throw ExceptionUtil.createParamException("请上传BP信息");
                }
            }
        }
    }
    /**
     * 验证融资信息
     * @param financingInfo
     */
    private void checkFinancingInfo(ProjectFinancingInfoReq financingInfo) {

        if (financingInfo == null || financingInfo.stage == null || financingInfo.stage < 0 ||
                financingInfo.amount == null || financingInfo.amount <= 0 ||
                financingInfo.amountUnit == null || financingInfo.amountUnit <= 0) {
            throw ExceptionUtil.createParamException("请填写融资信息");
        }

        if (financingInfo.share == null || financingInfo.share <= 0 || financingInfo.share > 100) {
            throw ExceptionUtil.createParamException("出让股份应为0到100之间");
        }
    }


    /**
     * 从本地库获取项目信息
     */
    public List<EventUserSignUpResp> getLocalSimpleProjectsInfo(List<EventRecord> eventRecords) {

        List<EventUserSignUpResp> response = new ArrayList<>();
        if (StringUtil.isEmpty(eventRecords)) {
            return response;
        }
        List<Integer> eventRecordIds = new ArrayList<>();
        Map<Integer, EventRecord> eventRecordMap = new HashMap<>();
        for (EventRecord eventRecord : eventRecords) {
            eventRecordIds.add(eventRecord.getId());
            eventRecordMap.put(eventRecord.getId(), eventRecord);
        }
        ProjectBackupInfoExample example = new ProjectBackupInfoExample();
        example.createCriteria().andEventRecordIdIn(eventRecordIds);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(example);
        EventUserSignUpResp userSignUpResp;
        for (ProjectBackupInfo projectBackupInfo : projectBackupInfos) {
            userSignUpResp = new EventUserSignUpResp();
            userSignUpResp.name = projectBackupInfo.getName();
            userSignUpResp.signUpState = eventRecordMap.get(projectBackupInfo.getEventRecordId()).getState().intValue();
            userSignUpResp.signUpTime = eventRecordMap.get(projectBackupInfo.getEventRecordId()).getCreateTime();
            userSignUpResp.url = eventRecordMap.get(projectBackupInfo.getEventRecordId()).getTicketUrl();
            userSignUpResp.payState = ParamEnum.PayState.PAY_YES.getValue();
            response.add(userSignUpResp);
        }
        return response;
    }

    /**
     * 根据报名记录id从bp服务获取bp信息
     * @param eventRecordId
     * @return
     */
    public BpResp getRemoteProjct(Integer eventRecordId) {
        ProjectBackupInfoExample example = new ProjectBackupInfoExample();
        example.createCriteria().andEventRecordIdEqualTo(eventRecordId);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(example);
        if (StringUtil.isEmpty(projectBackupInfos)) {
            throw ExceptionUtil.createParamException("projectBackupInfo不存在");
        }
        return bpRemoteService.getBpDetailById(projectBackupInfos.get(0).getPid());

    }


    public ProjectScreenParams getProjectScreenParams() {
        ProjectScreenParams projectScreenParams = new ProjectScreenParams();
        List<CommonFrontParams> commonFrontParams = new ArrayList<>();
        List<CommonFrontParams> commonInnerFrontParams = new ArrayList<>();
        //添加领域列表
        CommonFrontParams commonOuterParam = new CommonFrontParams();
        commonOuterParam.list = new ArrayList<>();
        commonOuterParam.name = "industry";
        commonOuterParam.title = "项目领域";
        FrontParams params;
        List<IndustryService.Industry> industryList = industryService.getAllFirstIndustryList();
        for (int i = 0, count = industryList.size(); i < count; i++) {
            params = new FrontParams();
            params.id = industryList.get(i).id;
            params.name = industryList.get(i).name;
            commonOuterParam.list.add(params);
        }
        commonInnerFrontParams.add(commonOuterParam);
        //添加阶段列表
        CommonFrontParams stageOuterParam = new CommonFrontParams();
        stageOuterParam.list = new ArrayList<>();
        stageOuterParam.name = "stage";
        stageOuterParam.title = "轮次";
        FrontParams stageParams;
        for (int i = 0, count = Constant.CONSTANT_STAGE_ID_SCREEN.length; i < count; i++) {
            stageParams = new FrontParams();
            stageParams.id = Constant.CONSTANT_STAGE_ID_SCREEN[i];
            stageParams.name = Constant.CONSTANT_STAGE_NAME_SCREEN[i];
            stageOuterParam.list.add(stageParams);
        }
        commonInnerFrontParams.add(stageOuterParam);

        //添加城市列表
        CommonFrontParams cityOuterParam = new CommonFrontParams();
        cityOuterParam.list = new ArrayList<>();
        cityOuterParam.name = "city";
        cityOuterParam.title = "城市";
        FrontParams cityParams;
        for (int i = 0, count = Constant.CONSTANT_CITY_ID.length; i < count; i++) {
            cityParams = new FrontParams();
            cityParams.id = Constant.CONSTANT_CITY_ID[i];
            cityParams.name = Constant.CONSTANT_CITY_NAME[i];
            cityOuterParam.list.add(cityParams);
        }
        commonFrontParams.add(cityOuterParam);

        //添加星级列表
        CommonFrontParams starOuterParam = new CommonFrontParams();
        starOuterParam.list = new ArrayList<>();
        starOuterParam.name = "star";
        starOuterParam.title = "评星";
        FrontParams starParams;
        for (int i = 0, count = Constant.CONSTANT_STAR_ID.length; i < count; i++) {
            starParams = new FrontParams();
            starParams.id = Constant.CONSTANT_STAR_ID[i];
            starParams.name = Constant.CONSTANT_STAR_NAME[i];
            starOuterParam.list.add(starParams);
        }
        commonFrontParams.add(starOuterParam);

        //添加时间列表
        CommonFrontParams daysOuterParam = new CommonFrontParams();
        daysOuterParam.list = new ArrayList<>();
        daysOuterParam.name = "days";
        daysOuterParam.title = "报名时间";
        FrontParams daysParams;
        for (int i = 0, count = Constant.CONSTANT_DAY_ID.length; i < count; i++) {
            daysParams = new FrontParams();
            daysParams.id = Constant.CONSTANT_DAY_ID[i];
            daysParams.name = Constant.CONSTANT_DAY_NAME[i];
            daysOuterParam.list.add(daysParams);
        }
        commonFrontParams.add(daysOuterParam);
        projectScreenParams.outter = commonFrontParams;
        projectScreenParams.inner = commonInnerFrontParams;
        return projectScreenParams;
    }
}
