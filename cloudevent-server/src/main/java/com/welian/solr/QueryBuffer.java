package com.welian.solr;

import java.util.Date;
import java.util.List;

/**
 * @author rui.mao
 * @Type QueryBuffer
 * @Desc
 * @date 2016/09/02
 */
public class QueryBuffer {
    private static final String START = "(";
    private static final String END = ")";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String NOT = " NOT ";
    private static final String ALL = "*";

    private StringBuffer buffer;

    /** 是否可以拼接连接符and或者or（true可以 false不可以） */
    private boolean isConnect;

    public QueryBuffer(){
        buffer = new StringBuffer();
    }

    public QueryBuffer(String str) {
        buffer = new StringBuffer(str);
    }

    public QueryBuffer appendStart(){
        return append(START);
    }

    public QueryBuffer appendEnd(){
        return append(END);
    }

    public QueryBuffer appendAND(){
        if (isConnect()) {
            append(AND);
        }
        return this;
    }

    public QueryBuffer appendOR(){
        if (isConnect()) {
            append(OR);
        }
        return this;
    }

    public QueryBuffer appendNOT(){
        return append(NOT);
    }

    public QueryBuffer appendALL(){
        return append(ALL);
    }

    /**
     * 放入字符
     */
    public QueryBuffer append(Object str) {
        buffer.append(str);
        return this;
    }

    /**
     * 查询单个字段单个值（" AND "）
     * @param filed
     * @param value
     * @return
     */
    public QueryBuffer addAndFiled(String filed, Object value){
        return addFiled(AND, filed, value);
    }

    /**
     * 查询单个字段单个值（" OR "）
     * @param filed
     * @param value
     * @return
     */
    public QueryBuffer addOrFiled(String filed, Object value){
        return addFiled(OR, filed, value);
    }

    /**
     * 查询单个字段单个值（不带连接符）
     */
    public QueryBuffer addFiled(String filed, Object value) {
        return addFiled("", filed, value);
    }

    /**
     * 查询单个字段单个值（带连接符）
     */
    private QueryBuffer addFiled(String connectStr, String filed, Object value){
        if (value != null && !"".equals(value)) {
            if (isConnect()) {
                buffer.append(connectStr);
            }
            buffer.append(filed).append(":").append(value);
        }
        return this;
    }

    /**
     * 日期区间查询（" AND "）
     */
    public QueryBuffer addAndSectionDate(String filed, Date startDate, Date endDate){
        return addSectionDate(AND, filed, startDate, endDate);
    }

    /**
     * 日期区间查询（" OR "）
     */
    public QueryBuffer addOrSectionDate(String filed, Date startDate, Date endDate){
        return addSectionDate(OR, filed, startDate, endDate);
    }

    /**
     * 日期区间查询（不带连接符）
     */
    public QueryBuffer addSectionDate(String filed, Date startDate, Date endDate){
        return addSectionDate("", filed, startDate, endDate);
    }

    /**
     * 日期区间查询（带连接符）
     */
    private QueryBuffer addSectionDate(String connectStr, String filed, Date startDate, Date endDate) {
        if (startDate != null || endDate != null) {
            if (isConnect()) {
                buffer.append(connectStr);
            }
            buffer.append(filed).append(":[");
            if (startDate != null) {
                buffer.append(startDate.getTime());
            } else {
                buffer.append("*");
            }
            buffer.append(" TO ");
            if (endDate != null) {
                buffer.append(endDate.getTime());
            } else {
                buffer.append("*");
            }
            buffer.append("]");
        }
        return this;
    }

    /**
     * 区间查询(" AND ")
     */
    public QueryBuffer addAndSection(String filed, Object startDateTimes, Object endDateTimes) {
        return addSection(AND, filed, startDateTimes, endDateTimes);
    }

    /**
     * 区间查询(" OR ")
     */
    public QueryBuffer addOrSection(String filed, Object startDateTimes, Object endDateTimes) {
        return addSection(OR, filed, startDateTimes, endDateTimes);
    }

