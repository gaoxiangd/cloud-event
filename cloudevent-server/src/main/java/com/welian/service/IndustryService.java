package com.welian.service;

import com.alibaba.fastjson.JSON;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import com.welian.beans.recommend.FirstIndustryReq;
import com.welian.beans.recommend.RecTagBean;
import com.welian.client.recommend.RecTagClient;
import com.welian.code.ResultCodes;
import com.welian.enums.recommend.EnumCategoryType;
import com.welian.enums.recommend.EnumIndustrySearchType;
import com.welian.enums.recommend.EnumTagSource;
import com.welian.enums.recommend.EnumTagStatus;
import com.welian.exception.CodeException;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by memorytale on 2017/7/6.
 */
@Service
public class IndustryService {
    private static final Logger logger = Logger.newInstance(IndustryService.class);

    private Map<Integer, Industry> allIndustryMap = new HashMap<>();

    private List<Industry> allIndustry = new ArrayList<>();

    @Autowired
    private RecTagClient recTagClient;

    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;

    @Scheduled(cron = "0 1 0 * * ? ")//每天凌晨0点1分执行
    protected void taskIndustryUpdate() {
        allIndustry.clear();
        allIndustryMap.clear();
    }

    /**
     * 初始化全部数据
     */
    @PostConstruct
    public void init() {
        getAllIndustryList();
        getAllIndustryMap();
    }

    public class Industry {
        public Integer id;
        public String name;
        public Integer fid;
        public List<Industry> list;
    }

    /**
     * 从其他服务获取领域列表
     *
     * @return
     */
    private PageData<RecTagBean> getAllIndustriesFromRemote() {
        FirstIndustryReq req = new FirstIndustryReq();
        req.categoryId = EnumCategoryType.INDUSTRY.getValue();
        req.source = EnumTagSource.INDUSTRY;
        req.status = EnumTagStatus.CONVERTED;
        req.searchType = EnumIndustrySearchType.FRIST_AND_SUB;
        BaseResult<PageData<RecTagBean>> baseResult = recTagClient.tagList(req);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        logger.info("IndustryService: " + JSONUtil.obj2Json(baseResult.getData()));
        return baseResult.getData();
    }

    private List<Industry> getAllIndustriesConvertData() {
        List<Industry> allIndustryInner = new ArrayList<>();
        PageData<RecTagBean> pageData = getAllIndustriesFromRemote();
        Optional.ofNullable(pageData.list)
                .orElseThrow(() -> new CodeException(new ResultCodes.DataCode(ResultCodes.Code.COMMON_ERROR_PARAMS,
                        "领域列表异常")))
                .stream()
                .filter(e -> NumberUtil.isValidId(e.businessId))
                .forEach(e -> {

                    Industry industryListBean = new Industry();
                    industryListBean.id = e.id;
                    industryListBean.name = e.name;
                    industryListBean.list = new ArrayList<>();

                    if (StringUtil.isNotEmpty(e.subTags)) {
                        Industry industryOther = null;
                        for (RecTagBean recTagBean : e.subTags) {
                            if (NumberUtil.isInValidId(recTagBean.businessId)) {
                                continue;
                            }
                            if (StringUtil.isEmpty(recTagBean.name)) {
                                continue;
                            }

                            Industry subIndustryBean = new Industry();
                            if (recTagBean.name.contains("其他") || recTagBean.name.contains("其它")) {
                                industryOther = new Industry();
                                industryOther.id = recTagBean.id;
                                industryOther.name = "其他";
                            } else {
                                subIndustryBean.id = recTagBean.id;
                                subIndustryBean.name = recTagBean.name;
                                industryListBean.list.add(subIndustryBean);
                            }
                            //将二级领域中其他放到列表的最后，因为推荐服务那边传递过来的不是排好序的
                            if (e.subTags.indexOf(recTagBean) == e.subTags.size() - 1) {
                                if (industryOther != null) {
                                    industryListBean.list.add(industryOther);
                                }
                            }
                        }

                    }
                    allIndustryInner.add(industryListBean);
                });
        allIndustry = allIndustryInner;
        return allIndustryInner;
    }

    public List<Industry> getAllIndustryList() {
        if (allIndustry.size() == 0) {
            return getAllIndustriesConvertData();
        } else {
            return allIndustry;
        }
    }

