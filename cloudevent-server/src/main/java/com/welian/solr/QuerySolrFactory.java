package com.welian.solr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.sean.framework.util.Logger;
import org.sean.framework.util.StringUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.DisMaxParams;

import java.util.List;
import java.util.Map;

/**
 * @author rui.mao
 * @Type QuerySolrFactory
 * @Desc
 * @date 2016/09/04
 */
public class QuerySolrFactory {
    private static final Logger LOG = Logger.newInstance(QuerySolrFactory.class);
    private static final int CONNECTION_TIMEOUT = 5000;


    /**
     * 搜索分页列表
     *
     * @param param
     * @return
     */
    public static JSONObject pageJsonCore(SolrPageParam param) {
        HttpSolrClient solrClient = null;
        try {
            LOG.info("solr搜索列表的参数:param={}", JSON.toJSONString(param));
            solrClient = new HttpSolrClient.Builder(param.getSolrUrl()).build();
            solrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            SolrQuery query = new SolrQuery();
            if (StringUtil.isEmpty(param.getQ())) {
                query.setQuery("*:*");
            } else {
                query.setQuery(param.getQ());
            }
            if ((param.getEdismax() == null || !param.getEdismax())
                    && param.getSortList() != null && param.getSortList().size() > 0) {
                for (SolrQuery.SortClause item : param.getSortList()) {
                    query.addSort(item);
                }
            }
            if (param.getFlArray() != null && param.getFlArray().length > 0) {
                query.set(CommonParams.FL, param.getFlArray());
            }
            if (param.getFqArray() != null && param.getFqArray().length > 0) {
                query.set(CommonParams.FQ, param.getFqArray());
            }
            //权重设置
            if (param.getEdismax() != null && param.getEdismax()) {
                query.set("defType", "edismax");
                query.add(DisMaxParams.PF, param.getPf());
                query.add(DisMaxParams.QF, param.getQf());
                query.add(DisMaxParams.BF, param.getBf());
            }
            //高亮设置
            if(param.getHlFl() != null && param.getHlFl()){
                query.setHighlight(true);
                query.set("hl",true);
                query.setHighlightSimplePre(param.getHlSimplePre());
                query.setHighlightSimplePost(param.getHlSimplePost());
                query.set("hl.fl", param.getFlFields());
            }
            query.setStart(param.getStart());
            query.setRows(param.getRows());
            QueryResponse response = solrClient.query(query, SolrRequest.METHOD.POST);

            SolrDocumentList docList = response.getResults();

            if (docList != null && docList.size() > 0) {

                JSONObject result = new JSONObject();
                JSONArray array = new JSONArray();

                if (param.getHlFl() != null && param.getHlFl()) {
                    String[] hlFieldAry = param.getFlFields().split(",");
                    Map<String, Map<String, List<String>>> hlResult = response.getHighlighting();
                    for (SolrDocument doc : docList) {
                        Object idObj = doc.getFieldValue("id");
                        String idValue = idObj == null ? "0" : idObj.toString();
                        Map<String, List<String>> hlFieldMap = hlResult.get(idValue);
                        if (hlFieldMap != null) {
                            for (String hlField : hlFieldAry) {
                                List<String> hlNewValue = hlFieldMap.get(hlField);
                                if(hlNewValue != null){
                                    doc.setField(hlField, hlNewValue.get(0));
                                }
                            }
                        }
                        array.add(doc);
                    }
                } else {
                    for (SolrDocument doc : docList) {
                        array.add(doc);
                    }
                }

                result.put("data", array);
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeHttpSolrClient(solrClient);
        }
    }

    /**
     * 搜索 单一对象
     *
     * @param param
     * @return
     */
    public static JSONObject findJsonObj(SolrSearchParam param) {
        HttpSolrClient solrClient = null;
        try {
                LOG.info("solr搜索单一对象的参数:param={}", JSON.toJSONString(param));
            solrClient = new HttpSolrClient.Builder(param.getSolrUrl()).build();
            solrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            SolrQuery query = new SolrQuery();
            if (StringUtil.isEmpty(param.getQ())) {
                query.setQuery("*:*");
            } else {
                query.setQuery(param.getQ());
            }
            if ((param.getEdismax() == null || !param.getEdismax())
                    && param.getSortList() != null && param.getSortList().size() > 0) {
                for (SolrQuery.SortClause item : param.getSortList()) {
                    query.addSort(item);
                }
            }
            if (param.getFlArray() != null && param.getFlArray().length > 0) {
                query.set(CommonParams.FL, param.getFlArray());
            }
            if (param.getFqArray() != null && param.getFqArray().length > 0) {
                query.set(CommonParams.FQ, param.getFqArray());
            }

            if (param.getEdismax() != null && param.getEdismax()) {
                query.set("edismax", true);
                query.add(DisMaxParams.PF, param.getPf());
                query.add(DisMaxParams.QF, param.getQf());
                query.add(DisMaxParams.BF, param.getBf());
            }
            QueryResponse response = solrClient.query(query, SolrRequest.METHOD.POST);
            SolrDocumentList docList = response.getResults();
            if (docList != null && docList.size() > 0) {
                JSONObject result = new JSONObject();
                result.put("data", docList.get(0));
                return result;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeHttpSolrClient(solrClient);
        }
    }

    /**
     * 查询数量
     *
     * @param param
     * @return
     */
    public static long countByCore(SolrSearchParam param) {
        HttpSolrClient solrClient = null;
        try {
                LOG.info("solr查询数量的参数:param={}", JSON.toJSONString(param));
            solrClient = new HttpSolrClient.Builder(param.getSolrUrl()).build();
            solrClient.setConnectionTimeout(CONNECTION_TIMEOUT);
            SolrQuery query = new SolrQuery();
            if (StringUtil.isEmpty(param.getQ())) {
                query.setQuery("*:*");
            } else {
                query.setQuery(param.getQ());
            }
            if ((param.getEdismax() == null || !param.getEdismax())
                    && param.getSortList() != null && param.getSortList().size() > 0) {
                for (SolrQuery.SortClause item : param.getSortList()) {
                    query.addSort(item);
                }
            }
            if (param.getFqArray() != null && param.getFqArray().length > 0) {
                query.set(CommonParams.FQ, param.getFqArray());
            }
            if (param.getEdismax() != null && param.getEdismax()) {
                query.set("edismax", true);
                query.add(DisMaxParams.PF, param.getPf());
            }
            QueryResponse response = solrClient.query(query, SolrRequest.METHOD.POST);
            return response.getResults().getNumFound();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeHttpSolrClient(solrClient);
        }
    }

    private static void closeHttpSolrClient(HttpSolrClient solrClient) {
        if (solrClient != null) {
            try {
                solrClient.close();
            } catch (Exception e) {
                LOG.error("关闭HttpSolrServer出错", e);
            }
        }
    }

}
