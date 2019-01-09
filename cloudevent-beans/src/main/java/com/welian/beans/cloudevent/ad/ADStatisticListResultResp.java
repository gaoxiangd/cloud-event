package com.welian.beans.cloudevent.ad;


import java.util.List;

/**
 * Description: Request Bean
 * 用于传递参数
 * Created by Sean.xie on 2017/2/16.
 */
public class ADStatisticListResultResp  {

    /**
     * 分页的项目
     */
    public List<ADStatisticResp> list;

    /**
     * 个数
     */
    public Integer count;

}
