package com.welian.service;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.investor.CreateOrUpdateInvestorGroupReq;
import com.welian.beans.cloudevent.investor.InvestorGroupListBeanResp;
import com.welian.beans.cloudevent.investor.InvestorGroupListResultResp;
import com.welian.beans.cloudevent.investor.InvestorGroupReq;
import com.welian.beans.cloudevent.investor.InvestorGroupResp;
import com.welian.beans.cloudevent.investor.InvestorListResultResp;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventInvestorGroupRelationMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.InvestorGroupMapper;
import com.welian.mapper.InvestorGroupRelationMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventInvestorGroupRelation;
import com.welian.pojo.EventInvestorGroupRelationExample;
import com.welian.pojo.InvestorGroup;
import com.welian.pojo.InvestorGroupExample;
import com.welian.pojo.InvestorGroupRelation;
import com.welian.pojo.InvestorGroupRelationExample;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
@Service
public class InvestorGroupService {

    @Autowired
    private InvestorGroupMapper investorGroupMapper;
    @Autowired
    private EventInvestorGroupRelationMapper eventInvestorGroupRelationMapper;
    @Autowired
    private InvestorGroupRelationMapper investorGroupRelationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EventMapper eventMapper;
//    @Autowired
//    private InvestorClient investorClient;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;

    /**
     * 创建或修改事件活动
     *
     * @param req
     * @return
     */
    @Transactional
    public InvestorGroupResp createOrUpdate(CreateOrUpdateInvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (StringUtil.isEmpty(req.name)) {
            throw ExceptionUtil.createParamException("请填写投资人分组名称");
        }
        if (req.name.trim().length() > 10) {
            throw ExceptionUtil.createParamException("投资人分组名称不能超过10字");
        }
        //校验是否存在同名的投资人分组
        InvestorGroup investorGroup;
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.createCriteria().andGroupNameEqualTo(req.name)
                .andIdNotEqualTo(req.investorGroupId).andStateEqualTo((byte) 0).andOrgIdEqualTo(req.orgId);
        List<InvestorGroup> investorGroups = investorGroupMapper.selectByExample(investorGroupExample);
        if (investorGroups != null && !investorGroups.isEmpty()) {
            throw ExceptionUtil.createParamException("已存在同名的投资人分组");
        }
        //如果是修改分组
        if (NumberUtil.isValidId(req.investorGroupId)) {
            investorGroup = updateInvestorGroup(req);
        } else {//如果是创建分组
            investorGroup = createInvestorGroup(req);
        }
        //保存分组信息
        InvestorGroupResp investorGroupResp = new InvestorGroupResp();
        investorGroupResp.investorGroupId = investorGroup.getId();
        return investorGroupResp;
    }

