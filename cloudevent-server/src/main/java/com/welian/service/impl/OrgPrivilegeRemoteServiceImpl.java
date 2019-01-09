package com.welian.service.impl;

import com.welian.beans.account.AuthPageData;
import com.welian.beans.account.Member;
import com.welian.beans.account.Organization;
import com.welian.beans.account.OrganizationAuthInfo;
import com.welian.beans.account.OrganizationAuthMaterial;
import com.welian.beans.account.Package;
import com.welian.beans.account.PageModule;
import com.welian.beans.account.Role;
import com.welian.beans.account.UIModule;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.org.EvidencesResp;
import com.welian.beans.cloudevent.org.MembersReq;
import com.welian.beans.cloudevent.org.OrgPackagePageReq;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.org.PackageInfoResp;
import com.welian.client.account.OrganizationAuthClient;
import com.welian.client.account.OrganizationClient;
import com.welian.client.account.OrganizationModuleClient;
import com.welian.client.account.OrganizationPackageClient;
import com.welian.client.account.OrganizationRoleClient;
import com.welian.config.CloudEventConfig;
import com.welian.enums.account.EnumSource;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orgPrivilegeRemoteService")
public class OrgPrivilegeRemoteServiceImpl{

    @Autowired
    private OrganizationClient organizationClient;
    @Autowired
    private OrganizationModuleClient organizationModuleClient;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private OrganizationAuthClient organizationAuthClient;
    @Autowired
    private OrganizationPackageClient organizationPackageClient;
    @Autowired
    private OrganizationRoleClient organizationRoleClient;


    public Integer addOrg(Organization organization) {
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();//指定云活动平台
        BaseResult<Integer> baseResult = organizationClient.addOrg(organization);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        if (!NumberUtil.isValidId(baseResult.getData())) {
            throw ExceptionUtil.createParamException("创建机构失败");
        }
        return baseResult.getData();
    }


    public User getOrgsByUid(User user) {
        user.source = EnumSource.CLOUD_ACTIVITY.getValue();
        user.withOrgs = true;
        user.withPackage = true;
        user.withAuthStatus = true;
        BaseResult<PageData<User>> baseResult = organizationClient.queryUser(user);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        if (baseResult.getData() == null || baseResult.getData().count == 0 || baseResult.getData().list.isEmpty()) {
            throw ExceptionUtil.createParamException("找不到用户");
        }
        return baseResult.getData().list.get(0);
    }


    public Organization getOrgById(Integer orgId) {
        Organization organization = new Organization();
        organization.id = orgId;
        organization.withPackage = true;//用户在云活动平台获取账号信息时使用
        organization.withAuthStatus = true;//用户在云活动平台获取账号信息时使用
        organization.withAdmin = true;//运营后台更新机构信息时使用
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();
        BaseResult<Organization> baseResult = organizationClient.getOrg(organization);
        if (!baseResult.isSuccess() || baseResult.getData() == null) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }

    /**
     * 根据orgIds获取机构列表
     * @param orgIds
     * @return
     */

    public List<Organization> getOrgsByIds(List<Integer> orgIds) {
        List<Organization> returnOrgs=new ArrayList<>();
        Organization organization=new Organization();
        organization.withAdmin = true;//运营后台更新机构信息时使用
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();
        organization.orgIds=orgIds;
        organization.setFull(true);
        BaseResult<PageData<Organization>> baseResult=organizationClient.getOrgs(organization);
        if (!baseResult.isSuccess() ) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        for(Organization org:baseResult.getData().list){
            if(orgIds.contains(org.id)){
                returnOrgs.add(org);
            }
        }
        return returnOrgs;
    }

    /**
     * 根据条件搜索获取机构（运营后台使用）
     *
     * @param req
     * @return
     */

    public AuthPageData getOrgList(OrgPageReq req) {
        OrganizationAuthInfo org = new OrganizationAuthInfo();
        /**
         * 根据type和keyword拼接查询条件
         */
        if (req.type == ParamEnum.OrgType.TYPE_ORG_NAME.getValue().intValue()) {
            org.orgName = req.keyword;
        } else if (req.type == ParamEnum.OrgType.TYPE_PHONE.getValue().intValue()) {
            org.phone = req.keyword;
        } else if (req.type == ParamEnum.OrgType.TYPE_USER_NAME.getValue().intValue()) {
            org.userName = req.keyword;
        }
        org.verifyStatus = req.verifyStatus;
        org.source = EnumSource.CLOUD_ACTIVITY.getValue();
        org.withAdmin = true;
        org.setPage(req.page);
        org.setSize(req.size);
        BaseResult<AuthPageData> baseResult = organizationAuthClient.list(org);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }

