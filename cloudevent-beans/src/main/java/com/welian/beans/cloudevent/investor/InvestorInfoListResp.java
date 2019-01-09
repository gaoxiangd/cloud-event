package com.welian.beans.cloudevent.investor;

import com.welian.beans.cloudevent.user.UserResp;
import org.sean.framework.bean.BaseBean;

/**
 * Description: Request Bean
 * 用于传递参数
 * Created by Sean.xie on 2017/2/16.
 */
public class InvestorInfoListResp extends BaseBean {

    public UserResp user;

    public Integer feedback;
    public Integer received;
    public Integer interview;
}