    /**
     * 删除投资人分组
     *
     * @param req
     * @return
     */
    public Object deleteInvestorGroup(InvestorGroupReq req) {
        //先判断此投资人分组有没有活动与之关联，如果有关联，则提示分组不能删除
        EventInvestorGroupRelationExample eventInvestorGroupRelationExample = new EventInvestorGroupRelationExample();
        eventInvestorGroupRelationExample.createCriteria().andInvestorGroupIdEqualTo(req.investorGroupId);
        List<EventInvestorGroupRelation> eventInvestorGroupRelations = eventInvestorGroupRelationMapper
                .selectByExample(eventInvestorGroupRelationExample);
        if (eventInvestorGroupRelations != null && !eventInvestorGroupRelations.isEmpty()) {
            Event event = eventMapper.selectByPrimaryKey(eventInvestorGroupRelations.get(0).getEventId());
            if (event == null) {
                throw ExceptionUtil.createParamException("分组关联的活动不存在，无法删除");
            }
            if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().byteValue()) {

            } else {
                throw ExceptionUtil.createParamException("该分组已关联在活动" + event.getTitle() + "中，需去除关联才能删除该分组");
            }
        }
        //修改分组状态，将其置为伪删除状态
        InvestorGroup investorGroup = new InvestorGroup();
        investorGroup.setState((byte) 1);
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.createCriteria().andIdEqualTo(req.investorGroupId);
        int result = investorGroupMapper.updateByExampleSelective(investorGroup, investorGroupExample);
        if (result == 0) {
            throw ExceptionUtil.createParamException("删除投资人分组失败");
        }
        return null;
    }

    /**
     * 获取投资人分组列表信息
     *
     * @param req
     * @return
     */
    public Object getInvestorGroupListByOrgId(InvestorGroupReq req) {
        InvestorGroupListResultResp resultResp = new InvestorGroupListResultResp();
        resultResp.list = getInvestorGroupList(req);
        //获取投资人分组的数量
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.createCriteria().andStateEqualTo((byte) 0).andOrgIdEqualTo(req.orgId);
        resultResp.count = (int) investorGroupMapper.countByExample(investorGroupExample);
        return resultResp;
    }

    /**
     * 通过投资人分组id获取投资人的信息
     *
     * @param req
     * @return
     */
    public Object getInvestorListByInvestorGroupId(InvestorGroupReq req) {
        InvestorListResultResp resultResp = new InvestorListResultResp();
        resultResp.list = getInvestorList(req);
        //获取分组中投资人的数量
        InvestorGroupRelationExample relationExample = new InvestorGroupRelationExample();
        relationExample.createCriteria().andInvestorGroupIdEqualTo(req.investorGroupId);
        resultResp.count = (int) investorGroupRelationMapper.countByExample(relationExample);
        return resultResp;
    }

    /**
     * 删除投资人分组里的投资人
     *
     * @param req
     * @return
     */
    @Transactional
    public Object deleteInvestor(InvestorGroupReq req) {
        InvestorGroupRelation investorGroupRelation = investorGroupRelationMapper.
                selectByPrimaryKey(req.investorGroupRelationId);
        if (investorGroupRelation == null) {
            throw ExceptionUtil.createParamException("投资人关联不存在");
        }
        // 更新投资人分组中投资人的数量
        InvestorGroupRelationExample example = new InvestorGroupRelationExample();
        example.createCriteria().andIdEqualTo(req.investorGroupRelationId);
        int result = investorGroupRelationMapper.deleteByExample(example);
        if (result == 0) {
            throw ExceptionUtil.createParamException("删除投资人失败");
        }
        example.clear();
        example.createCriteria().andInvestorGroupIdEqualTo(investorGroupRelation.getInvestorGroupId());
        //更改数量
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.createCriteria().andIdEqualTo(investorGroupRelation.getInvestorGroupId());
        InvestorGroup investorGroup = new InvestorGroup();
        investorGroup.setId(investorGroupRelation.getInvestorGroupId());
        //获取最新的数量
        investorGroup.setCount((int) investorGroupRelationMapper.countByExample(example));
        int increaseResult = investorGroupMapper.updateByPrimaryKeySelective(investorGroup);
        if (increaseResult == 0) {
            throw ExceptionUtil.createParamException("更新投资人的数量失败");
        }
        return null;
    }

    /**
     * 向投资人分组中添加投资人
     *
     * @param req
     * @return
     */
    @Transactional
    public Object addInvestor(InvestorGroupReq req) {
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.createCriteria().andIdEqualTo(req.investorGroupId).andStateEqualTo((byte) 0);
        InvestorGroup investorGroup = investorGroupMapper.selectByPrimaryKey(req.investorGroupId);
        if (investorGroup == null) {
            throw ExceptionUtil.createParamException("投资人分组不存在");
        }
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(investorGroup.getOrgId()).andStateEqualTo((byte) 0);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (StringUtil.isEmpty(orgInfos)) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        OrgInfo orgInfo = orgInfos.get(0);
        //去判断当前是否是认证投资人，如果不是认证投资人，去提交审核
        User profile = userService.getUserInfoById(req.uid);
        if (profile.authStatus != null && profile.authStatus != 1) {
            //去做投资人审核
            com.welian.beans.investor.UserReq userResp = new com.welian.beans.investor.UserReq();
            userResp.investorAuth = -2;
            userResp.uid = req.uid;
            userResp.authRemark = "云活动平台" + orgInfo.getName() + "来源";
//            BaseResult<Void> baseResult1 = investorClient.reviseInvestorAuthState(userResp);
//            if (!baseResult1.isSuccess()) {
//                throw ExceptionUtil.createParamException("修改投资人审核状态失败");
//            }
        }

        InvestorGroupRelationExample example = new InvestorGroupRelationExample();
        example.createCriteria().andInvestorGroupIdEqualTo(req.investorGroupId).andUidEqualTo(req.uid);
        int count = (int) investorGroupRelationMapper.countByExample(example);
        if (count > 0) {
            //如果关联表里已经有添加过此投资人,则进行提示，如果此用户不是投资人，则默认提示添加成功，因为此用户的审核状态有变化
            if (profile.authStatus != null && profile.authStatus == 1) {
                throw ExceptionUtil.createParamException("此投资人已添加，不可重复添加");
            } else {

            }
        } else {
            //保存投资人到投资人分组
            InvestorGroupRelation relation = new InvestorGroupRelation();
            relation.setState((byte) 0);
            Long time = System.currentTimeMillis();
            relation.setCreateTime(time);
            relation.setModifyTime(time);
            relation.setInvestorGroupId(req.investorGroupId);
            relation.setUid(req.uid);
            int result = investorGroupRelationMapper.insertSelective(relation);
            if (result == 0) {
                throw ExceptionUtil.createParamException("添加投资人失败");
            }
            //更新投资人的数量
            example.clear();
            example.createCriteria().andInvestorGroupIdEqualTo(req.investorGroupId);
            InvestorGroup investorGroupUpdate = new InvestorGroup();
            investorGroupUpdate.setId(req.investorGroupId);
            //获取最新的数量
            investorGroupUpdate.setCount((int) investorGroupRelationMapper.countByExample(example));
            //更改数量
            int increaseResult = investorGroupMapper.updateByPrimaryKeySelective(investorGroupUpdate);
            if (increaseResult == 0) {
                throw ExceptionUtil.createParamException("更新投资人的数量失败");
            }
        }
        return null;
    }
    /**
     * 外部调用放放与内部调用方法分割线
     ******************************************************************************/

    /**
     * 获取投资人列表
     *
     * @param req
     * @return
     */
    private List<UserResp> getInvestorList(InvestorGroupReq req) {
        InvestorGroupRelationExample investorGroupRelationExample = new InvestorGroupRelationExample();
        investorGroupRelationExample.createCriteria().andInvestorGroupIdEqualTo(req.investorGroupId)
                .andStateEqualTo((byte) 0);
        investorGroupRelationExample.setOrderByClause("id desc");
        investorGroupRelationExample.setLimit(req.getSize());
        investorGroupRelationExample.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
        List<InvestorGroupRelation> investorGroupRelations = investorGroupRelationMapper
                .selectByExample(investorGroupRelationExample);
        List<UserResp> userResps = new ArrayList<UserResp>();
        if (investorGroupRelations == null || investorGroupRelations.isEmpty()) {
            return userResps;
        }
        List<Integer> investorUids = new ArrayList<>();
        for (InvestorGroupRelation investorGroupListBeanResp : investorGroupRelations) {
            investorUids.add(investorGroupListBeanResp.getUid());
        }
        //批量查询投资人的信息列表
        Map<Integer, User> map = userService.getUserInfoListByIds(investorUids);
        for (InvestorGroupRelation investorGroupListBeanResp : investorGroupRelations) {
            UserResp userResp = new UserResp();
            if (map.containsKey(investorGroupListBeanResp.getUid())) {
                User profile = map.get(investorGroupListBeanResp.getUid());
                userResp.name = profile.name;
                userResp.company = profile.company;
                userResp.position = profile.position;
                userResp.investorAuth = profile.authStatus;
                userResp.avatar = cloudEventConfig.getImage_prefix() + profile.avatar;
            }
            userResp.investorGroupRelationId = investorGroupListBeanResp.getId();
            userResps.add(userResp);
        }

        return userResps;
    }


    /**
     * 获取投资人分组列表
     *
     * @param req
     * @return
     */
    private List<InvestorGroupListBeanResp> getInvestorGroupList(InvestorGroupReq req) {
        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
        investorGroupExample.setLimit(req.getSize());
        investorGroupExample.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
        investorGroupExample.createCriteria().andOrgIdEqualTo(req.orgId).andStateEqualTo((byte) 0);
        List<InvestorGroup> investorGroups = investorGroupMapper.selectByExample(investorGroupExample);
        List<InvestorGroupListBeanResp> investorGroupListBeanResps = new ArrayList<InvestorGroupListBeanResp>();
        InvestorGroupListBeanResp investorGroupListBeanResp;
        for (InvestorGroup investorGroup : investorGroups) {
            investorGroupListBeanResp = new InvestorGroupListBeanResp();
            investorGroupListBeanResp.orgId = investorGroup.getOrgId();
            investorGroupListBeanResp.investorGroupId = investorGroup.getId();
            investorGroupListBeanResp.investorGroupName = investorGroup.getGroupName();
            investorGroupListBeanResp.investorCount = investorGroup.getCount();
            investorGroupListBeanResp.createTime = investorGroup.getCreateTime();
            investorGroupListBeanResps.add(investorGroupListBeanResp);
        }
        return investorGroupListBeanResps;
    }

    /**
     * 创建投资人分组
     *
     * @param req
     * @return
     */
    private InvestorGroup createInvestorGroup(CreateOrUpdateInvestorGroupReq req) {
        InvestorGroup investorGroup = new InvestorGroup();
        Long time = System.currentTimeMillis();
        investorGroup.setCreateTime(time);
        investorGroup.setModifyTime(time);
        investorGroup.setGroupName(req.name);
        investorGroup.setOrgId(req.orgId);
        investorGroup.setState((byte) 0);
        //保存投资人分组
        int result = investorGroupMapper.insertSelective(investorGroup);
        if (result == 0) {
            throw ExceptionUtil.createParamException("创建投资人分组失败");
        }
        return investorGroup;
    }

    /**
     * 更新投资人分组
     *
     * @param req
     * @return
     */
    private InvestorGroup updateInvestorGroup(CreateOrUpdateInvestorGroupReq req) {
        InvestorGroup investorGroup = new InvestorGroup();
        Long time = System.currentTimeMillis();
        investorGroup.setId(req.investorGroupId);
        investorGroup.setModifyTime(time);
        investorGroup.setGroupName(req.name);
        investorGroup.setOrgId(req.orgId);
        investorGroup.setState((byte) 0);
        //保存投资人分组
        int result = investorGroupMapper.updateByPrimaryKeySelective(investorGroup);
        if (result == 0) {
            throw ExceptionUtil.createParamException("编辑投资人分组失败");
        }
        return investorGroup;
    }

}