        return baseResult.getData();
    }



    public PageData<Organization> getPackageList(OrgPackagePageReq req) {
        Package aPackage = new Package();
        aPackage.withAdmin = true;
        aPackage.source = EnumSource.CLOUD_ACTIVITY.getValue();
        aPackage.setPage(req.page);
        aPackage.setSize(req.size);
        /**
         * 根据type和keyword拼接查询条件
         */
        if (req.type == ParamEnum.OrgType.TYPE_ORG_NAME.getValue().intValue()) {
            aPackage.orgName = req.keyword;
        } else if (req.type == ParamEnum.OrgType.TYPE_PHONE.getValue().intValue()) {
            aPackage.phone = req.keyword;
        } else if (req.type == ParamEnum.OrgType.TYPE_USER_NAME.getValue().intValue()) {
            aPackage.userName = req.keyword;
        }
        BaseResult<PageData<Organization>> baseResult = organizationPackageClient.getAllSourceOrg(aPackage);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        if (baseResult.getData() == null || baseResult.getData().list == null) {
            throw ExceptionUtil.createParamException("套餐不存在");
        }
        return baseResult.getData();
    }

    /**
     * 修改认证状态
     *
     * @param req
     */

    public Object updateVerify(OrgPageReq req) {
        OrganizationAuthInfo org = new OrganizationAuthInfo();
        org.source = EnumSource.CLOUD_ACTIVITY.getValue();
        org.orgId = req.orgId;
        org.verifyStatus = req.verifyStatus;
        org.reason = req.reason;
        org.applyTime = new Date();
        org.materials = new ArrayList<>();
        OrganizationAuthMaterial organizationAuthMaterial;
        if (!StringUtil.isEmpty(req.evidences)) {
            for (EvidencesResp resp : req.evidences) {
                organizationAuthMaterial = new OrganizationAuthMaterial();
                organizationAuthMaterial.url = resp.url;
                organizationAuthMaterial.name = resp.title;
                org.materials.add(organizationAuthMaterial);
            }
        }
        return organizationAuthClient.updateStatus(org);
    }

    /**
     * 修改套餐d
     *
     * @param req
     * @return
     */
    public Object updatePackage(OrgPackagePageReq req) {
        Package orgPcg = new Package();
        orgPcg.orgId = req.orgId;
        orgPcg.id = req.packageId;
        orgPcg.startTime = System.currentTimeMillis();
        orgPcg.source = EnumSource.CLOUD_ACTIVITY.getValue();
        BaseResult<Void> baseResult = organizationPackageClient.update(orgPcg);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }

    /**
     * 获取团队成员列表
     *
     * @param req
     * @return
     */

    public PageData<User> getOrgMemberList(OrgPageReq req) {
        Organization organization = new Organization();
        if(req.isFull!=null&&req.isFull){
            organization.setFull(true);
        }
        organization.id = req.orgId;
        organization.setPage(req.page);
        organization.setSize(req.size);
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();
        organization.withRoles = true;
        BaseResult<PageData<User>> baseResult = organizationClient.getUsers(organization);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }

    /**
     * 批量删除团队成员
     *
     * @param req
     */

    public void deleteMemberBatch(MembersReq req, Integer uid) {

        if (req.memberUids.contains(uid)) {
            throw ExceptionUtil.createParamException("禁止这种操作");
        }
        User user = new User();
        user.source = EnumSource.CLOUD_ACTIVITY.getValue();
        user.uids = req.memberUids;
        user.orgId = req.orgId;
        //验证权限
        Role role = new Role();
        role.uid = uid;
        role.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role.orgId = req.orgId;
        BaseResult<Role> baseResult = organizationRoleClient.getUserRoles(role);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Integer roleId = cloudEventConfig.getDefault_normal_user_id();
        if (baseResult.getList() != null && !baseResult.getList().isEmpty()) {
            roleId = baseResult.getList().get(0).id;
        }
        if (roleId != cloudEventConfig.getDefault_admin_id().intValue()) {
            throw ExceptionUtil.createParamException("您不是超级管理员，没有权限删除成员");
        }
        BaseResult<Void> result = organizationClient.deleteMemberBatch(user);
        if (!result.isSuccess()) {
            throw ExceptionUtil.createParamException(result.getErrormsg());
        }
    }

    /**
     * 创建团队成员
     *
     * @param req
     * @return
     */

    public void createMember(MembersReq req, Integer uid) {

        //验证已经存在的不能添加
        Organization organization = new Organization();
        organization.id = req.orgId;
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();
        organization.withRoles = true;
        BaseResult<PageData<User>> result = organizationClient.getUsers(organization);
        List<Integer> uids = new ArrayList<>();
        if (!result.isSuccess()) {
            throw ExceptionUtil.createParamException(result.getErrormsg());
        }
        if(!StringUtil.isEmpty(result.getData().list)) {
            for (User user : result.getData().list) {
                uids.add(user.id);
            }
            if (uids.contains(req.memberUid)){
                throw ExceptionUtil.createWarnException("该成员已存在");
            }
        }
        Role role = new Role();
        role.orgId = req.orgId;
        role.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role.joinOneRole = true;
        Member member = new Member();
        member.orgId = req.orgId;
        member.joinTime = new Date().getTime();
        member.uid = req.memberUid;
        member.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role.member = member;
        role.id = cloudEventConfig.getDefault_normal_user_id();
        //验证权限
        Role role1 = new Role();
        role1.uid = uid;
        role1.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role1.orgId = req.orgId;
        BaseResult<Role> baseResult = organizationRoleClient.getUserRoles(role1);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Integer roleId = cloudEventConfig.getDefault_normal_user_id();
        if (baseResult.getList() != null && !baseResult.getList().isEmpty()) {
            roleId = baseResult.getList().get(0).id;
        }
        if (roleId != cloudEventConfig.getDefault_admin_id().intValue()) {
            throw ExceptionUtil.createParamException("您不是超级管理员，没有权限添加成员");
        }

        BaseResult<Void> baseResult1 = organizationRoleClient.addRoleMember(role);
        if (!baseResult1.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }


    public PackageInfoResp getPackageInfo(Integer sourceId) {
        Package _package = new Package();
        _package.source = EnumSource.CLOUD_ACTIVITY.getValue();
        BaseResult<PageData<Package>> baseResult = organizationPackageClient.getPackageInfo(_package);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        PackageInfoResp packageInfoResp = new PackageInfoResp();
        packageInfoResp.count = (int) baseResult.getData().count;
        packageInfoResp.list = baseResult.getData().list;

        return packageInfoResp;
    }


    public Object getModules(Integer orgId) {
        Integer uid = RequestHolder.getVerifiedUid();
        UIModule uiModule = new UIModule();
        uiModule.withPackage = true;
        uiModule.source = EnumSource.CLOUD_ACTIVITY.getValue();
        uiModule.orgId = orgId;
        uiModule.uid = uid;
        uiModule.setFull(true);
        BaseResult<PageModule> baseResult = organizationModuleClient.getUserModules(uiModule);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getData();
    }


    public void modifyOrg(Organization organization) {
        organization.source = EnumSource.CLOUD_ACTIVITY.getValue();
        BaseResult<Void> baseResult = organizationClient.modifyOrg(organization);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }


    public void refreshOrgMember(Integer orgId, Integer id) {
        //更改管理员信息
        Role role = new Role();
        role.orgId = orgId;
        role.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role.joinOneRole = true;
        Member member = new Member();
        member.orgId = orgId;
        member.joinTime = new Date().getTime();
        member.uid = id;
        member.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role.member = member;
        role.id = cloudEventConfig.getDefault_admin_id();
        BaseResult<Void> baseResult = organizationRoleClient.refreshRoleMember(role);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }



    public Integer getRoleTypeByUid(Integer orgId, Integer uid) {
        //从jwt获得roleType
        Role role1 = new Role();
        role1.uid = uid;
        role1.source = EnumSource.CLOUD_ACTIVITY.getValue();
        role1.orgId = orgId;
        BaseResult<Role> baseResult = organizationRoleClient.getUserRoles(role1);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Integer roleType = null;
        if (baseResult.getList() != null && !baseResult.getList().isEmpty()) {
            Integer roleId = baseResult.getList().get(0).id;
            if (roleId != null && roleId.equals(cloudEventConfig.getDefault_normal_user_id())) {
                roleType = 2;
            } else if (roleId != null && roleId.equals(cloudEventConfig.getDefault_admin_id())) {
                roleType = 1;
            }
        }
        return roleType;
    }


    public Role getRole(Integer uid, Integer orgId) {
        Role role = new Role();
        role.orgId = orgId;
        role.uid = uid;
        role.source = EnumSource.CLOUD_ACTIVITY.getValue();
        BaseResult<Role> baseResult = organizationRoleClient.getUserRoles(role);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return baseResult.getList().get(0);
    }


}
