package com.welian.utils;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

/**
 * Created by zhaopu on 2017/10/25.
 */
public class EventSolrBean {

    @Id
    @Field("id") //如果Java Bean的字段名称与Solr中的字段名称不一致，则应该像这样给注解构面添加字符串，代表在Solr中的字段名称是什么
    public String id;// 唯一主键 活动id

    @Field
    public String title; //活动名字

    @Field
    public Integer type; // 0:普通活动 1:路演大赛 2:一键融资活动

    @Field
    public Long start_time; //活动开始时间

    @Field
    public Long end_time; //活动结束时间

    @Field
    public Integer joined_count;//报名人数 --

    @Field
    public Integer joined_count_status;//0:不展示报名数量 1:展示报名数量 --

    @Field
    public Integer sort_type;//0正常，2：hot热门活动   (1：new活动  3 已结束  4 已截止)appsever那边根据时间来判断 --

    @Field
    public String sponsor;//主办方id ,分割 --

    @Field
    public String sponsor_name;//主办方id ,分割 --

    @Field
    public Integer recommend_status;//活动被推荐的状态，默认0表示未推荐，1表示已推荐 --

    @Field
    public Long recommend_time; //活动推荐时间 --

    @Field
    public Integer state;//状态，1：发布之后又取消发布和未发布  2: 已发布 3: 已结束 4:已删除,3基本用不到

    @Field
    public String city_name;//城市

    @Field
    public Integer city_id;//城市id

    @Field
    public Integer limited;//报名限制人数 --

    @Field
    public Integer ticket_type;//0默认免费票    1收费", --

    @Field
    public String logo;

    @Field
    public String amountStr;

    @Field
    public String jumpUrl;

}
