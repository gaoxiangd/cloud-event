package com.welian.solr;

/**
 * @author rui.mao
 * @Type SolrItemParam
 * @Desc 查询solr分页列表的参数
 * @date 2016/09/04
 */
public class SolrPageParam extends SolrSearchParam{

    /** 开始数，从0开始，类似于mysql的limit的第一个参数 */
    private int start;

    /** 分页数量，类似于mysql的limit的第二个参数 */
    private int rows;

    private String solrUrl;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public void setSolrUrl(String solrUrl) {
        this.solrUrl = solrUrl;
    }
}
