package com.welian.service.impl;

import com.welian.beans.cloudevent.Ticket;
import com.welian.ApplicationLauncher;
import com.welian.service.SolrService;
import org.sean.framework.util.Logger;
import com.welian.utils.EventSolrBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopu on 2017/10/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class SolrTest {

    private static final Logger logger = Logger.newInstance(SolrTest.class);

    @Autowired
    private SolrService solrService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void deleteAllSolrData() throws Exception {
        solrService.deleteAllSolrDataByQuery();
    }

    @Test
    public void addEventAllSolrData() throws Exception {
        List<EventSolrBean> solrBeanList = new ArrayList<>();

        solrService.addAllEventToSolr(solrBeanList);
    }

    @Test
    public void testapi() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.price = new Double(496000);

        Ticket ticket2 = new Ticket();
        ticket2.price = new Double(100);

        Ticket ticket3 = new Ticket();
        ticket3.price = new Double(6400);

        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);

        Double amount = new Double(0);
        for (int i = 0 ; i < ticketList.size() ; i++) {
            Ticket ticket = ticketList.get(i);
            if (i == 0) {
                amount = ticket.price;
            }else {
                int retval = amount.compareTo(ticket.price);
                if (retval > 0) {
                    amount = ticket.price;
                }
            }
        }
        String amountStr = "￥" + (amount / 100 ) + "起";

        logger.info("amountStr================================" + amountStr);
    }

    @Test
    public void addSolrData() throws Exception {
        EventSolrBean solrBean = new EventSolrBean();
        solrBean.id = "128805";
        solrBean.title = "产品从0到1及1到N的那些坑";
        solrBean.start_time = Long.valueOf("1532237400000") ;
        solrBean.end_time =Long.valueOf("1532250000000") ;
        solrBean.type = 0;
        solrBean.joined_count = 14;
        solrBean.joined_count_status =  1 ;
        solrBean.limited = 100;
        solrBean.ticket_type = 0;
        solrBean.sort_type = 0;
        solrBean.recommend_status = 1;
        solrBean.recommend_time = Long.valueOf("1531885017520");
        solrBean.sponsor = "35937";
        solrBean.sponsor_name = "混沌研习社8班";
        solrBean.state = 2;
        solrBean.city_name = "杭州";
        solrBean.city_id = 179;
        solrBean.logo = "http://image.welian.com/yxvr1531752283312_1080_640_349.jpg";
        solrBean.jumpUrl = "http://h5.welian.com/event/detail/MTMxOTg5ODUxNSYxMjg4MDU=";

        solrService.addEventToSolr(solrBean);
    }

}
