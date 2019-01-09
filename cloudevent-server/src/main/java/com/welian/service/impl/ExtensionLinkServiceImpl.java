package com.welian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.welian.beans.cloudevent.EventUrlResp;
import com.welian.beans.cloudevent.ExtensionLinkListResultResp;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.urls.Url;
import com.welian.beans.urls.UrlListReq;
import com.welian.beans.urls.UrlListResp;
import com.welian.beans.urls.UrlReq;
import com.welian.beans.urls.UrlType;
import com.welian.client.urls.UrlClient;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import com.welian.pojo.custom.CreateLinkParam;
import com.welian.utils.Base64Utils;
import com.welian.utils.CommonUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by memorytale on 2017/7/10.
 */
@Service("extensionLinkService")
public class ExtensionLinkServiceImpl {
    private static final Logger logger = Logger.newInstance(ExtensionLinkServiceImpl.class);
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private UrlClient urlClient;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;



    public ExtensionLink createDefaultExtensionLinkChannelEventId(Integer eventId) {
        ExtensionLinkReq reqLink = new ExtensionLinkReq();
        reqLink.eventId = eventId;
        reqLink.extensionLinkName = "默认";
        reqLink.linkType = SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue();
        return addLink(reqLink);
    }


    public ExtensionLink createExtensionLinkChannelByEventId(Integer eventId, String name) {
        ExtensionLinkReq reqLink = new ExtensionLinkReq();
        reqLink.eventId = eventId;
        reqLink.extensionLinkName = name;
        reqLink.linkType = SqlEnum.LINKTYPE.TYPE_USER.getValue();
        return addLink(reqLink);
    }


    public int deleteExtensionLink(Integer extensionLinkId) {
        //没有此需求，以后可能会有
        return 0;
    }


    public Object reviseLinkJson(ExtensionLinkReq req) {
        return reviseLink(req);
    }


    public Object reviseLink(ExtensionLinkReq req) {
        judgeLinkNameSame(req);
        //保存修改的推广链接
        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andIdEqualTo(req.extensionLinkId);
        ExtensionLink link = new ExtensionLink();
        link.setLinkName(req.extensionLinkName);
        int result = extensionLinkMapper.updateByExampleSelective(link, example);
        if (result == 0) {
            throw ExceptionUtil.createParamException("修改失败");
        }
        return null;
    }


    public Object addLinkJson(ExtensionLinkReq req) {
        ExtensionLink resp = createExtensionLinkChannelByEventId(req.eventId, req.extensionLinkName);
        ExtensionLinkResp extensionLinkResp = new ExtensionLinkResp();
        extensionLinkResp.extensionLinkId = resp.getId();
        return extensionLinkResp;
    }


    public Object getLinkListByEventIdJson(ExtensionLinkReq req) {
        ExtensionLinkListResultResp resultResp = new ExtensionLinkListResultResp();
        //根据活动的id分页获取推广链接渠道
        List<ExtensionLink> extensionLinks = getLinkByEventId(req);
        //组装
        List<ExtensionLinkResp> extensionLinkResps = new ArrayList<>();
        if (extensionLinks == null || extensionLinks.isEmpty()) {
            resultResp.list = new ArrayList<>();
        }
        ExtensionLinkResp extensionLinkResp;
        for (ExtensionLink extensionLink : extensionLinks) {
            extensionLinkResp = new ExtensionLinkResp();
            extensionLinkResp.extensionLinkId = extensionLink.getId();
            extensionLinkResp.extensionLinkName = extensionLink.getLinkName();
            //根据传入的不同类型，返回不同类型的链接地址
            if (req.linkListType.intValue() ==
                    ParamEnum.ChooseLinkListType.TYPE_CHOOSE_COMMON.getValue()) {
                extensionLinkResp.extensionLinkUrl = extensionLink.getLinkUrlCommon();
            } else if (req.linkListType.intValue() ==
                    ParamEnum.ChooseLinkListType.TYPE_CHOOSE_CUSTOM.getValue()) {
                extensionLinkResp.extensionLinkUrl = extensionLink.getLinkUrlCustom();
            } else if (req.linkListType.intValue() ==
                    ParamEnum.ChooseLinkListType.TYPE_CHOOSE_FORM.getValue()) {
                extensionLinkResp.extensionLinkUrl = extensionLink.getLinkUrlForm();
            }
            if (extensionLink.getLinkType() != null) {
                extensionLinkResp.linkType = (int) extensionLink.getLinkType();
            }
            extensionLinkResps.add(extensionLinkResp);
        }
        resultResp.list = extensionLinkResps;
        ExtensionLinkExample extensionLinkExample = new ExtensionLinkExample();
        extensionLinkExample.createCriteria().andEventIdEqualTo(req.eventId).andStateEqualTo((byte) 0);
        resultResp.count = (int) extensionLinkMapper.countByExample(extensionLinkExample);
        return resultResp;
    }


