package com.welian.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

//import javapns.back.PushNotificationManager;
//import javapns.back.SSLConnectionHelper;
//import javapns.data.Device;
//import javapns.data.PayLoad;


public class CommonUtil {
    public static String encoding = "UTF-8";

    public static List<Map<String, Object>> listMapToLowerCase(
            List<Map<String, Object>> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                list.set(i, mapToLowerCase(map));
            }
        }
        return list;
    }


    public static Map<String, Object> mapToLowerCase(Map<String, Object> inMap) {
        Map<String, Object> resultMap = null;
        if (inMap != null) {
            if (LinkedHashMap.class.isInstance(inMap)) {
                resultMap = new LinkedHashMap<String, Object>();
            } else if (HashMap.class.isInstance(inMap)) {
                resultMap = new HashMap<String, Object>();
            } else {
                return inMap;
            }
            for (Iterator<Entry<String, Object>> it = inMap.entrySet()
                    .iterator(); it.hasNext(); ) {
                Entry<String, Object> e = it.next();
                String new_key = e.getKey().toLowerCase();
                resultMap.put(new_key, e.getValue());
            }
        }
        return resultMap;
    }


    public static String changeEncode(String str, String fromEncode,
                                      String toEncode) throws UnsupportedEncodingException {
        String returnStr;
        returnStr = new String(str.getBytes(fromEncode), toEncode);
        return returnStr;
    }

    public static String changeEncodeToUtf8(String str)
            throws UnsupportedEncodingException {
        return changeEncode(str, "ISO-8859-1", "UTF-8");
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(String date_string, String format) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.parse(date_string);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate(String date_string) {
        String format = "yyyy-MM-dd";
        if (date_string.indexOf(":") >= 0) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return toDate(date_string, format);
    }

    public static List<String> changeToList(String str) {
        if (isEmpty(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        int fromIndex = 0;
        int toIndex = 0;
        while ((toIndex = str.indexOf("},{", fromIndex)) > -1) {
            String s = str.substring(fromIndex, toIndex + 1);
            fromIndex = toIndex + 2;
            list.add(s);
        }
        String s = str.substring(fromIndex);
        if (!isEmpty(s)) {
            list.add(s);
        }
        return list;
    }

    /**
     * 过滤空字符串
     *
     * @param
     * @return
     */
    public static String trimNull(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }


    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Object str) {
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Long str) {
        if (str == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(List<Object> list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从map中获取String
     *
     * @param map
     * @param key
     * @return
     * @date 2011-7-19
     * @time 下午05:18:31
     */
    public static String getMapString(Map<String, Object> map, String key) {
        if (map == null) {
            return null;
        }
        Object value_obj = map.get(key);
        if (value_obj == null) {
            return null;
        } else {
            return value_obj.toString().trim();
        }
    }

    /**
     * 从map中获取long
     *
     * @param map
     * @param key
     * @return
     * @date 2011-7-19
     * @time 下午05:18:20
     */
    public static Long getMapLong(HashMap<String, Object> map, String key) {
        String value = getMapString(map, key);
        if (isEmpty(value)) {
            return null;
        } else {
            return Long.parseLong(value);
        }
    }

    public static Double getMapDouble(HashMap<String, Object> map, String key) {
        String value = getMapString(map, key);
        if (isEmpty(value)) {
            return null;
        } else {
            return Double.parseDouble(value);
        }
    }

    /**
     * 从map中获取Int
     *
     * @param map
     * @param key
     * @return
     * @date 2011-7-19
     * @time 下午05:18:20
     */
    public static Integer getMapInt(HashMap<String, Object> map, String key) {
        String value = getMapString(map, key);
        if (isEmpty(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }


    public static String addZeroToNumber(Long number, int total) {
        if (number == null) {
            return null;
        }
        String number_string = number.toString();
        return addZeroToNumber(number_string, total, true);
    }

    /**
     * 在位数不到total的情况下前面补0
     *
     * @return
     */
    public static String addZeroToNumber(String number, int total,
                                         boolean cut_if_long) {
        if (number == null) {
            return null;
        }
        int length = number.length();
        if (length < total) {
            for (int i = length; i < total; i++) {
                number = "0" + number;
            }
        } else if (cut_if_long && length > total) {
            number = number.substring(length - total, length);
        }
        return number;
    }

    public static String format(String value, String type) {
        if ("".equals(value)) {
            value = "''";
        } else {
            if ("date".equals(type)) {
                value = "to_date('" + value + "','YYYY/MM/DD HH24:MI:SS')";
            } else if ("int".equals(type) || "string".equals(type)) {
                value = "'" + value + "'";
            }
        }

        return value;
    }

    public static String getNowTimeString() {
        return getNowTimeString("yyyyMMddHHmmss");
    }

    public static String getNowDateString() {
        return getNowTimeString("yyyyMMdd");
    }

    public static String getNowTimeString(String format) {
        return formatDate(new Date(), format);
    }

    /*
     * 获得相隔天数
     */
    public static String getDateDiffTimeString(String format, int datediff) {
        Date getDate = new Date();
        long datediffTime = (getDate.getTime() / 1000) - 60 * 60 * 24 * datediff;
        getDate.setTime(datediffTime * 1000);
        return formatDate(getDate, format);
    }

    public static String arrayToString(String[] arr) {
        if (arr == null) {
            return null;
        } else {
            String re = "";
            for (int i = 0; i < arr.length; i++) {
                re += arr[i] + ",";
            }
            if (!"".equals(re)) {
                re = re.substring(0, re.length() - 1);
            }
            return re;
        }
    }

    public static String listToString(List<String> list) {
        return listToString(list, ",");
    }

    public static String listToString(List<String> list, String split) {
        if (list == null) {
            return null;
        } else {
            String re = "";
            for (String s : list) {
                re += s + split;
            }
            if (!"".equals(re)) {
                re = re.substring(0, re.length() - 1);
            }
            return re;
        }
    }


    public static List<String> getExistNumbers(List<String> new_phone_numbers,
                                               List<String> old_phone_numbers) {
        List<String> result_list = new ArrayList<String>(Arrays
                .asList(new String[old_phone_numbers.size()]));
        Collections.copy(result_list, old_phone_numbers);
        result_list.retainAll(new_phone_numbers);
        return result_list;
    }

    public static List<String> getNewNumbers(List<String> new_phone_numbers,
                                             List<String> old_phone_numbers) {
        List<String> result_list = new ArrayList<String>(Arrays
                .asList(new String[new_phone_numbers.size()]));
        Collections.copy(result_list, new_phone_numbers);
        result_list.removeAll(old_phone_numbers);
        return result_list;
    }

    /**
     * 取得第一个数组相对第二个数组缺少的
     */
    public static List<String> getRemoveNumbers(List<String> new_phone_numbers,
                                                List<String> old_phone_numbers) {
        List<String> result_list = new ArrayList<String>(Arrays
                .asList(new String[old_phone_numbers.size()]));
        Collections.copy(result_list, old_phone_numbers);
        result_list.removeAll(new_phone_numbers);
        return result_list;
    }


    public static Date addMonth(Date from_date, int month) {
        // Date d = (Date) from_date.clone();
        Calendar c = Calendar.getInstance();
        c.setTime(from_date);
        int new_month = c.get(Calendar.MONTH);
        new_month += month;
        int add_year = new_month / 12;
        new_month %= 12;
        if (add_year > 0) {
            int year = c.get(Calendar.YEAR);
            c.set(Calendar.YEAR, year + add_year);
            // d.setYear(d.getYear() + add_year);
        }
        c.set(Calendar.MONTH, new_month);
        return c.getTime();
    }


    /**
     * 判断时间是否大于当前时间
     *
     * @param from_date
     * @return
     */
    public static boolean isBeforeNow(Date from_date) {
        Date now_date = new Date();
        if (from_date.before(now_date)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断时间是否大于等于当前时间
     *
     * @param from_date
     * @return
     */
    public static boolean isBeforeOrEqualNow(Date from_date) {
        Date now_date = new Date();
        if (now_date.before(from_date)) {
            return false;
        } else {
            return true;
        }
    }

    public static int getDaysBetweenDate(Date from_date, Date to_date) {
        int days;
        days = (int) ((to_date.getTime() - from_date.getTime()) / (24 * 60 * 60 * 1000));
        return days;
    }

    public static double getDoubleLeft(double f, int place) {
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(place, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     * 转换成double
     *
     * @param obj
     * @return
     */
    public static double parseDouble(Object obj) {
        try {
            return (Double) obj;
        } catch (Exception e) {
            return 0;
        }

    }

    @SuppressWarnings("unchecked")
    public static List getRandomSubList(List list, int limit) {
        int size = list.size();
        if (size <= limit) {
            return list;
        } else {
            List retList = new ArrayList();
            int intRd = 0; //存放随机数
            int count = 0; //记录生成的随机数个数
            int flag = 0; //是否已经生成过标志
            int[] intRet = new int[limit];
            Random rdm = new Random(System.currentTimeMillis());
            while (count < limit) {
                intRd = Math.abs(rdm.nextInt()) % size;
                for (int i = 0; i < count; i++) {
                    if (intRet[i] == intRd) {
                        flag = 1;
                        break;
                    } else {
                        flag = 0;
                    }
                }
                if (flag == 0) {
                    retList.add(list.get(intRd));
                    intRet[count] = intRd;
                    count++;
                }
            }
            return retList;
        }

    }

    public static boolean isPhoneNo(String phoneNo) {
        boolean isPhone = false;
        if (phoneNo != null && phoneNo.length() == 11) {// 手机号码为11位
            String firstChar = phoneNo.substring(0, 1);
            if ("1".equals(firstChar)) {// 且第一位为1
                isPhone = true;
            }
        }
        return isPhone;
    }

//	public static String getPhone(String phoneNo) {
//		String returnPhone = null;
//		if (phoneNo != null && !"".equals(phoneNo)) {
//			phoneNo = phoneNo.replace("+86", "");
//			String regEx = "[^0-9]";
//	        Pattern p = Pattern.compile(regEx);
//	        Matcher m = p.matcher(phoneNo);
//	    //替换与模式匹配的所有字符（即非数字的字符将被""替换）
//	        returnPhone = m.replaceAll("").trim();
//	        if(returnPhone.length()>11){
//	        	returnPhone = returnPhone.substring(0, 11);
//	        }
//	        if(isMobileNO(returnPhone)){
//	        	//是手机号
//	        	return returnPhone;
//	        }else{
//	        	return null;
//	        }
//		}
//		return returnPhone;
//	}

	
	/*
    private void pushMessage(String deviceToken, String msg) {
		
		logger.warn("deviceToken" + deviceToken);
		if (deviceToken == null)
			return;
		try {
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(msg);
			payLoad.addBadge(1);
			payLoad.addSound("default");
			
			PushNotificationManager pushManager = PushNotificationManager
					.getInstance();
			pushManager.addDevice("iphone", deviceToken);
			
			// Device c = pushManager.getDevice("iphone");
			// String host = "gateway.sandbox.push.apple.com"; //
			// 测试用的苹果推送服务器gateway.push.apple.com
			String host = "gateway.push.apple.com";
			logger.warn("host " + host);
			int port = 2195;
			//String certificatePath =
			// "src/com/car/util/推送证书正式版密码hx2car123.p12"; // 刚才在mac系统下导出的证书//
			String certificatePath = "/app/soft/app/web/src/com/car/util/推送证书正式版密码hx2car123.p12";
			logger.warn("certificatePath " + certificatePath);
			String certificatePassword = "hx2car123";
			
			pushManager.initializeConnection(host, port, certificatePath,
					certificatePassword,
					SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);// 初始化tcp连接，公司网络代理上网，不能连上外网的tcp连接
			
			// Send Push
			Device client = pushManager.getDevice("iphone");
			pushManager.sendNotification(client, payLoad); // 推送消息
			pushManager.stopConnection();
			
			pushManager.removeDevice("iphone");
		} catch (Exception e) {
			logger.error("程序异常推送失败" + e.getMessage());
			
			FileLogger.printStackTrace(e);
		}
		logger.info("push succeed!");
		
	}
	*/

    /**
     * 两个参数的属性值中，数据一致的清空，不一致的保留fromClient
     *
     * @param fromClient 从客户端传来的信息
     * @param fromDB     从数据库中获取的信息
     * @return 返回fromClient中修改的值保留，其余值清空
     */
    public static <T> T translate(T fromClient, T fromDB) {
        if (fromClient == null || fromDB == null) {
            return null;
        }
        try {
            Class clazs = fromClient.getClass();
            Field[] fields = clazs.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                String name = field.getName();
                String clientValue = field.get(fromClient) + "";
                String dbValue = field.get(fromDB) + "";

                if (name.equals("mobile") || name.equals("id")) {
                    continue;
                } else if (clientValue == null || clientValue.equals("null") || clientValue.equals("")) {
                    field.set(fromDB, null);
                } else if (clientValue.equals(dbValue)) {
                    field.set(fromDB, null);
                } else if ((field.get(fromDB)) instanceof Date) {
                    Calendar c = Calendar.getInstance();
                    c.setTime((Date) field.get(fromClient));
                    long clientDateValue = c.getTimeInMillis();
                    c.setTime((Date) field.get(fromDB));
                    long d2 = c.getTimeInMillis();

                    if (clientDateValue == d2) {
                        continue;
                    } else {
                        field.set(fromDB, field.get(fromClient));
                    }
                } else {
                    field.set(fromDB, field.get(fromClient));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fromDB;
    }

    /**
     * 取指定长度随机数
     *
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        Random random = new Random(System.currentTimeMillis());
        String number = "";
        for (int i = 0; i < length; i++) {
            number = number + random.nextInt(10);
        }
        return number;
    }

    /**
     * 根据种子，取指定长度随机数
     *
     * @param seed
     * @param length
     * @return
     */
    public static String getRandomNumber(long seed, int length) {
        Random random = new Random(seed);
        String number = "";
        for (int i = 0; i < length; i++) {
            number = number + random.nextInt(10);
        }
        return number;
    }


    /**
     * 根据指定字符串，取指定长度随机字符串
     */
    public static String getRandomChar(String chars, int length) {
        if (chars == null || chars.trim() == "") {
            return null;
        }

        int count = chars.length();

        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(count)));
        }
        return sb.toString();
    }

    /**
     * 根据指定文件路径，读取文件内容
     */
    public static String readFile(String path) {
        InputStream input = null;
        File file = null;
        String str = "";
        try {
            file = new File(path);
            if (file.exists()) {
                int read = 0;
                input = new FileInputStream(file);
                byte[] tmp = new byte[1024];
                while ((read = input.read(tmp)) > 0) {
                    str += new String(tmp);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

}
