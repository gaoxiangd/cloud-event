package com.welian.controller;

import com.welian.beans.cloudevent.EvenBaseReq;
import com.welian.beans.cloudevent.org.CreateOrUpdateOrgReq;
import com.welian.beans.cloudevent.org.MembersReq;
import com.welian.beans.cloudevent.org.OrderChannelListReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.service.UserService;
import com.welian.service.impl.OrgServiceImpl;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by memorytale on 2017/7/5.
 */

@RestController
@RequestMapping("/org")
public class ServerOrgController {

    @Autowired
    private OrgServiceImpl orgService;
    @Autowired
    private UserService userService;

    /**
     * 根据机构id获取机构信息
     * 机构方查询自己的机构信息时调用
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getinfo", method = {RequestMethod.POST})
    public Object getInfoById(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        return orgService.getInfoById(req);
    }

    /**
     * 根据批量的机构id批量获取机构信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getlistbyids", method = {RequestMethod.POST})
    public Object getInfoListByIds(@RequestBody OrderChannelListReq req) {
        if (req.orgIds == null || req.orgIds.isEmpty()) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        return orgService.getInfoListByIds(req);
    }

    /**
     * 平台方自己修改账号信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object createOrUpdate(@RequestBody CreateOrUpdateOrgReq req) {

        return orgService.createOrUpdate(req);
    }

    /**
     * 创建或者修改云活动平台机构的信息
     * 运营后台创建或者修改平台方账号信息，
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "verify/authbyuser", method = {RequestMethod.POST})
    public Object auth(@RequestBody OrgPageReq req) {
        return orgService.auth(req);
    }

    /**
     * 获取菜单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getmodules", method = {RequestMethod.POST})
    public Object getModules(@RequestBody OrgPageReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        return orgService.getModules(req);
    }


    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getlistbyuid", method = {RequestMethod.POST})
    public Object getListByUid() {
        Integer uid = RequestHolder.getVerifiedUid();
        return orgService.getListByUid(uid);
    }

    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getlistbyuid2", method = {RequestMethod.POST})
    public Object getListByUid2(@RequestBody UserReq userReq) {
        if (!NumberUtil.isValidId(userReq.uid)) {
            throw ExceptionUtil.createParamException("uid参数错误");
        }
        return orgService.getListByUid(userReq.uid);
    }

    /**
     * 运营后台-查询机构列表
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Object list(@RequestBody OrgPageReq req) {
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return orgService.getOrgList(req);
    }

    /**
     * 运营后台修改认证状态
     */
    @RequestMapping(value = "/verify/update", method = {RequestMethod.POST})
    public Object updateVerify(@RequestBody OrgPageReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.verifyStatus)) {
            throw ExceptionUtil.createParamException("verifyStatus参数错误");
        }
        if(!NumberUtil.isValidId(req.adminUid)){
            throw ExceptionUtil.createParamException("adminUid参数错误");
        }
        return orgService.updateVerify(req);
    }

    /**
     * 获取团队成员列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/member/list", method = {RequestMethod.POST})
    public Object getOrgMemberList(@RequestBody OrgPageReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return orgService.getOrgMemberList(req);
    }

    /**
     * 删除团队成员
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/member/delete", method = {RequestMethod.POST})
    public Object deleteMemberBatch(@RequestBody MembersReq req) {
        Integer uid = RequestHolder.getVerifiedUid();
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (req.memberUids == null || req.memberUids.isEmpty()) {
            throw ExceptionUtil.createParamException("请选择删除的用户uid");
        }
        return orgService.deleteMemberBatch(req, uid);
    }

    /**
     * 保存团队成员
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/member/save", method = {RequestMethod.POST})
    public Object saveMember(@RequestBody MembersReq req) {
        Integer uid = RequestHolder.getVerifiedUid();
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.memberUid)) {
            throw ExceptionUtil.createParamException("memberUid参数错误");
        }
        return orgService.saveMember(req, uid);
    }

    @RequestMapping(value = "/member/getuserbymobile", method = {RequestMethod.POST})
    public Object searchMember(@RequestBody UserReq req) {
        if (!StringUtil.isMobile(req.mobile)) {
            throw ExceptionUtil.createParamException("请输入正确的手机号码");
        }
        return userService.getUserInfoByMobile(req.mobile);
    }



    /**
     * 账单管理列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/bills", method = {RequestMethod.POST})
    public Object getBills(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        return orgService.bills(req);
    }


    /**
     * 结算申请
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/settlementapply", method = {RequestMethod.POST})
    public Object settlementapply(@RequestBody EvenBaseReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id错误");
        }
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("用户id错误");
        }

        if (StringUtil.isEmpty(req.intro)) {
            throw ExceptionUtil.createParamException("intro参数错误");
        }
        return orgService.settlementapply(req.eventId , req.uid , req.intro);
    }


}
