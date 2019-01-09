package com.welian.service.impl;

import com.welian.ApplicationLauncher;
import com.welian.enums.wallet.message.PaySuccessMessage;
import com.welian.mapper.SmsManageOrderMapper;
import com.welian.pojo.SmsManageOrder;
import com.welian.pojo.SmsManageOrderExample;
import com.welian.service.EventKafkaService;
import com.welian.utils.ExceptionUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sean.framework.text.WordFilter;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by GaoXiang on 2018/6/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class SendSmsTest {

    @Autowired
    private EventKafkaService eventKafkaService;
    @Autowired
    private SmsManageOrderMapper smsManageOrderMapper;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void filterContent() {
        String content = "感谢参加 [链友圈]关于中早期项目融资分享沙龙 的线下活动，活动时间：2018-04-17 14:00:00 ~ 2018-04-17 16:30:00，活动地址：OCG国际中心B座18F。不见不散～";
        WordFilter.FilterResult result = WordFilter.filterWords(content);
        if (result.hasBlackWord) {
            throw ExceptionUtil.createParamException("发送的短信含有敏感词'" + getDistinctString(result.blackWords) + "'，请重新编辑");
        }

    }


    private String getDistinctString(String blackWords) {
        if (blackWords == null || StringUtil.isEmpty(blackWords.trim())) {
            return blackWords;
        }
        Set setResult = new HashSet(Arrays.asList(blackWords.trim().split(",")));
        List listResult = new ArrayList<>(setResult);
        return String.join(",", listResult);
    }


    @Test
    public void insertSmsMangeOrder() {
        SmsManageOrder smsManageOrder=new SmsManageOrder();
        smsManageOrder.setState(2);
        smsManageOrder.setModifyTime(System.currentTimeMillis());
        smsManageOrder.setCreateTime(System.currentTimeMillis());
        smsManageOrder.setOrgId(1523);
        smsManageOrder.setSmsCount(20);
        smsManageOrder.setPrice(200);
        smsManageOrder.setUid(511211);
        smsManageOrder.setTradeNo(StringUtil.getRandomNumber(18,true));
        smsManageOrder.setWalletTradeNo(StringUtil.getRandomNumber(17,true));
        smsManageOrderMapper.insertSelective(smsManageOrder);
        PaySuccessMessage message = new  PaySuccessMessage();
        message.setBusinessTradeNo(smsManageOrder.getTradeNo());
        message.setState(1);
        message.setAmount("10");
        message.setPayTime(System.currentTimeMillis());
        message.setPayType(2);
        message.setTradeNo(smsManageOrder.getWalletTradeNo());
        message.setModule(8);
        eventKafkaService.insertSmsMangeOrder(message);

    }

}
