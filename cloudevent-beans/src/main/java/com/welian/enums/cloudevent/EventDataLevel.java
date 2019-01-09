package com.welian.enums.cloudevent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dayangshu on 17/7/13.
 */
public enum EventDataLevel {
    LEVEL11(11, "3.3.0.1版本 App接口兼容 提供兼容字段", null),
    LEVEL10(10, "活动报名数", null),
    LEVEL8(8, "分享url", null),
    LEVEL7(7, "报名设置", null),
    LEVEL6(6, "标签", null),
    LEVEL5(5, "自定义表单", null),
    LEVEL4(4, "嘉宾", null),
    LEVEL3(3, "主办方", null),
    LEVEL2(2, "所有基本信息", null),
    LEVEL1(1, "标题+id+城市+活动类型+时间+地址", new int[]{
            LEVEL8.level
    }),
    LEVEL0(0, "全部信息", new int[]{
            LEVEL2.level, LEVEL3.level, LEVEL4.level, LEVEL5.level, LEVEL6.level, LEVEL7.level,LEVEL8.level,LEVEL10.level
    }),
    LEVEL12(12, "app详情信息", new int[]{
            LEVEL2.level, LEVEL3.level, LEVEL4.level, LEVEL5.level, LEVEL6.level, LEVEL7.level,LEVEL8.level,LEVEL10.level
            ,LEVEL11.level
    }),
    LEVEL9(9, "分享url+标题", new int[]{LEVEL1.level}),
    LEVEL13(13, "我的票券", new int[]{LEVEL1.level,LEVEL5.level,LEVEL7.level}),
    LEVEL14(14, "打印相关信息", new int[]{LEVEL1.level,LEVEL5.level,LEVEL7.level}),
    LEVEL15(15, "列表list", new int[]{
        LEVEL1.level,LEVEL3.level, LEVEL4.level, LEVEL7.level,LEVEL10.level
    });

    private int level;
    private String title;
    private int[] parentLevel;


    EventDataLevel(int level, String title, int[] parentLevel) {
        this.level = level;
        this.title = title;
        this.parentLevel = parentLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(int[] parentLevel) {
        this.parentLevel = parentLevel;
    }

    public static EventDataLevel getEnumLevel(int level) {
        for (EventDataLevel eventDataLevel : EventDataLevel.values()) {
            if (eventDataLevel.level == level) {
                return eventDataLevel;
            }
        }
        return null;
    }

    public static Set<Integer> getParentLevels(int level) {
        Set<Integer> levelSet = new HashSet();
        levelSet = recurse(levelSet, level);
        levelSet.add(level);
        return levelSet;
    }

    private static Set<Integer> recurse(Set<Integer> levelSet, int level) {
        EventDataLevel eventDataLevel = getEnumLevel(level);
        if (eventDataLevel == null) {
            return levelSet;
        }
        if (eventDataLevel.getParentLevel() != null) {
            int[] parentArry = eventDataLevel.getParentLevel();
            for (int parentInt : parentArry) {
                levelSet.add(parentInt);
                recurse(levelSet, parentInt);
            }
        }
        return levelSet;
    }
}
