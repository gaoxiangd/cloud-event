package com.welian.beans.cloudevent;


/**
 * Description: Request Bean
 * 用于传递参数
 * Created by Sean.xie on 2017/2/16.
 */
public class CreateOrUpdateEventConstant  {
    public interface OpenFinancingServiceType {
        int NOT_OPEN = 0;//不开启一键融资
        int OPEN = 1;//开启一键融资
    }

    public interface LimitTimeType {
        int NOT_Limit = 0;//不限制时间
        int Limit = 1;//限制
    }

    public interface GroupSettingType {
        int USE_WELIAN_DEFAULT = 0;//使用微链认证的投资人
        int USE_CUSTOM = 1;//使用自定义分组
    }


}
