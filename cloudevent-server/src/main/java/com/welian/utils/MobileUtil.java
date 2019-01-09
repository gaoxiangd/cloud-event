package com.welian.utils;

import org.sean.framework.util.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileUtil {

    private static final Logger logger = Logger.newInstance(MobileUtil.class);
    private static Map<String, String> Location = new HashMap<>();

    static {
        BufferedReader br = null;
        try {
            InputStream is = MobileUtil.class.getClassLoader().getResourceAsStream("mobile.txt");
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] strs = line.split("\\|");
                String mobile = strs[0];
                String city = strs[3];
                Location.put(mobile, city);
            }
            br.close();
        } catch (Exception e) {
            logger.printStackTrace(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.printStackTrace(e);
            }
        }
    }

    public static Map<String, String> getLocation() {
        return Location;
    }

    public static String getCity(String mobile) {
        try {
        mobile = mobile.substring(0, 7);
        String cityName = Location.get(mobile);
        if (cityName != null) {
            return cityName;
        }
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * 过滤不符合规范的手机号码
     * @param phoneList
     * @return
     */
    public static List<String> filterPhoneList(List<String> phoneList) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        List<String> newPhoneList=new ArrayList<>();
        for(String phone:phoneList){
            if(phone.matches(regExp)){
                newPhoneList.add(phone);
            }
        }
        return newPhoneList;
    }
}