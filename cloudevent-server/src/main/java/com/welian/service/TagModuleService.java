package com.welian.service;

import com.welian.beans.cloudevent.Tag;
import com.welian.beans.tag.TagReq;
import com.welian.beans.tag.TagResp;
import com.welian.client.tag.TagBusinessClient;
import com.welian.client.tag.TagClient;
import com.welian.config.Constant;
import com.welian.pojo.BaseModule;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayangshu on 17/7/7.
 */
@Service
public class TagModuleService extends BaseModule<com.welian.beans.cloudevent.Tag> {
    @Autowired
    private TagBusinessClient tagBusinessClient;
    @Autowired
    private TagClient tagClient;


    public void save(Integer eventId, List<com.welian.beans.cloudevent.Tag> tags) {
        if (tags != null && tags.size() > 0) {
            TagReq tagReqRemote = new TagReq();
            tagReqRemote.relationId = Long.valueOf(eventId);

            List<TagReq> tagReqList = new ArrayList<>();
            for (com.welian.beans.cloudevent.Tag tagReq : tags) {
                TagReq tagReqTmp = new TagReq();
                if (NumberUtil.isValidId(tagReq.id)) {
                    tagReqTmp.tagId = Long.valueOf(tagReq.id);
                }
                tagReqTmp.tagName = tagReq.name;
                tagReqList.add(tagReqTmp);
            }
            tagReqRemote.list = tagReqList;

            tagBusinessClient.sendCloudEventTagInsertAndRelation(tagReqRemote);
        }
    }

    public void delete(Integer id) {
        TagReq tagReq = new TagReq();
        tagReq.type = Constant.TAG_TYPE;
        tagReq.relationId = Long.valueOf(id);
        tagClient.deleteRelation(tagReq);
    }


    public Object get(Integer eventId) {
        TagReq tagReq = new TagReq();
        tagReq.relationId = (long) eventId;
        tagReq.type = Constant.TAG_TYPE;
        BaseResult result = tagClient.sendGetTagById(tagReq);
        return result.getData();
    }


    public Tag conversePara(Object Object) {
        if (Object != null) {
            Tag tag = new Tag();
            tag.id = ((TagResp) Object).tagId.intValue();
            tag.name = ((TagResp) Object).tagName;
            return tag;
        }
        return null;
    }

    public List converseParaList(List<TagResp> tagList) {
        List<Tag> tagListTmp = new ArrayList<>();
        if (tagList != null && tagList.size() > 0) {
            for (TagResp tag : tagList) {
                tagListTmp.add(conversePara(tag));
            }
        }
        return tagListTmp;

    }

    public List<Tag> getDefaultTag() {
        TagReq tagReq = new TagReq();
        tagReq.orgId = 0L;
        BaseResult<TagResp> result = tagBusinessClient.sendGetCloudEventTag(tagReq);
        return converseParaList(result.getData().list);
    }


}
