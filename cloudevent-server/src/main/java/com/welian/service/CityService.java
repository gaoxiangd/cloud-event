package com.welian.service;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.account.CityGroup;
import com.welian.beans.account.CityResp;
import com.welian.client.account.CityClient;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by memorytale on 2017/7/6.
 */
@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private Map<Integer, CityResp> cityMap = new HashMap<>();

    private Map<Integer, String> cityStringMap = new HashMap<>();

    private Map<String, Integer> cityStringKeyMap = new HashMap<>();

    /**
     * 获取所有的城市
     *
     * @return
     */
    public Map<Integer, CityResp> getAllCities() {
        if (cityMap.isEmpty()) {
            BaseResult<CityGroup> baseResult = cityClient.getAllCities();
            if (!baseResult.isSuccess() || baseResult.getData().cityMap == null ||
                    baseResult.getData().cityMap.isEmpty()) {
                throw ExceptionUtil.createParamException("城市不存在");
            }
            return baseResult.getData().cityMap;
        }
        return cityMap;
    }

    /**
     * 获取所有的城市
     *
     * @return
     */
    public Map<Integer, String> getAllStringCities() {
        if (cityStringMap.isEmpty()) {
            Map<Integer, CityResp> cityRespMap = getAllCities();
            for (Map.Entry<Integer, CityResp> entry : cityRespMap.entrySet()) {
                if (entry.getValue() != null) {
                    cityStringMap.put(entry.getValue().cityId, entry.getValue().cityName);
                }
            }
        }
        return cityStringMap;
    }

    /**
     * 获取所有的城市
     *
     * @return
     */
    public Map<String, Integer> getAllStringKeyCities() {
        if (cityStringKeyMap.isEmpty()) {
            Map<Integer, CityResp> cityRespMap = getAllCities();
            for (Map.Entry<Integer, CityResp> entry : cityRespMap.entrySet()) {
                if (entry.getValue() != null) {
                    cityStringKeyMap.put(entry.getValue().cityName, entry.getValue().cityId);
                }
            }
        }
        return cityStringKeyMap;
    }

}
