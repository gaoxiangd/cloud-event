package com.welian.controller;

import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.coupon.CouponReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.EventRecordReq;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.signup.SearchSignupReq;
import com.welian.beans.cloudevent.signup.UpdateSignupReq;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.enums.account.EnumReqType;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.service.UserService;
import com.welian.service.impl.EventRecordServiceImpl;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.task.SmsPushTask;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by memorytale on 2017/7/13.
 */

@RestController
@RequestMapping("/signup")
public class ServerRecordController {
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsPushTask smsPushTask;

    /**
     * 报名根据uniqueKey获取活动详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getdetailbykey", method = RequestMethod.POST)
    public Object getDetailByKey(@RequestBody Base64KeyReq req) {

        return eventRecordService.getDetailByKey(req);
    }

    /**
     * 小程序获取活动详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/miniprogram/event", method = RequestMethod.POST)
    public Object getMiniEvent(@RequestBody Base64KeyReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        req.uniqueKey=req.eventId.toString();
        return eventRecordService.getDetailByKey(req);
    }


    /**
     * 报名根据uniqueKey获取活动详情
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getformbykey", method = RequestMethod.POST)
    public Object getFormByKey(@RequestBody Base64KeyReq req) {
        if (StringUtil.isEmpty(req.uniqueKey)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventRecordService.getFormByKey(req);
    }

    /**
     * 获取报名的项目列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/projectlist", method = RequestMethod.POST)
    public Object getProjectList(@RequestBody EventServiceReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return eventRecordService.getSignUpProjectList(req);
    }

    /**
     * 项目报名
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/projectsign", method = RequestMethod.POST)
    public Object projectSignUp(@RequestBody ProjectSignUpReq req) {
        //3.7.2加逻辑，uid由服务端获取
        com.welian.beans.account.UserReq user=new com.welian.beans.account.UserReq();
        user.type= EnumReqType.PRE;
        user.position=req.user.position;
        user.mobile=req.user.mobile;
        user.company=req.user.company;
        user.name=req.user.name;
        req.user.uid=userService.getUidByUser(user);
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("类型参数错误");
        }
        if (req.type == 3 && req.eventId != null) { // app
            return eventRecordService.projectSignUpFromApp(req);
        }
        return eventRecordService.projectSignUp(req);
    }

    /**
     * 创业活动报名
     */
    @RequestMapping(value = "/customsign", method = RequestMethod.POST)
    public Object customSignUp(@RequestBody CustomSignUpReq req) {
        //还有一个兼容app的创业活动的接口?
        if (req.type.equals(3) && req.eventId != null) {
            return eventRecordService.customSignUpFromApp(req);
        }
        return eventRecordService.customSignUp(req);
    }



