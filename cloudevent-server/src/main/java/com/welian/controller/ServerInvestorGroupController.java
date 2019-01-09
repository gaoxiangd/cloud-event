package com.welian.controller;

import com.welian.beans.cloudevent.investor.CreateOrUpdateInvestorGroupReq;
import com.welian.beans.cloudevent.investor.InvestorGroupReq;
import com.welian.service.InvestorGroupService;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: Controller
 * 写法示例,为了方法快速开发实现业务,定义了格式标准
 * Created by Sean.xie on 2017/2/10.
 */
@RestController
@RequestMapping("/investor")
public class ServerInvestorGroupController {


    @Autowired
    private InvestorGroupService investorGroupService;

    /**
     * 创建或修改投资人分组信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/createorupdate", method = {RequestMethod.POST})
    public Object createOrUpdate(@RequestBody CreateOrUpdateInvestorGroupReq req) {
        return investorGroupService.createOrUpdate(req);
    }

    /**
     * 删除投资人分组信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/delete", method = {RequestMethod.POST})
    public Object deleteInvestorGroup(@RequestBody InvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.investorGroupId)) {
            throw ExceptionUtil.createParamException("分组id参数错误");
        }
        return investorGroupService.deleteInvestorGroup(req);
    }

    /**
     * 获取投资人分组信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/list", method = {RequestMethod.POST})
    public Object getInvestorGroupListByOrgId(@RequestBody InvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构id参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return investorGroupService.getInvestorGroupListByOrgId(req);
    }

    /**
     * 投资人分组-添加投资人
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/addinvestor", method = {RequestMethod.POST})
    public Object addInvestor(@RequestBody InvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.investorGroupId)) {
            throw ExceptionUtil.createParamException("投资人分组id参数错误");
        }
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("投资人uid参数错误");
        }
        return investorGroupService.addInvestor(req);
    }

    /**
     * 投资人分组-删除投资人
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/deleteinvestor", method = {RequestMethod.POST})
    public Object deleteInvestor(@RequestBody InvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.investorGroupRelationId)) {
            throw ExceptionUtil.createParamException("投资人分组关系id参数错误");
        }
        return investorGroupService.deleteInvestor(req);
    }

    /**
     * 投资人分组-获取投资人列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/getinvestor", method = {RequestMethod.POST})
    public Object getInvestorList(@RequestBody InvestorGroupReq req) {
        if (!NumberUtil.isValidId(req.investorGroupId)) {
            throw ExceptionUtil.createParamException("投资人分组id参数错误");
        }
        return investorGroupService.getInvestorListByInvestorGroupId(req);
    }

}
