package com.welian.service;

import com.alibaba.fastjson.JSONObject;
import com.welian.config.CloudEventConfig;
import com.welian.solr.QuerySolrFactory;
import com.welian.solr.SolrPageParam;
import org.sean.framework.util.Logger;
import com.welian.utils.EventSolrBean;
import com.welian.utils.PagingUtil;
import com.welian.utils.SolrClientUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhaopu on 2017/10/25.
 */
@Service
public class SolrService {
    private static final Logger logger = Logger.newInstance(SolrService.class);

    @Autowired
    private CloudEventConfig cloudEventConfig;

    /**
     * 添加指定活动的数据到solr
     *
     * @param solrBean
     */
    public void addEventToSolr(EventSolrBean solrBean) {
        if (solrBean == null) {
            return;
        }
        Collection<EventSolrBean> solrBeanC = new ArrayList<>();
        solrBeanC.add(solrBean);
        if (solrBeanC != null) {
            SolrClientUtil clientUtil = new SolrClientUtil(cloudEventConfig.getSolr_url());
            //添加数据
            clientUtil.addAll(solrBeanC);
            //关闭连接
            clientUtil.close();
        }
    }

    /**
     * 批量添加数据到solr
     */
    public void addAllEventToSolr(List<EventSolrBean> solrBeanList) {
        Collection<EventSolrBean> solrBeanListR = new ArrayList<>();
        for (EventSolrBean solrBean : solrBeanList) {
            EventSolrBean bean = solrBean;
            if (bean != null) {
                solrBeanList.add(bean);
            }
        }

        if (solrBeanListR != null && solrBeanListR.size() > 0) {
            SolrClientUtil clientUtil = new SolrClientUtil(cloudEventConfig.getSolr_url());
            //添加数据
            clientUtil.addAll(solrBeanListR);
            //关闭连接
            clientUtil.close();
        }
    }


    /**
     * 更新solr上面的指定数据
     *
     * @param id
     * @param fieldName
     * @param fieldValue
     */
    public void updateSolrInfoById(String id, String fieldName, Object fieldValue) {
        SolrClientUtil clientUtil = new SolrClientUtil(cloudEventConfig.getSolr_url());
        //添加数据
        clientUtil.updateField(id, fieldName, fieldValue);
        //关闭连接
        clientUtil.close();
    }

    /**
     * 删除指定Id的solr数据
     *
     * @param solrId
     */
    public void deleteSolrEventByPostId(String solrId) {
        SolrClientUtil clientUtil = new SolrClientUtil(cloudEventConfig.getSolr_url());
        // 删除查询的所有数据
        clientUtil.deleteByQuery("id:" + solrId);
        //关闭连接
        clientUtil.close();
    }


    /**
     * 删除所有solr上面的数据
     */
    public void deleteAllSolrDataByQuery() {
        SolrClientUtil clientUtil = new SolrClientUtil(cloudEventConfig.getSolr_url());
        // 删除查询的所有数据
        clientUtil.deleteByQuery("*:*");
        //关闭连接
        clientUtil.close();
    }


    public JSONObject pageActive(String keyWord, Integer page, Integer size) {
        SolrPageParam param = new SolrPageParam();
//        param.setCoreName(CoreConfig.ik_event.name());

        param.setFqArray(new String[]{"title:*" + keyWord + "* or sponsor_name:*" + keyWord + "*", "state:2 or 3",
                "recommend_status:1"});

        param.setStart(PagingUtil.getStart(page, size));
        param.setRows(size);


        // 指定返回字段
        String[] fields = { "id", "title", "type", "start_time", "end_time", "joined_count", "joined_count_status", "sort_type", "sponsor",
                "sponsor_name","recommend_status", "recommend_time", "state", "city_name", "city_id", "limited", "ticket_type", "logo" ,"amountStr","jumpUrl"};

        param.setFlArray(fields);


        // 排序
        List<SolrQuery.SortClause> sortList = new ArrayList<>();
        sortList.add(new SolrQuery.SortClause("start_time", SolrQuery.ORDER.desc));
        param.setSortList(sortList);

        return page(param);
    }

    private JSONObject page(SolrPageParam param) {
        JSONObject result = null;
        try {
            param.setSolrUrl(cloudEventConfig.getSolr_url());
            result = QuerySolrFactory.pageJsonCore(param);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }

}
