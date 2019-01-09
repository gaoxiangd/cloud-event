package com.welian.client.cloudevent.http;

import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import com.welian.beans.account.PageModule;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.EvenBaseReq;
import com.welian.beans.cloudevent.org.CreateOrUpdateOrgReq;
import com.welian.beans.cloudevent.org.MembersReq;
import com.welian.beans.cloudevent.org.OrderChannelListReq;
import com.welian.beans.cloudevent.org.OrderChannelListResultResp;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.org.OrgListResp;
import com.welian.beans.cloudevent.org.OrgMemberDataResp;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.user.UserReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 机构
 *
 * @Author jeizas
 * @Date 2018 10/10/18 2:48 PM
 */
@FeignClient(name = "cloudevent")
public interface OrgClient {

    /**
     * 根据机构id获取机构信息
     * 机构方查询自己的机构信息时调用
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/getinfo", method = {RequestMethod.POST})
    BaseResult<OrgInfoResp> getInfoById(@RequestBody OrderChannelReq req);

    /**
     * 根据批量的机构id批量获取机构信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/getlistbyids", method = {RequestMethod.POST})
    BaseResult<OrderChannelListResultResp> getInfoListByIds(@RequestBody OrderChannelListReq req);

    /**
     * 平台方自己修改账号信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/save", method = {RequestMethod.POST})
    BaseResult<Void> createOrUpdate(@RequestBody CreateOrUpdateOrgReq req);

    /**
     * 创建或者修改云活动平台机构的信息
     * 运营后台创建或者修改平台方账号信息，
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orgverify/authbyuser", method = {RequestMethod.POST})
    BaseResult<Void> auth(@RequestBody OrgPageReq req);

    /**
     * 获取菜单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/getmodules", method = {RequestMethod.POST})
    BaseResult<PageModule> getModules(@RequestBody OrgPageReq req);


    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/org/getlistbyuid", method = {RequestMethod.POST})
    BaseResult<PageData<OrgInfoResp>> getListByUid();

    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/org/getlistbyuid2", method = {RequestMethod.POST})
    BaseResult<PageData<OrgInfoResp>> getListByUid2(@RequestBody UserReq userReq);

    /**
     * 运营后台-查询机构列表
     */
    @RequestMapping(value = "/org/list", method = {RequestMethod.POST})
    BaseResult<OrgListResp> list(@RequestBody OrgPageReq req);

    /**
     * 运营后台修改认证状态
     */
    @RequestMapping(value = "/org/verify/update", method = {RequestMethod.POST})
    BaseResult<Void> updateVerify(@RequestBody OrgPageReq req);

    /**
     * 获取团队成员列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/member/list", method = {RequestMethod.POST})
    BaseResult<OrgMemberDataResp> getOrgMemberList(@RequestBody OrgPageReq req);

    /**
     * 删除团队成员
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/member/delete", method = {RequestMethod.POST})
    BaseResult<Void> deleteMemberBatch(@RequestBody MembersReq req);

    /**
     * 保存团队成员
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/org/member/save", method = {RequestMethod.POST})
    BaseResult<Void> saveMember(@RequestBody MembersReq req);

    @RequestMapping(value = "/member/getuserbymobile", method = {RequestMethod.POST})
    BaseResult<User> searchMember(@RequestBody UserReq req);

    /**
     * 结算申请
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/settlementapply", method = {RequestMethod.POST})
    public Object settlementapply(@RequestBody EvenBaseReq req);

}
