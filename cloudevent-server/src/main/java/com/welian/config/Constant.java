package com.welian.config;

import com.welian.beans.cloudevent.CommonFrontParams;
import com.welian.beans.cloudevent.FrontParams;
import com.welian.beans.cloudevent.ProjectScreenParams;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by memorytale on 2017/4/26.
 */
public class Constant {

    public static String ALIOSS_ACCESS_KEY_ID;

    public static String ALIOSS_ACCESS_KEY_SECRET;

    private final static String CURRENT_DIR;

    public final static String XLS_DIR;

    private final static String XLS_DIR_NAME = "/wrong_xls";
    /**
     * 待上传文件保存路径
     */
    public static String uploadFileDir = "/data/tmp/server/upload/";

    /**
     * H5投递项目时默认的城市
     */
    public static final Integer PROJECT_DEFAULT_CITY = 131;

    /**
     * H5投递项目时默认的新建项目的二级领域
     */
    public static final Integer PROJECT_DEFAULT_SECOND_INDUSTRY = 349;
    /**
     * H5投递项目时默认的新建项目的一级领域
     */
    public final static int PROJECT_DEFAULT_FIRST_INDUSTRY = 34;

    public static final String RESULT_STRING_FAIL = "你的报名被主办方拒绝了";

    public static final String RESULT_STRING_CHECK = "你已经成功报名,请等待审核";

    public static final String RESULT_STRING_DEFAULT = "你已经成功报名了";

    public static final long FREE_PRICE_LONG = 0l;

    public static final String DEFAULT_FREE_TICKET_INTRO = "活动票券";

    public static final Integer NO_LIMIT_NUMBER = 9999999;

    public static final String DEFAULT_COMMODITY_PROPERTY_CODE = "sort";

    public static final Double FREE_TICKET_DEFAULT_PRICE = 0.00;

    public static final String FREE_TICKET_DEFAULT_NAME = "普通票";

    public static final String TICK_PROP_NAME = "排序";

    public static final String DEFAULT_TICKET_NAME_APPEND = "活动的票券";

    public static final int DEFAULT_NOT_NULL_NUMBER = 0;

    public static final String DEFAULT_NOT_NULL_STRING = "";

    public static final String ORG_AD_LIST = "orgAdList";

    public static final long DAY_LONG = 24 * 60 * 60 * 1000l;


    /**
     * 事件活动中的匹配的投资人个数字段
     * 开启【一键融资】，选中默认微链认证投资人分组，匹配20个投资人
     */
    public static final int MAX_MATCH_INVESTORCOUNT = 20;

    // 融资阶段id
    public static final int[] CONSTANT_STAGE_ID = {0, 1, 2, 3, 4, 5};
    // 融资阶段名称
    public static final String[] CONSTANT_STAGE_NAME = new String[]{"种子轮", "天使轮", "pre-A轮", "A轮", "B轮", "B轮以上"};
    // 城市
    public static final int[] CONSTANT_CITY_ID = {131, 289, 179, 257, 340, 75, 218, -1};
    public static final String[] CONSTANT_CITY_NAME = new String[]{"北京", "上海", "杭州", "广州", "深圳", "成都", "武汉", "其他"};


    // 融资阶段id
    public static final int[] CONSTANT_STAGE_ID_SCREEN = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    // 融资阶段名称
    public static final String[] CONSTANT_STAGE_NAME_SCREEN = new String[]{"种子轮", "天使轮", "pre-A轮",
            "A轮", "B轮", "C轮", "D轮", "E轮", "F轮", "已上市", "其他"};


    // 评星
    public static final int[] CONSTANT_STAR_ID = {0, 1, 2, 3, 4, 5};
    // 评星
    public static final String[] CONSTANT_STAR_NAME = new String[]{"0星", "1星", "2星", "3星", "4星", "5星"};


    // 日期
    public static final int[] CONSTANT_DAY_ID = {7, 30};
    // 日期
    public static final String[] CONSTANT_DAY_NAME = new String[]{"7天内", "30天内"};

    //调用tag-server的type
    public static final int TAG_TYPE = 5;

    //票种
    public static final double FREE_PRICE = 0;
    public static final String FREE_TICKET_TITLE = "免费票";
    public static final Byte PROJECT_SIGN_UP_TYPE = 1;

    //默认的签到数
    public static final int CHECK_IN_TOTAL = 0;


    //搜索报名记录type
    public static final byte SEARCH_RECORD_BY_NAME = 0;
    public static final byte SEARCH_RECORD_BY_MOBILE = 1;

    //短信开启开关
    public static final byte SMS_SEND_OPEN = 0;
    public static final byte SMS_SEND_CLOSE = 1;

    //开启群聊
    public static final byte GROUP_CHART_OPEN = 1;
    public static final byte GROUP_CHART_CLOSE = 0;


    /**
     * seed(0, "种子轮", "#6db67f"),
     * angel(1, "天使轮", "#fa8997"),
     * preA(2, "Pre-A轮", "#62bcd4"),
     * a(3, "A轮", "#fbb217"),
     * b(4, "B轮", "#caa4cc"),
     * c(5, "C轮", "#3474ba"),
     * d(6, "D轮", "#c88b76"),
     * e(7, "E轮", "#e65d74"),
     * f(8, "F轮", "#af6dd5"),
     * ipo(9, "已上市", "#71a8bb"),
     * other(10, "其他", "#00c2d0");
     *
     * @param stage
     * @return
     */
    public static int getStageCorrespondingRelation(Integer stage) {
        if (stage == null) {
            return 0;
        }
        if (stage > 4) {
            return 5;
        }
        return stage;
    }

    public static int getCityCorrespondingRelation(Integer cityId) {
        if (cityId == null) {
            return -1;
        }
        for (int i = 0, count = CONSTANT_STAGE_ID.length; i < count; i++) {
            if (cityId == CONSTANT_CITY_ID[i]) {
                return cityId;
            }
        }
        return -1;
    }

    static {
        CURRENT_DIR = getPath();
        XLS_DIR = CURRENT_DIR + XLS_DIR_NAME;
    }

    public static String getPath() {
        URL url = Constant.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = url.getPath();
        filePath = filePath.replaceFirst("file:.*\\.jar!.*", "");
        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"
            // 截取路径中的jar包名
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }

        File file = new File(filePath);

        // /If this abstract pathname is already absolute, then the pathname
        // string is simply returned as if by the getPath method. If this
        // abstract pathname is the empty abstract pathname then the pathname
        // string of the current user directory, which is named by the system
        // property user.dir, is returned.
        filePath = file.getAbsolutePath();//得到windows下的正确路径
        return filePath;
    }



    /**
     * 返回xls文件的存储路径
     *
     * @param url
     * @return
     */
    public static String getXlsPath(String url) {
        return XLS_DIR + "/" + url;
    }

    /**
     * 获取轮次map
     *
     * @return
     */
    public static Map<Integer, String> getStageMap() {
        Map<Integer, String> returnMap = new HashMap<>();
        for (int i = 0, count = CONSTANT_STAGE_ID_SCREEN.length; i < count; i++) {
            returnMap.put(CONSTANT_STAGE_ID_SCREEN[i], CONSTANT_STAGE_NAME_SCREEN[i]);
        }
        return returnMap;
    }
}
