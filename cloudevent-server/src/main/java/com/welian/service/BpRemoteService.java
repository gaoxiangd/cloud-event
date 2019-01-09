package com.welian.service;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.Bp;
import com.welian.beans.BpAttachment;
import com.welian.beans.BpFinancingRequirement;
import com.welian.beans.BpReq;
import com.welian.beans.BpResp;
import com.welian.beans.BpSaveAttachmentRequest;
import com.welian.beans.BpSaveFinancingRequest;
import com.welian.beans.BpSaveRequest;
import com.welian.beans.cloudevent.http.HttpProjectCreateOrUpdateReq;
import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectInfoFromOtherServiceRep;
import com.welian.client.bpmanage.BpClient;
import com.welian.config.CloudEventConfig;
import com.welian.enums.bpmanage.BpAttachmentSource;
import com.welian.enums.bpmanage.BpSource;
import com.welian.enums.bpmanage.BpType;
import com.welian.enums.bpmanage.FinancingStatus;
import com.welian.enums.bpmanage.IsOpenBpStatus;
import com.welian.enums.bpmanage.VerifyStatus;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 与bp有关的请求类
 */
@Service
public class BpRemoteService {

    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private BpClient bpClient;

    public BpResp getBpDetailById(Integer pid) {
        BpReq bpReq = new BpReq();
        bpReq.bpId = pid;
        bpReq.withFinancingInfo = true;
        bpReq.withIndustry = true;
        bpReq.withCity = true;
        bpReq.withBpAttachment = true;
        BaseResult<BpResp> baseResult = bpClient.getBpDetailById(bpReq);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Optional.ofNullable(baseResult.getData()).orElseThrow(()->ExceptionUtil.createParamException("获取项目信息失败"));
        return baseResult.getData();
    }

    public ProjectInfoFromOtherServiceRep createOrUpdateToChooseInvestors(HttpProjectCreateOrUpdateReq req) {

        Bp bp = saveOrUpdateProjectFromChannel(req);

        ProjectInfoFromOtherServiceRep response = saveOrUpdateProjectBPFromChannel(bp, req);

        return response;

    }

    /**
     * 保存项目的融资信息和商业计划书
     *
     * @param bp  这里返回的bp其实是project
     * @param req
     * @return
     */
    private ProjectInfoFromOtherServiceRep saveOrUpdateProjectBPFromChannel(Bp bp,
                                                                            HttpProjectCreateOrUpdateReq req) {
        ProjectInfoFromOtherServiceRep response = new ProjectInfoFromOtherServiceRep();
        // 保存bp信息
        BpSaveAttachmentRequest bpSaveAttachmentRequest = new BpSaveAttachmentRequest();
        bpSaveAttachmentRequest.setOperateUid(bp.getUid());
        bpSaveAttachmentRequest.setUid(bp.getUid());
        bpSaveAttachmentRequest.setBpId(bp.getId());
        BpAttachment bpAttachment = null;
        if (NumberUtil.isValidId(req.bp.bpId)) {
            bpSaveAttachmentRequest.setBpAttachmentId(req.bp.bpId);
            BaseResult<Integer> baseResult = bpClient.saveAttachment(bpSaveAttachmentRequest);
            if (!baseResult.isSuccess()) {
                throw ExceptionUtil.createParamException(baseResult.getErrormsg());
            }
            bpAttachment = bpClient.getBpAttachmentById(baseResult.getData()).getData();
        } else {
            if (req.bp.bpSize != null && !StringUtil.isEmpty(req.bp.bpName) && !StringUtil.isEmpty(req.bp.bpUrl)) {
                bpSaveAttachmentRequest.setBpAttachmentName(req.bp.bpName);
                bpSaveAttachmentRequest.setBpAttachmentUrl(req.bp.bpUrl);
                bpSaveAttachmentRequest.setBpAttachmentSize(req.bp.bpSize);
                bpSaveAttachmentRequest.setBpAttachmentSource(BpAttachmentSource.H5.getCode());
                BaseResult<Integer> baseResult = bpClient.saveAttachment(bpSaveAttachmentRequest);
                if (!baseResult.isSuccess()) {
                    throw ExceptionUtil.createParamException(baseResult.getErrormsg());
                }
                bpAttachment = bpClient.getBpAttachmentById(baseResult.getData()).getData();
            }
        }
        // 保存项目的融资信息
        if (req.financingInfo != null && req.financingInfo.share != null && req.financingInfo.stage != null && req
                .financingInfo.amountUnit !=
                null && req.financingInfo.amount != null) {
            BpSaveFinancingRequest financingRequest = new BpSaveFinancingRequest();
            financingRequest.setOperateUid(bp.getUid());
            financingRequest.setBpId(bp.getId());
            BpFinancingRequirement financingRequirement = new BpFinancingRequirement();
            financingRequirement.setAmount(Integer.valueOf(req.financingInfo.amount));
            financingRequirement.setAmountUnit(req.financingInfo.amountUnit.byteValue());
            financingRequirement.setStage(req.financingInfo.stage);
            financingRequirement.setShare(req.financingInfo.share);
            //financingRequirement.setIsopenbp(IsOpenBpStatus.FALSE.getCode());
            //financingRequirement.setStatus(FinancingStatus.PROCESSED.getCode());
            financingRequest.setBpFinancingRequirement(financingRequirement);
            BaseResult<BpFinancingRequirement> result = bpClient.saveFinancing(financingRequest);
            if (!result.isSuccess()) {
                throw ExceptionUtil.createParamException("融资信息保存失败");
            }
        }
        response.bp = new BPResp();
        if (bpAttachment != null) {
            response.bp.bpSize = bpAttachment.getSize();
            response.bp.bpName = bpAttachment.getTitle();
            //url目前的作用只是保存project备份，不需要前缀
            response.bp.bpUrl = bpAttachment.getUrl();
            response.bp.bpId = bpAttachment.getId();
        }
        response.pid = NumberUtil.isValidId(req.project.pid) ? req.project.pid : bp.getId();
        return response;
    }