    /**
     * 批量投递给投资人
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/batchorderfromchannel", method = RequestMethod.POST)
    public Object batchOrderFromChannel(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("extensionLinkId参数校验失败");
        }
        if (!NumberUtil.isValidId(req.pid)) {
            throw ExceptionUtil.createParamException("pid参数校验失败");
        }
        if (req.investors == null || req.investors.isEmpty()) {
            throw ExceptionUtil.createParamException("投资人数量为0，不能投递");
        }
        return eventRecordService.batchOrderFromChannel(req);
    }


    /**
     * 补上bp
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/updatesignupbp", method = RequestMethod.POST)
    public Object updateSignUpBP(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("extensionLinkId参数校验失败");
        }
        if (!NumberUtil.isValidId(req.pid)) {
            throw ExceptionUtil.createParamException("pid参数校验失败");
        }
        if (!NumberUtil.isValidId(req.bpId)) {
            throw ExceptionUtil.createParamException("bpId参数校验失败");
        }
        if (StringUtil.isEmpty(req.bpName)) {
            throw ExceptionUtil.createParamException("bpName参数校验失败");
        }
        if (StringUtil.isEmpty(req.bpUrl)) {
            throw ExceptionUtil.createParamException("bpUrl参数校验失败");
        }
        return eventRecordService.updateSignUpBP(req);
    }

    /**
     * 补上bp
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/updateSignUpBPByRecordId", method = RequestMethod.POST)
    public Object updateSignUpBPByRecordId(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.recordId)) {
            throw ExceptionUtil.createParamException("recordId参数校验失败");
        }
        if (!NumberUtil.isValidId(req.pid)) {
            throw ExceptionUtil.createParamException("pid参数校验失败");
        }
        if (!NumberUtil.isValidId(req.bpId)) {
            throw ExceptionUtil.createParamException("bpId参数校验失败");
        }
        if (StringUtil.isEmpty(req.bpName)) {
            throw ExceptionUtil.createParamException("bpName参数校验失败");
        }
        if (StringUtil.isEmpty(req.bpUrl)) {
            throw ExceptionUtil.createParamException("bpUrl参数校验失败");
        }
        return eventRecordService.updateSignUpBPByRecordId(req);
    }


    /**
     * 批量修改活动报名记录状态
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updatestatus", method = {RequestMethod.POST})
    public Object updateRecordStatus(@RequestBody UpdateSignupReq req) {
        if (StringUtil.isEmpty(req.signUpList)) {
            throw ExceptionUtil.createParamException("请选择要操作的列表");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        if (req.state == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (eventService.getEvent(req.eventId) == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (req.state == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getValue().byteValue()) {
            if (StringUtil.isEmpty(req.reason)) {
                throw ExceptionUtil.createParamException("请输入拒绝原因");
            }
        }
        eventRecordService.batchUpdate(req);
        return null;
    }

    /**
     * 云活动平台-搜索报名记录列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public Object searchRecord(@RequestBody SearchSignupReq req) {
        req.withCount=true;
        return eventRecordService.searchRecordList(req);
    }

    /**
     * 老融资活动重定向接口
     */
    @RequestMapping(value = "/getkeybyold", method = {RequestMethod.POST})
    public Object getKeyByOld(@RequestBody EventPara eventPara) {
        if (!NumberUtil.isValidId(eventPara.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        return eventRecordService.getKeyByOld(eventPara);
    }

    /**
     * 云活动平台-报名表单-票
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/tickets", method = {RequestMethod.POST})
    public Object tickets(@RequestBody SearchSignupReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        return eventRecordService.tickets(req);
    }

    /**
     * 是否报过名
     */
    @RequestMapping(value = "/existrecord", method = {RequestMethod.POST})
    public Object existRecord(@RequestBody AppReq req) {
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数错误");
        }
        return eventRecordService.existRecord(req);
    }



    /**
     * 活动报名数据导入
     *
     * @return
     */
    @RequestMapping(value = "/template/upload/{eventId}", method = {RequestMethod.POST}, consumes = MediaType
            .MULTIPART_FORM_DATA_VALUE)
    public Object templateDownload(@RequestParam("records") MultipartFile file,@PathVariable("eventId") Integer eventId) throws Exception {
        return eventRecordService.templateUpload(file,eventId);
    }

    /**
     * 微信根据unionId获取用户信息
     * @param req
     * @return
     */
    @RequestMapping("/unionuser")
    public Object getUserByUnionId(@RequestBody UserReq req){
        if(StringUtil.isBlank(req.unionId)){
            throw ExceptionUtil.createParamException("unionId参数异常");
        }
        return eventRecordService.getUserByUnionId(req.unionId);
    }
    /**
     * 请求调用微信推送消息
     * @return
     */
    @RequestMapping("/record/task")
    public void recordTask(){
        smsPushTask.miniWechatPush();
    }


    /**
     * 获取优惠券信息
     * @param req 优惠券入参
     */
    @RequestMapping("/coupons")
    public Object coupons(@RequestBody CouponReq req){
        return eventRecordService.getCoupons(req);
    }

    /**
     * 领取优惠券
     * @param req 优惠券入参
     */
    @RequestMapping("/coupon/get")
    public Object getCoupon(@RequestBody CouponReq req){
        return eventRecordService.getCoupon(req);
    }

    /**
     * 获取报名信息
     * @param req 活动报名记录入参
     */
    @RequestMapping("/recordInfo")
    public Object getRecordInfo(@RequestBody EventRecordReq req){
        return eventRecordService.getRecordInfo(req);
    }
}
