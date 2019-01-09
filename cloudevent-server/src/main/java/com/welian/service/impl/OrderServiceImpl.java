package com.welian.service.impl;

import com.welian.mapper.EventRecordMapper;
import com.welian.pojo.EventRecordExample;
import com.welian.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务，用来收费活动报名时，生成订单号的功能
 */
@Service("orderService")
public class OrderServiceImpl{

    @Autowired
    private EventRecordMapper eventRecordMapper;


    public String createOrderNumber() {
        boolean flag = true;
        String key = null;
        while (flag) {
            key = CommonUtil.getRandomNumber(10);
            EventRecordExample example = new EventRecordExample();
            example.createCriteria().andOrderNumberEqualTo(key);
            long count = eventRecordMapper.countByExample(example);
            if (count == 0) {
                flag = false;
            }
        }
        return key;
    }


    public String createTicketNumber() {
        boolean flag = true;
        String key = null;
        while (flag) {
            key = CommonUtil.getRandomNumber(10);
            EventRecordExample example = new EventRecordExample();
            example.createCriteria().andTicketNumberEqualTo(key);
            long count = eventRecordMapper.countByExample(example);
            if (count == 0) {
                flag = false;
            }
        }
        return key;
    }
}
