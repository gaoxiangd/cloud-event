package com.welian.service.impl;

import com.welian.ApplicationLauncher;
import com.welian.service.V1EventService;
import org.sean.framework.util.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by zhaopu on 2018/7/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TaskTest {

    private static final Logger logger = Logger.newInstance(SolrTest.class);

    @Autowired
    private V1EventService eventService;


    @Test
    public void taskDoEventEndDays() throws Exception {
        eventService.authSettlement(null);
    }


}