    public List<ExtensionLink> getLinkByEventId(ExtensionLinkReq req) {
        //获取所有的推广链接
        ExtensionLinkExample extensionLinkExample = new ExtensionLinkExample();
        extensionLinkExample.createCriteria().andStateEqualTo((byte) 0).andEventIdEqualTo(req.eventId);
        extensionLinkExample.setOrderByClause("id asc");
        if (NumberUtil.isValidId(req.size)) {
            extensionLinkExample.setLimit(req.size);
            if (NumberUtil.isValidId(req.page)) {
                extensionLinkExample.setOffset(PagingUtil.getStart(req.page, req.size));
            }
        }
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(extensionLinkExample);
        return extensionLinks;
    }


    public Object getDefaultLinkJson(ExtensionLinkReq req) {
        ExtensionLink extensionLink = getDefaultLinkByEventId(req.eventId);

        ExtensionLinkResp extensionLinkResp = new ExtensionLinkResp();
        extensionLinkResp.commonUrl = extensionLink.getLinkUrlCommon();
        extensionLinkResp.formUrl = extensionLink.getLinkUrlForm();
        extensionLinkResp.customUrl = extensionLink.getLinkUrlCustom();
        extensionLinkResp.extensionLinkId = extensionLink.getId();
        return extensionLinkResp;
    }


