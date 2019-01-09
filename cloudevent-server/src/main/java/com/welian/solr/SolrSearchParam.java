package com.welian.solr;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

/**
 * @author rui.mao
 * @Type SolrQueryParam
 * @Desc
 * @date 2016/09/18
 */
public class SolrSearchParam {
    /** 索引名称，必填 */
    private String coreName;

    private String[] flArray;

    /** q的查询sql 或 关键字*/
    private String q;

    /** fq的过滤sql条件数组 */
    private String[] fqArray;

    /** sort的排序 */
    private List<SolrQuery.SortClause> sortList;

    /*权重设置*/
    private Boolean edismax;

    /*关键字 匹配哪些字段 多个字段,分割*/
    private String pf;

    /*关键字匹配多个字段的权重设置*/
    private String qf;

    /*其他字段的权重设置*/
    private String bf;

    /*是否高亮显示*/
    private Boolean hlFl;

    /*要高亮的字段 多个字段,分割*/
    private String flFields;

    /*要插入的标签起始*/
    private String hlSimplePre;

    /*要插入的标签 截止*/
    private String hlSimplePost;

    private String solrUrl;

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String[] getFlArray() {
        return flArray;
    }

    public void setFlArray(String[] flArray) {
        this.flArray = flArray;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String[] getFqArray() {
        return fqArray;
    }

    public void setFqArray(String[] fqArray) {
        this.fqArray = fqArray;
    }

    public List<SolrQuery.SortClause> getSortList() {
        return sortList;
    }

    public void setSortList(List<SolrQuery.SortClause> sortList) {
        this.sortList = sortList;
    }

    public Boolean getEdismax() {
        return edismax;
    }

    public void setEdismax(Boolean edismax) {
        this.edismax = edismax;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getQf() {
        return qf;
    }

    public void setQf(String qf) {
        this.qf = qf;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public Boolean getHlFl() {
        return hlFl;
    }

    public void setHlFl(Boolean hlFl) {
        this.hlFl = hlFl;
    }

    public String getFlFields() {
        return flFields;
    }

    public void setFlFields(String flFields) {
        this.flFields = flFields;
    }

    public String getHlSimplePre() {
        return hlSimplePre;
    }

    public void setHlSimplePre(String hlSimplePre) {
        this.hlSimplePre = hlSimplePre;
    }

    public String getHlSimplePost() {
        return hlSimplePost;
    }

    public void setHlSimplePost(String hlSimplePost) {
        this.hlSimplePost = hlSimplePost;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public void setSolrUrl(String solrUrl) {
        this.solrUrl = solrUrl;
    }
}
