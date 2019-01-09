package com.welian.service;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.mapper.EventCustomFieldMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义表单模块service
 */
@Service
public class CustomFormModuleService extends BaseModule<AddtionalForm> {
    @Autowired
    private EventCustomFieldMapper eventCustomFieldMapper;


    public void save(Integer eventId, List<AddtionalForm> forms) {
        if (StringUtil.isNotEmpty(forms)) {
            List<EventCustomField> eventCustomFieldList = new ArrayList<>();
            for (AddtionalForm addtionalFormReq : forms) {
                EventCustomField eventCustomField = new EventCustomField();
                eventCustomField.setEventId(eventId);
                eventCustomField.setFieldType(addtionalFormReq.type);
                eventCustomField.setTitle(addtionalFormReq.label);
                eventCustomField.setCreateTime(System.currentTimeMillis());
                eventCustomField.setModifyTime(System.currentTimeMillis());
                eventCustomField.setLimitCount(addtionalFormReq.limit);
                eventCustomField.setRequired(addtionalFormReq.required);
                eventCustomFieldList.add(eventCustomField);
            }
            eventCustomFieldMapper.insertByBatch(eventCustomFieldList);
        }
    }


    public void delete(Integer eventId) {
        EventCustomFieldExample eventCustomFieldExample = new EventCustomFieldExample();
        eventCustomFieldExample.createCriteria().andEventIdEqualTo(eventId);
        eventCustomFieldMapper.deleteByExample(eventCustomFieldExample);
    }


    public Object get(Integer eventId) {
        EventCustomFieldExample example = new EventCustomFieldExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        return eventCustomFieldMapper.selectByExample(example);
    }


    public AddtionalForm conversePara(Object object) {
        if (object != null) {
            AddtionalForm addtionalForm = new AddtionalForm();
            addtionalForm.type = ((EventCustomField) object).getFieldType();
            addtionalForm.label = ((EventCustomField) object).getTitle();
            addtionalForm.id = ((EventCustomField) object).getId();
            addtionalForm.required = ((EventCustomField) object).getRequired();
            addtionalForm.limit = ((EventCustomField) object).getLimitCount();
            return addtionalForm;
        }
        return null;
    }

    public List converseParaList(List<EventCustomField> eventCustomFieldList) {
        List<AddtionalForm> addtionalFormList = new ArrayList<>();
        if (eventCustomFieldList != null && eventCustomFieldList.size() > 0) {
            for (EventCustomField eventCustomField : eventCustomFieldList) {
                addtionalFormList.add(conversePara(eventCustomField));
            }
        }
        return addtionalFormList;
    }

    public Map<Integer, EventCustomField> getEventCustomFieldMap(List<Integer> idList) {
        Map<Integer, EventCustomField> map = new HashMap<>();
        if (StringUtil.isNotEmpty(idList)) {
            EventCustomFieldExample eventCustomFieldExample = new EventCustomFieldExample();
            eventCustomFieldExample.createCriteria().andIdIn(idList);
            List<EventCustomField> eventCustomFieldList = eventCustomFieldMapper.selectByExample(eventCustomFieldExample);
            for (EventCustomField eventCustomField : eventCustomFieldList) {
                map.put(eventCustomField.getId(), eventCustomField);
            }
        }
        return map;
    }


}
