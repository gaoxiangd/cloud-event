package com.welian.service.impl;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordCollectionExample;
import com.welian.service.CustomFormModuleService;
import org.sean.framework.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dayangshu on 17/7/14.
 */
@Service("eventRecordCollectionService")
public class EventRecordCollectionServiceImpl{
    @Autowired
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    @Autowired
    private CustomFormModuleService customFormModuleService;


    public List<EventRecordCollection> getEventRecordCollectionList(Integer eventRecordId) {
        List<EventRecordCollection> eventRecordCollectionList = new ArrayList<>();
        if (NumberUtil.isValidId(eventRecordId)) {
            EventRecordCollectionExample example = new EventRecordCollectionExample();
            example.createCriteria().andEventRecordIdEqualTo(eventRecordId);
            eventRecordCollectionList = eventRecordCollectionMapper.selectByExample(example);
        }
        return eventRecordCollectionList;
    }


    public AddtionalForm converseData(EventRecordCollection eventRecordCollection, Map<Integer, EventCustomField> map) {
        AddtionalForm addtionalForm = new AddtionalForm();
        if (eventRecordCollection != null) {
            if (map != null && map.get(eventRecordCollection.getCollectionId()) != null) {
                addtionalForm.label = map.get(eventRecordCollection.getCollectionId()).getTitle();
                addtionalForm.type = map.get(eventRecordCollection.getCollectionId()).getFieldType();
            }
            addtionalForm.value = eventRecordCollection.getContent();
        }
        return addtionalForm;
    }


    public List<AddtionalForm> converseDataList(List<EventRecordCollection> eventRecordCollectionList) {
        List<AddtionalForm> addtionalFormList = new ArrayList<>();
        if (eventRecordCollectionList != null && eventRecordCollectionList.size() > 0) {
            List<Integer> collectionIdList = new ArrayList<>();
            for (EventRecordCollection eventRecordCollection : eventRecordCollectionList) {
                collectionIdList.add(eventRecordCollection.getCollectionId());
            }
            Map<Integer, EventCustomField> map = customFormModuleService.getEventCustomFieldMap(collectionIdList);
            for (EventRecordCollection eventRecordCollection : eventRecordCollectionList) {
                addtionalFormList.add(converseData(eventRecordCollection, map));
            }
        }
        return addtionalFormList;
    }

}