    /**
     * 保存项目的基本信息
     *
     * @param req
     * @return
     */
    private Bp saveOrUpdateProjectFromChannel(HttpProjectCreateOrUpdateReq req) {
        Integer uid = req.user.uid;
        BpSaveRequest bpSaveRequest = new BpSaveRequest();
        // 项目信息处理
        Integer pid = req.project.pid;
        if (NumberUtil.isValidId(pid)) {
            bpSaveRequest.setBpId(pid);
            //修改项目时如果有logo则修改，如果没有logo，数据不动
            if (!StringUtil.isEmpty(req.project.logo)) {
                bpSaveRequest.setLogo(req.project.logo);
            }
        } else {
            //新增项目时如果有logo则修改，如果没有logo，填写默认logo
            if (StringUtil.isNotEmpty(req.project.logo)) {
                bpSaveRequest.setLogo(req.project.logo);
            }
            bpSaveRequest.setVerifyStatus(VerifyStatus.INIT);
            bpSaveRequest.setVerifyTime(System.currentTimeMillis());
            bpSaveRequest.setType(BpType.APPLY);
            bpSaveRequest.setSource(BpSource.THIRD_CHANNEL);
            BpFinancingRequirement financingRequirement = new BpFinancingRequirement();
            financingRequirement.setIsopenbp(IsOpenBpStatus.FALSE.getCode());
            financingRequirement.setStatus(FinancingStatus.PROCESSED.getCode());
            bpSaveRequest.setBpFinancingRequirement(financingRequirement);
        }
        //保存名称
        String projectName = req.project.name;
        if (projectName != null) {
            bpSaveRequest.setName(projectName);
        }
        bpSaveRequest.setOperateUid(uid);
        bpSaveRequest.setUid(uid);
        //保存一句话介绍
        String projectIntro = req.project.intro;
        if (projectIntro != null) {
            bpSaveRequest.setIntro(projectIntro);
        }
        //保存备注
        String projectRemark = req.project.remark;
        if (projectRemark != null) {
            bpSaveRequest.setRemark(projectRemark);
        }
        //保存公司全称
        String projectCompanyName = req.project.companyName;
        if (projectCompanyName != null) {
            bpSaveRequest.setCompany(projectCompanyName);
        }
        String projectWebsite = req.project.website;
        if (projectWebsite != null) {
            bpSaveRequest.setWebsite(projectWebsite);
        }
        //保存项目阶段
        Integer projectStage = req.project.projectStage;
        if (projectStage != null) {
            bpSaveRequest.setProjectStage(projectStage);
        }
        //保存城市
        Integer projectCityId = req.project.cityId;
        if (projectCityId != null) {
            bpSaveRequest.setCityId(projectCityId);
        }
        //保存领域
        Integer projectIndustryId = req.project.industrySecondId;
        if (projectIndustryId != null) {
            List<Integer> industrys = new ArrayList<>();
            industrys.add(projectIndustryId);
            bpSaveRequest.setIndustryIds(industrys);
        }
        BaseResult<Integer> bpSaveResponse = bpClient.save(bpSaveRequest);
        if (!bpSaveResponse.isSuccess()) {
            throw ExceptionUtil.createParamException(bpSaveResponse.getErrormsg());
        }
        BaseResult<Bp> result = bpClient.getBpById(bpSaveResponse.getData());
        if (!result.isSuccess()) {
            throw ExceptionUtil.createParamException(result.getErrormsg());
        }
        return result.getData();
    }
}