    public ExtensionLink getDefaultLinkByEventId(Integer eventId) {
        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andEventIdEqualTo(eventId).andStateEqualTo((byte) 0)
                .andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue());
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(example);
        if (extensionLinks == null || extensionLinks.isEmpty()) {
            throw ExceptionUtil.createParamException("无默认链接");
        }
        return extensionLinks.get(0);
    }

    /**
     * 获取多个活动的默认的渠道信息
     *
     * @param eventIds
     * @return
     */

    public  List<ExtensionLink> getDefaultLinkByEventIds(List<Integer> eventIds) {
        ExtensionLinkExample example = new ExtensionLinkExample();
        example.setDistinct(true);
        example.createCriteria().andEventIdIn(eventIds).andStateEqualTo((byte) 0)
                .andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue());
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(example);
        if (extensionLinks == null || extensionLinks.isEmpty()) {
            throw ExceptionUtil.createParamException("无默认链接");
        }
        return extensionLinks;
    }


    /**
     * 根据uniqueKey获得eventId，清洗url调用方法
     *
     * @param uniqueKeys
     * @return
     */

    public Map<String,Integer> getEventIdByUniquekeys(List<String> uniqueKeys) {
        if(StringUtil.isEmpty(uniqueKeys)){
            throw ExceptionUtil.createParamException("uniqueKey参数异常");
        }
        Map<String,Integer> resultMap=new HashMap<>();
        ExtensionLinkExample example=new ExtensionLinkExample();

        example.createCriteria().andUniqueKeyIn(uniqueKeys);
        List<ExtensionLink> extensionLinks=extensionLinkMapper.selectByExample(example);
        if(StringUtil.isEmpty(extensionLinks)){
            throw ExceptionUtil.createParamException("找不到任何extension");
        }
        for(ExtensionLink link:extensionLinks){
            resultMap.put(link.getUniqueKey(),link.getEventId());
        }

        return resultMap;
    }


    public EventUrlResp getResultUrl(ExtensionLinkReq req) {
        if(StringUtil.isEmpty(req.uniqueKey)){
            throw ExceptionUtil.createParamException("uniqueKey参数异常");
        }
        if(StringUtil.isEmpty(req.tradeNo)){
            throw ExceptionUtil.createParamException("tradeNo参数异常");
        }
        ExtensionLinkExample example=new ExtensionLinkExample();
        String uniquekey=isJson(Base64Utils.decodeToString(req.uniqueKey))?
                JSONUtil.json2Obj(Base64Utils.decodeToString(req.uniqueKey),ExtensionLink.class).getUniqueKey():
                Base64Utils.decodeToString(req.uniqueKey).split("&")[0];
        //兼容这里会可能传来eventId的情况
        if(uniquekey.length()>9){
            example.createCriteria().andUniqueKeyEqualTo(uniquekey);
        }else{
            example.createCriteria().andEventIdEqualTo(Integer.parseInt(uniquekey));
        }
        List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(example);
        if (StringUtil.isEmpty(extensionLinkList)) {
            throw ExceptionUtil.createParamException("extensionLink不存在");
        }
        ExtensionLink extensionLink = extensionLinkList.get(0);
        EventRecordExample eventRecordExample=new EventRecordExample();
        eventRecordExample.createCriteria().andTradeNoEqualTo(req.tradeNo).andEventIdEqualTo(extensionLink.getEventId());

        List<EventRecord> eventRecords=eventRecordMapper.selectByExample(eventRecordExample);
        if (StringUtil.isEmpty(eventRecords)) {
            throw ExceptionUtil.createParamException("eventRecord不存在");
        }
        EventRecord record=eventRecords.get(0);
        EventUrlResp response=new EventUrlResp();
        ExtensionLinkResp resp=new ExtensionLinkResp();
        resp.extensionLinkId=extensionLink.getId();
        resp.commonUrl=extensionLink.getLinkUrlCommon();
        response.extensionLink=resp;
        response.ticketUrl=record.getTicketUrl();
        response.recordIdStr=Base64Utils.encode(record.getId().toString().getBytes());
        response.recordId=record.getId();
        response.eventId=record.getEventId();
        switch (record.getState().intValue()){
            case 0:response.resultStr=Constant.RESULT_STRING_DEFAULT;
            break;
            case 3:response.resultStr=Constant.RESULT_STRING_CHECK;
            break;
            case 4:response.resultStr=Constant.RESULT_STRING_FAIL;
            break;
        }
        return response;
    }

    /**
     * 为事件活动添加推广链接
     *
     * @param req
     * @return
     */

    public ExtensionLink addLink(ExtensionLinkReq req) {
        judgeLinkNameSame(req);
        //保存推广链接
        CreateLinkParam createLinkParam = new CreateLinkParam();
        createLinkParam.extensionLinkName = req.extensionLinkName;
        createLinkParam.linkType = req.linkType;
        createLinkParam.eventId = req.eventId;
        ExtensionLink extensionLink = createLink(createLinkParam);
        //获取当前活动所有的链接个数
        ExtensionLinkExample extensionLinkExample = new ExtensionLinkExample();
        extensionLinkExample.createCriteria().andStateEqualTo((byte) 0).andEventIdEqualTo(req.eventId);
        int count = (int) extensionLinkMapper.countByExample(extensionLinkExample);
        //事件活动的推广链接个数更新
        Event eventActivityUpdate = new Event();
        eventActivityUpdate.setId(req.eventId);
        eventActivityUpdate.setLinkUrlCount(count);
        eventMapper.updateByPrimaryKeySelective(eventActivityUpdate);
        return extensionLink;
    }


    /**
     * 外部调用放放与内部调用方法分割线
     ******************************************************************************/

    /**
     * 判断同一个活动下的链接渠道名称是否有重名的问题
     *
     * @param req
     */
    private void judgeLinkNameSame(ExtensionLinkReq req) {
        if (null == req)
            return;
        ExtensionLinkExample example = new ExtensionLinkExample();
        if (NumberUtil.isValidId(req.extensionLinkId)) {
            example.createCriteria().andEventIdEqualTo(req.eventId)
                    .andLinkNameEqualTo(req.extensionLinkName).andStateEqualTo((byte) 0).andIdNotEqualTo(req
                    .extensionLinkId);
        } else {
            example.createCriteria().andEventIdEqualTo(req.eventId)
                    .andLinkNameEqualTo(req.extensionLinkName).andStateEqualTo((byte) 0);
        }
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(example);
        if (extensionLinks != null && !extensionLinks.isEmpty()) {
            throw ExceptionUtil.createParamException("已存在同名的推广链接");
        }
    }

    /**
     * 创建默认的链接
     *
     * @param req
     * @return
     */
    private ExtensionLink createLink(CreateLinkParam req) {
        String key = getUniqueKey();
        List<Url> urls = getShortList(key, req.eventId);
        ExtensionLink extensionLink = new ExtensionLink();
        Long time = System.currentTimeMillis();
        extensionLink.setCreateTime(time);
        extensionLink.setModifyTime(time);
        extensionLink.setEventId(req.eventId);
        extensionLink.setLinkType((byte) ((int) req.linkType));
        extensionLink.setLinkName(req.extensionLinkName);
        extensionLink.setUniqueKey(key);
        extensionLink.setLinkUrlCommon(urls.get(0).shortUrl);
        extensionLink.setLinkUrlForm(urls.get(1).shortUrl);
        extensionLink.setLinkUrlCustom(urls.get(2).shortUrl);
        int result = extensionLinkMapper.insertSelective(extensionLink);
        if (result == 0) {
            throw ExceptionUtil.createParamException("保存推广链接失败");
        }
        return extensionLink;
    }

    private List<Url> getShortList(String key, Integer eventId) {
        Event event=eventMapper.selectByPrimaryKey(eventId);
        UrlReq urlReqCommon = new UrlReq();
        String urlForm = cloudEventConfig.getLink_form_prefix() +
                Base64Utils.encode((key+"&"+eventId).getBytes()).toString();
        urlReqCommon.url = cloudEventConfig.getLink_common_prefix() +
                Base64Utils.encode((key+"&"+eventId).getBytes()).toString() ;
        if(event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())){
            urlForm = cloudEventConfig.getLink_common_prefix() +
                    Base64Utils.encode((key+"&"+eventId).getBytes()).toString()+"#/confirm";
        }
        String urlCustom = cloudEventConfig.getLink_custom_prefix() +
                Base64Utils.encode((key+"&"+eventId).getBytes()).toString();

        UrlListReq req = new UrlListReq();
        req.urls = new ArrayList<>();
        urlReqCommon.type = UrlType.NORMAL;
        urlReqCommon.source = cloudEventConfig.getUrl_req_source();

        UrlReq urlReqForm = new UrlReq();
        urlReqForm.url = urlForm;
        urlReqForm.type = UrlType.NORMAL;
        urlReqForm.source = cloudEventConfig.getUrl_req_source();

        UrlReq urlReqCustom = new UrlReq();
        urlReqCustom.url = urlCustom;
        urlReqCustom.type = UrlType.NORMAL;
        urlReqCustom.source = cloudEventConfig.getUrl_req_source();

        req.urls.add(urlReqCommon);
        req.urls.add(urlReqForm);
        req.urls.add(urlReqCustom);

        BaseResult<UrlListResp> baseResult = urlClient.getList(req);
        if (!baseResult.isSuccess()) {
            logger.debug("baseResult", baseResult.getErrormsg());
            throw ExceptionUtil.createParamException("创建短链失败");
        }
        if (baseResult.getData().urls == null
                || baseResult.getData().urls.isEmpty()
                || baseResult.getData().urls.size() != 3) {
            throw ExceptionUtil.createParamException("链接不存在");
        }
        return baseResult.getData().urls;
    }

    /**
     * 获取唯一的key
     *
     * @return
     */
    private String getUniqueKey() {
        boolean flag = true;
        String key = null;
        while (flag) {
            key = CommonUtil.getRandomNumber(10);
            ExtensionLinkExample example = new ExtensionLinkExample();
            example.createCriteria().andUniqueKeyEqualTo(key);
            List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(example);
            if (extensionLinks == null || extensionLinks.isEmpty()) {
                flag = false;
            }
        }
        return key;
    }

    public ExtensionLink getLinkByUniqueKey(String uniqueKey) {
        //链接信息
        ExtensionLinkExample extensionLinkExample = new ExtensionLinkExample();
        extensionLinkExample.createCriteria().andUniqueKeyEqualTo(uniqueKey).andStateEqualTo(
                (byte)
                        0);
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(extensionLinkExample);
        if (extensionLinks == null || extensionLinks.isEmpty()) {
            throw ExceptionUtil.createParamException("找不到推广链接");
        }
        return extensionLinks.get(0);
    }

    public boolean isJson(String content){

        try {
            JSONObject jsonStr= JSONObject.parseObject(content);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }
}
