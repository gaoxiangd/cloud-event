package com.welian.service.impl;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.account.CityGroup;
import com.welian.beans.account.CityResp;
import com.welian.client.account.CityClient;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * created by GaoXiang on 2017/10/16
 */
@Service("cityRemoteService")
public class CityRemoteServiceImpl  {
    @Autowired
    private CityClient cityClient;

    public Map<Integer, String> getAllCityMap() {

        BaseResult<CityGroup> baseResult = cityClient.getAllCities();
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        Map<Integer, CityResp> cityGroupMap = null;
        if (baseResult.getData() != null) {
            cityGroupMap = baseResult.getData().cityMap;
        }
        Map<Integer, String> cityMap = new HashMap<>();
        if (!cityGroupMap.isEmpty()) {
            Iterator<Map.Entry<Integer, CityResp>> it = cityGroupMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, CityResp> entry = it.next();
                CityResp city = entry.getValue();
                cityMap.put(city.cityId, city.cityName);
            }
        }
        return cityMap;
    }
    }