    /**
     * 区间查询（不带连接符）
     */
    public QueryBuffer addSection(String filed, Object startDateTimes, Object endDateTimes){
        return addSection("", filed, startDateTimes, endDateTimes);
    }

    /**
     * 区间查询（带连接符）
     */
    private QueryBuffer addSection(String connectStr, String filed, Object start, Object end) {
        if (start != null || end != null) {
            if (isConnect()) {
                buffer.append(connectStr);
            }
            buffer.append(filed).append(":[");
            if (start != null) {
                buffer.append(start);
            } else {
                buffer.append("*");
            }
            buffer.append(" TO ");
            if (end != null) {
                buffer.append(end);
            } else {
                buffer.append("*");
            }
            buffer.append("]");
        }
        return this;
    }

    /**
     * 查询单个字段多个值（" AND "）
     */
    public QueryBuffer addAndFiledValues(String filed, List list) {
        return addFiledValues(AND, filed, list);
    }

    /**
     * 查询单个字段多个值（" OR "）
     */
    public QueryBuffer addOrFiledValues(String filed, List list) {
        return addFiledValues(OR, filed, list);
    }

    /**
     * 查询单个字段多个值（不带连接符）
     */
    public QueryBuffer addFiledValues(String filed, List list) {
        return addFiledValues("", filed, list);
    }

    /**
     * 查询单个字段多个值（带连接符）
     */
    private QueryBuffer addFiledValues(String connectStr, String filed, List list){
        if (list != null && list.size() > 0) {
            if (isConnect()) {
                buffer.append(connectStr);
            }
            int count = 0;
            buffer.append(filed).append(":").append(START);
            for (Object item : list) {
                if (count > 0) {
                    buffer.append(OR);
                }
                buffer.append(item);
                count++;
            }
            buffer.append(END);
        }
        return this;
    }

    /**
     * 关联查询 ?????
     * @param from
     * @param to
     * @param fromIndex
     * @return
     */
    public QueryBuffer addJoinQuery(Object from, Object to, String fromIndex){
        buffer.append("{!join from=").append(from.toString());
        buffer.append(" to=").append(to.toString());
        buffer.append(" fromIndex=").append(fromIndex).append("}");
        return this;
    }

    /**
     * 为空
     * @param filed
     * @return
     */
    public QueryBuffer addNull(String filed,boolean isStr) {
        return addNull("", filed, isStr);
    }

    public QueryBuffer addAndNull(String filed,boolean isStr) {
        return addNull(AND, filed,isStr);
    }

    public QueryBuffer addOrNull(String filed,boolean isStr) {
        return addNull(OR, filed,isStr);
    }

    private QueryBuffer addNull(String connectStr, String filed,boolean isStr) {
        buffer.append(connectStr).append(" -").append(START);
        buffer.append(filed).append(":").append(START);
        buffer.append(ALL);
        if(isStr){
            buffer.append(NOT).append(" \"\"");
        }
        buffer.append(END).append(END);
        return this;
    }

    /**
     * 不为空
     * @param filed
     * @return
     */
    public QueryBuffer addNotNull(String filed,boolean isStr) {
        return addNotNull("", filed,isStr);
    }

    public QueryBuffer addAndNotNull(String filed,boolean isStr) {
        return addNotNull(AND, filed,isStr);
    }

    public QueryBuffer addOrNotNull(String filed,boolean isStr) {
        return addNotNull(OR, filed,isStr);
    }

    private QueryBuffer addNotNull(String connectStr, String filed,boolean isStr) {
        buffer.append(connectStr).append(filed).append(START);
        buffer.append(ALL);
        if(isStr){
            buffer.append(NOT).append(" \"\"");
        }
        buffer.append(END);
        return this;
    }

    /**
     * 是否可以拼接连接符
     */
    public boolean isConnect() {
        if (!isConnect) {
            if (!"".equals(buffer.toString())) {
                isConnect = true;
            }
        }
        return isConnect;
    }

    /**
     * 清除/重置
     */
    public void clean(){
        buffer.setLength(0);
        isConnect = false;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
