package com.welian.beans.cloudevent.statistical;


import java.util.List;

/**
 * Created by zhaopu on 2017/7/3.
 */
public class StatisticResp {

    public Integer signUpTotal; //累计报名人数

    public Integer signUpYesterday; //昨日报名人数

    public Integer eventTotal; //累计创建活动数

    public Integer signUpToday; //今日报名人数

    public Integer yesterdayState; //昨天相较于前天是否增长，1表示持平，2表示上升，3表示下降

    public Integer todayState; //今天相较于昨天是否增长，1表示持平，2表示上升，3表示下降

    public Integer detailViewCount; //此活动详情页面浏览数

    public Integer formViewCount; //此活动详情页面浏览数

    public List<StatisticParamResp> cityList; //城市分布

    public List<StatisticParamResp> industryList; //行业分布

}
