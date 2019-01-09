package com.welian.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PositionUtil {

    // public static Map<String,String> Position = new HashMap<String,String>();

    private static Map<String, Set<String>> Position = new HashMap<String, Set<String>>();

    static {
        BufferedReader br = null;
        try {
            InputStream is = MobileUtil.class.getClassLoader().getResourceAsStream("position.txt");
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] strs = line.split("\\|");
                if ("1".equals(strs[0]))
                    continue;
                String postion = strs[1];
                String position2 = strs[2];
                Set<String> tmpSet = new HashSet<String>();
                String[] positions = position2.split(",");
                for (String s : positions) {
                    if (s != null) {
                        tmpSet.add(s.toUpperCase());
                    }
                }
                Position.put(postion, tmpSet);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error while Position: " + e.toString());
        }
    }

    // 取用户标签
    // company and postion need trim and change to upper case
    public static String getFaceMark(String company, String position) {
        if (company == null || position == null) {
            return "其他";
        }
        if (company.indexOf("投资") > -1 || company.indexOf("资本") > -1 || company.indexOf("创投") > -1
                || company.indexOf("资产") > -1 || company.indexOf("基金") > -1 || position.indexOf("投资") > -1) {
            return "投资者";
        }
        for (String ss : Position.keySet()) {
            for (String sv : Position.get(ss)) {
                if (position.indexOf(sv) > -1) {
                    return ss;
                }
            }
        }
        return "其他";
    }

    /**
     * 获取用户标签，是否是潜在投资人
     * 0 普通用户，1 投资人，2 潜在投资人
     *
     * @param company
     * @param position
     * @return
     */
    public static Integer getUserTag(String company, String position) {
        String tag = getFaceMark(company, position);
        if (tag.indexOf("投资") > -1) {
            return 2;
        }

        return 0;
    }

    /**
     * 获取用户标签，是否是创业者
     *
     * @param company
     * @param position
     * @return
     */
    public static boolean isFounder(String company, String position) {
        String tag = getFaceMark(company, position);
        if (tag.indexOf("创业") > -1) {
            return true;
        }

        return false;
    }

}
