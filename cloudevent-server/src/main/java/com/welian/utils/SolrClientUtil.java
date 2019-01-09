package com.welian.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeException;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaopu on 2017/10/25.
 */
public class SolrClientUtil {

    private static final Logger logger = Logger.newInstance(SolrClientUtil.class);

    private String solrUrl;
    // solrClient类
    private SolrClient solrClient;


    /**
     * 创建新的SolrClient
     *
     * @return SolrClient
     */
    private SolrClient createNewSolrClient() {
        try {
            System.out.println("server address:" + solrUrl);
            // 负载均衡的HttpSolrClient,通过HTTP直接连接到Solr服务器
            HttpSolrClient client = new HttpSolrClient(solrUrl);
            client.setConnectionTimeout(30000);
            client.setDefaultMaxConnectionsPerHost(100);
            client.setMaxTotalConnections(100);
            client.setSoTimeout(30000);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 关闭连接
     */
    public void close() {
        try {
            solrClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public SolrClientUtil(String solrUrl) {
        this.solrUrl = solrUrl;
        this.solrClient = createNewSolrClient();
    }


    /**
     * 第一种方式，添加 以document方式 添加数据
     * 第二种方式，添加 Bean的方式 添加数据
     *
     * @param objects
     */
    public void addAll(Collection<? extends Object> objects) {
        if (objects == null || objects.size() == 0) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "参数错误");
        }

        logger.info("======================add doc ===================");
        try {
            //添加给solr
            UpdateResponse resp = null;
            if (objects.iterator().next() instanceof SolrInputDocument) {
                resp = solrClient.add((Collection<SolrInputDocument>) objects);
            } else {
                resp = solrClient.addBeans(objects);
            }
            if (resp != null) {
                logger.info("Add doc size" + objects.size() + " result:" + resp.getStatus() + " Qtime:" + resp.getQTime());
            }

            UpdateResponse rspcommit = solrClient.commit();
            logger.info("commit doc to index" + " result:" + resp.getStatus() + " Qtime:" + resp.getQTime());
        } catch (SolrServerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    /**
     * 根据条件获取solr查询结果
     *
     * @param query
     * @param fq
     * @param facet
     * @param facetField
     * @param facetQuery
     * @return QueryResponse
     */
    public QueryResponse solrSearchData(String query, String fq, boolean facet, String facetField, String facetQuery) {
        SolrQuery params = new SolrQuery();
        System.out.println("======================query===================");
        if (StringUtil.isEmpty(query)) {
            params.setQuery("*:*");// 查询所有
            //params.set("q", "*:*");// 查询所有
        } else {
            // 多条件查询 p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0
            params.setQuery(query);
            //params.set("q", "p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0");// 查询所有
        }

        // 过滤查询
        if (!StringUtil.isEmpty(fq)) {
            params.set("fq", fq);
        }

        //params.set("indent", true);
        // 设置返回的数据格式
        params.set("wt", "json");

        //// 排序方式
        params.set("sort", "startTime desc");

        // 分类查询
        if (facet) {
            params.setFacet(facet);
            if (!StringUtil.isEmpty(facetField)) {
                params.addFacetField(facetField);
            }
            if (!StringUtil.isEmpty(facetQuery)) {
                params.addFacetQuery(facetQuery);
            }
        }

        // 查询的条数
        //Integer start = NumberUtil.isValidId(startPage) ? startPage : 0;
        //Integer count = NumberUtil.isValidId(rows) ? rows : 30;
        //params.setStart(start);
        //params.setRows(count);
        try {
            QueryResponse resp = solrClient.query(params);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询指定条件的所有数据，不分页
     *
     * @param query
     * @param fq
     * @return SolrDocumentList
     */
    public SolrDocumentList queryDocs(String query, String fq) {
        SolrQuery params = new SolrQuery();
        System.out.println("======================query===================");
        if (StringUtil.isEmpty(query)) {
            params.setQuery("*:*");// 查询所有
            //params.set("q", "*:*");// 查询所有
        } else {
            // 多条件查询 p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0
            params.setQuery(query);
            //params.set("q", "p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0");// 查询所有
        }

        // 过滤查询
        if (!StringUtil.isEmpty(fq)) {
            params.set("fq", fq);
        }

        //params.set("indent", true);
        // 设置返回的数据格式
        params.set("wt", "json");

        //// 排序方式
        params.set("sort", "p_deliverTime desc");

        // 查询的条数
        //Integer start = NumberUtil.isValidId(startPage) ? startPage : 0;
        //Integer count = NumberUtil.isValidId(rows) ? rows : 30;
        //params.setStart(start);
        //params.setRows(count);
        try {
            //QueryResponse resp = client.query("core1", new SolrQuery(":"));
            QueryResponse resp = solrClient.query(params);
            SolrDocumentList docs = resp.getResults();
            System.out.println("总条数：" + docs.getNumFound());//查询总条数
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + resp.getQTime());
            return docs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有数据
     * <p>
     * q - 查询字符串，必须的。
     * fl - 指定返回那些字段内容，用逗号或空格分隔多个。
     * start - 返回第一条记录在完整找到结果中的偏移位置，0开始，一般分页用。
     * rows - 指定返回结果最多有多少条记录，配合start来实现分页。
     * sort - 排序，格式：sort=<field name>+<desc|asc>[,<field name>+<desc|asc>]… 。示例：（inStock desc, price asc）表示先 “inStock” 降序, 再 “price” 升序，默认是相关性降序。
     * wt - (writer type)指定输出格式，可以有 xml, json, php, phps, 后面 solr 1.3增加的，要用通知我们，因为默认没有打开。
     * fq - （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，例如：q=mm&fq=date_time:[20081001 TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的
     *
     * @param query
     * @param fq
     * @param startPage
     * @param rows
     */
    public SolrDocumentList queryDocs(String query, String fq, Integer startPage, Integer rows) {
        SolrQuery params = new SolrQuery();
        System.out.println("======================query===================");
        if (StringUtil.isEmpty(query)) {
            params.setQuery("*:*");// 查询所有
            //params.set("q", "*:*");// 查询所有
        } else {
            // 多条件查询 p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0
            params.setQuery(query);
            //params.set("q", "p_source:3 AND p_industryId:0 AND p_financingStage:0 AND p_cityId:0 AND p_status:0");// 查询所有
        }

        // 过滤查询
        if (!StringUtil.isEmpty(fq)) {
            params.set("fq", fq);
        }

        //params.set("indent", true);
        // 设置返回的数据格式
        params.set("wt", "json");

        //// 排序方式
        params.set("sort", "p_deliverTime desc");

        // 查询的条数
        Integer start = NumberUtil.isValidId(startPage) ? startPage : 0;
        Integer count = NumberUtil.isValidId(rows) ? rows : 30;
        params.setStart(start);
        params.setRows(count);

        //params.setFields("id","name","targetName","p_deliverTime");
        // 返回信息，默认为 “*”，指所有的字段。
//		params.set("fl", "id,name,price,score");

        //params.setFacet(true);
        //params.addFacetField("name");

        //query.setParam("hl.fl", "msg_title,msg_content");//设置哪些字段域会高亮显示
        //query.setHighlight(true).setHighlightSimplePre("<span class='hight'>")
        //  .setHighlightSimplePost("</span>");
        try {
            //QueryResponse resp = client.query("core1", new SolrQuery(":"));
            QueryResponse resp = solrClient.query(params);
            SolrDocumentList docs = resp.getResults();
            System.out.println("总条数：" + docs.getNumFound());//查询总条数
            System.out.println("查询内容:" + params);
            System.out.println("文档数量：" + docs.getNumFound());
            System.out.println("查询花费时间:" + resp.getQTime());
            return docs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过SolrDocumentList获取List<EventSolrBean>对象
     *
     * @param solrDocumentList
     * @return List<EventSolrBean>
     */
    public static List<EventSolrBean> getProjectSolrList(SolrDocumentList solrDocumentList) {
        List<EventSolrBean> resultList = null;
        if (solrDocumentList != null && solrDocumentList.size() > 0) {
            // 直接返回对象的数据结果，那么这个javabean必须像之前的例子一样有@Field(“cat”)的注解。
            DocumentObjectBinder binder = new DocumentObjectBinder();
            resultList = binder.getBeans(EventSolrBean.class, solrDocumentList);
            //List<ProjectSolrBean> resultList = resp.getBeans(ProjectSolrBean.class);
            //System.out.println("------resultList objec data:------" + JSONUtil.obj2Json(resultList));
            for (EventSolrBean solrBean : resultList) {
                System.out.println("------result object data:------" + JSONUtil.obj2Json(solrBean));
            }

            System.out.println("------query data:------" + JSONUtil.obj2Json(solrDocumentList));
        }
        return resultList;
    }


    /**
     * 删除指定id的数据
     *
     * @param id
     */
    public void deleteById(String id) {
        System.out.println("======================deleteById ===================");
        try {
            UpdateResponse rsp = solrClient.deleteById(id);
            solrClient.commit();
            System.out.println("delete id:" + id + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定查询条件的数据
     *
     * @param queryCon
     */
    public void deleteByQuery(String queryCon) {
        System.out.println("======================deleteByQuery ===================");
        UpdateResponse rsp;

        try {
            UpdateRequest commit = new UpdateRequest();
            commit.deleteByQuery(queryCon);
            commit.setCommitWithin(5000);
            commit.process(solrClient);
            System.out.println("url:" + commit.getPath() + "\t xml:" + commit.getXML() + " method:" + commit.getMethod());
            rsp = solrClient.deleteByQuery(queryCon);
            solrClient.commit();
            System.out.println("delete query:" + queryCon + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
        } catch (SolrServerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 更新指定条件的数据
     *
     * @param id
     * @param fieldName
     * @param fieldValue
     */
    public void updateField(String id, String fieldName, Object fieldValue) {
        System.out.println("======================updateField ===================");
        HashMap<String, Object> oper = new HashMap<String, Object>();
//        多值更新方法
//        List<String> mulitValues = new ArrayList<String>();
//        mulitValues.add(fieldName);
//        mulitValues.add((String)fieldValue);
        oper.put("set", fieldValue);

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", id);
        doc.addField(fieldName, oper);
        try {
            UpdateResponse rsp = solrClient.add(doc);
            System.out.println("update doc id: " + id + " result:" + rsp.getStatus() + " Qtime:" + rsp.getQTime());
            UpdateResponse rspCommit = solrClient.commit();
            System.out.println("commit doc to index result:" + rspCommit.getStatus() + " Qtime:" + rspCommit.getQTime());

        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分组查询
     *
     * @param groupFields
     */
    public List<PivotField> selectByGroup(String[] groupFields, String[] fq) {
        if (groupFields != null && groupFields.length > 0 && !StringUtil.isEmpty(fq)) {
            SolrQuery query = new SolrQuery();
            query.setQuery("*");
            query.setRows(0);
            query.setFilterQueries(fq);
            query.addFacetPivotField(StringUtils.join(groupFields, ","));
            try {
                QueryResponse response = solrClient.query(query);
                NamedList<List<PivotField>> lstFacet = response.getFacetPivot();
                List<PivotField> pfs = lstFacet.get(StringUtils.join(groupFields, ","));
                System.out.println(pfs);
                if (pfs == null) {
                    return null;
                }
                return pfs;
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据条件查询
     */
    public Map<String, Object> selectEventBySolr(String[] fq) {
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*");
            query.setRows(Integer.MAX_VALUE);
            if (fq != null && fq.length > 0) {
                query.setFilterQueries(fq);
            }
            QueryResponse response = solrClient.query(query);
            SolrDocumentList docs = response.getResults();
            System.out.println(docs.getNumFound());
            Map<String, Object> map = new HashMap<>();
            map.put("num", docs.getNumFound());
            map.put("list", docs);
            return map;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