    private Map<Integer, Industry> getAllIndustryMap() {
        if (allIndustryMap.size() == 0) {
            List<Industry> industryList = getAllIndustriesConvertData();
            Map<Integer, Industry> map = new HashMap<>();
            industryList.forEach(e -> {
                Industry industry = new Industry();
                industry.id = e.id;
                industry.name = e.name;
                map.put(industry.id, industry);
                Optional.ofNullable(e.list)
                        .orElseGet(ArrayList::new)
                        .forEach(f -> {
                            Industry industrySub = new Industry();
                            industrySub.id = f.id;
                            industrySub.name = f.name;
                            industrySub.fid = e.id;
                            map.put(industrySub.id, industrySub);
                        });
            });
            allIndustryMap = map;
            return map;
        } else {
            return allIndustryMap;
        }
    }

    /**
     * 外部使用方法
     ***************************************************************************/
    public List<Industry> getAllFirstIndustryList() {
        List<Industry> industryList = getAllIndustryList();
        return industryList.stream()
                .map(e -> {
                    Industry industry = new Industry();
                    industry.id = e.id;
                    industry.name = e.name;
                    return industry;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据领域id获取领域信息
     *
     * @param industryId
     * @return
     */
    public Industry getIndustryById(Integer industryId) {
        if (NumberUtil.isInValidId(industryId)) {
            return null;
        }
        Map<Integer, Industry> map = getAllIndustryMap();
        return map.get(industryId);
    }

    public String getIndustryNameById(Integer industryId) {
        Industry industry = getIndustryById(industryId);
        if (industry != null) {
            return industry.name;
        }
        return null;
    }


    /**
     * 清洗
     */

    public void cleanBpIndustryData() {
        Map<Integer, RecTagBean> cleanMap = getCleanMap();
        logger.info("执行项目领域清洗功能");
        try {
            boolean isContinue = true;
            int page = 1;
            int size = 200;
            while (isContinue) {
                ProjectBackupInfoExample bpExample = new ProjectBackupInfoExample();
                bpExample.setLimit(size);
                bpExample.setOffset((page - 1) * size);
                List<ProjectBackupInfo> bps = projectBackupInfoMapper.selectByExample(bpExample);
                if (StringUtil.isEmpty(bps)) {
                    isContinue = false;
                } else {
                    for (ProjectBackupInfo bp : bps) {
                        if (NumberUtil.isValidId(bp.getIndustryId())) {
                            RecTagBean recTagBean = cleanMap.get(bp.getIndustryId());
                            if (recTagBean != null) {
                                ProjectBackupInfo info = new ProjectBackupInfo();
                                info.setId(bp.getId());
                                info.setIndustryId(recTagBean.id);
                                projectBackupInfoMapper.updateByPrimaryKeySelective(info);
                            }
                        }
                    }
                }
                page++;
            }
        } catch (Exception e) {
            logger.info("执行项目领域清洗功能异常");
        }
        logger.info("执行项目领域清洗功能结束");
    }

    private Map<Integer, RecTagBean> getCleanMap() {
        Map<Integer, RecTagBean> cleanMap = new HashMap<>();
        PageData<RecTagBean> pageData = getAllIndustriesFromRemote();
        Optional.ofNullable(pageData.list)
                .orElseThrow(() -> new CodeException(new ResultCodes.DataCode(ResultCodes.Code.COMMON_ERROR_PARAMS,
                        "领域列表异常")))
                .stream()
                .filter(e -> NumberUtil.isValidId(e.businessId))
                .forEach(e -> {
                    cleanMap.put(e.businessId, e);
                    if (StringUtil.isNotEmpty(e.subTags)) {
                        for (RecTagBean recTagBean : e.subTags) {
                            if (NumberUtil.isInValidId(recTagBean.businessId)) {
                                continue;
                            }
                            if (NumberUtil.isInValidId(recTagBean.id)) {
                                continue;
                            }
                            if (StringUtil.isEmpty(recTagBean.name)) {
                                continue;
                            }
                            if (recTagBean.name.contains("其他") || recTagBean.name.contains("其它")) {
                                recTagBean.name = "其他";
                            }
                            cleanMap.put(recTagBean.businessId, recTagBean);
                        }

                    }
                });
        return cleanMap;
    }
}
