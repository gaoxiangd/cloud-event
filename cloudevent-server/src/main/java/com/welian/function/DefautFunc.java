package com.welian.function;

import org.sean.framework.bean.BaseResult;
import com.welian.utils.I18N;
import org.apache.http.HttpResponse;
import org.sean.framework.bean.PageData;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.config.SingleAppAutoConfiguration;
import org.sean.framework.exception.CodeException;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 只放一些简单短小但是出现频率很高的代码片段
 * @author ld
 */
public interface DefautFunc {

    String ISO_8859_1 = "ISO_8859_1";

    Random RANDOM = new Random();

    /**
     * 伴生对象,用于该接口的组合使用的场合
     */
    Inner OBJ = new Inner();

    class Inner implements DefautFunc {
        private Logger logger = Logger.newInstance(DefautFunc.class);
        private final String[] chars = new String[] {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        private Map<String,Boolean> note = new HashMap<>();
        private RedisService redisService;
    }

    byte BYTE_0 = 0;

    byte BYTE_1 = 1;

    Integer INT0 = 0;

    Long LONG0 = 0L;

    /**
     * 这个key对应的runnable的内容在每次部署只会被执行一次
     * 注：本方法是一个全局的记事本，每件记录需要只执行一次的事情不会被垃圾回收，所以不要频繁的记录新内容
     * runnable和多线程没有什么关系，只是实在找不到其他的的无输入无输出的函数式接口而已
     */
    default <T> void onlyRunOnce(String key, Runnable runnable) {
        try {
            if (OBJ.note.containsKey(key)) {
                return;
            }
            runnable.run();
            OBJ.note.computeIfAbsent(key, e -> true);
        } catch (Exception e) {
            if (OBJ.note.containsKey(key)) {
                OBJ.note.remove(key);
            }
        }
    }

    default Random random() {
        return RANDOM;
    }

    default Integer randomInt(Integer limit) {
        return RANDOM.nextInt(limit);
    }

    /**
     * 如果obj为null则返回default
     */
    default <T> T null2Default(T obj, T defaut) {
        return Objects.isNull(obj) ? defaut : obj;
    }

    default Integer null2Zero(Integer num) {
        return num == null ? Integer.valueOf(0) : num;
    }

    default Integer zero2Null(Integer num) {
        return num == 0 ? null : num;
    }

    default Integer null2One(Integer num) {
        return num == null ? Integer.valueOf(1) : num;
    }

    default Byte null2One(Byte num) {
        return num == null ? Byte.valueOf((byte)1) : num;
    }

    default Boolean null2False(Boolean c) {
        return c == null ? Boolean.FALSE : c;
    }

    default Boolean null2True(Boolean c) {
        return c == null ? Boolean.TRUE : c;
    }

    /**
     */
    default <T> Function<T,Object> void2null(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return null;
        };
    }

    /**
     * 调用第三方接口,封装判断是否成功和抛异常以及返回数据的细节
     */
    default  <T> T getClientData(BaseResult<T> baseResult) {
        if (!baseResult.isSuccess()) {
            throw new CodeException(baseResult);
        }
        return baseResult.getData();
    }

    /**
     * 调用第三方接口,封装判断是否成功和抛异常以及返回数据的细节
     */
    default  <T> T getClientData(String message, BaseResult<T> baseResult) {
        if (!baseResult.isSuccess()) {
            throw new CodeException(baseResult);
        }
        return baseResult.getData();
    }

    /**
     */
    default void whenThrowBizError(boolean condition, String message) {
        if (condition) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_UNKNOW,message);
        }
    }

    /**
     * 可与condition配合
     */
    default void whenThrowBizError(Predicate<Integer> condition, String message) {
        if (condition.test(0)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_UNKNOW,message);
        }
    }

    /**
     */
    default void whenThrowBizError(boolean condition, Supplier<String> messageSupplier) {
        if (condition) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_UNKNOW,messageSupplier.get());
        }
    }

    default CodeException error(String message) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_UNKNOW,message);
    }

    /**
     *
     */
    default void whenThrowParamError(boolean condition, String message) {
        if (condition) {
            System.out.println(message);
            throwParamError(message);
        }
    }

    /**
     *
     */
    default void whenThrowParamError(boolean condition) {
        if (condition) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
    }

    default void whenThrowParamError(Predicate<Integer> c) {
        whenThrowParamError(c.test(0));
    }

    /**
     *
     */
    default void whenThrow(boolean condition, String message) {
        if (condition) {
            System.out.println(message);
            throwParamError(message);
        }
    }

    /**
     */
    default void whenThrow(boolean condition, Supplier<String> messageSupplier) {
        whenThrowBizError(condition,messageSupplier);
    }

    default void whenNullThrowBizError(Object obj, String message) {
        whenThrowBizError(Objects.isNull(obj),message);
    }

    /**
     */
    default boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 参数全为null返回true 如果有不为null的 返回false
     */
    default boolean allIsNull(Object... objs) {
        for (Object obj:objs) {
            if (Objects.nonNull(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果中有不为null的返回true 参数全为null返回false
     */
    default boolean hasNotNull(Object... objs) {
        return !allIsNull(objs);
    }

    default boolean nonNull(Object obj) {
        return  Objects.nonNull(obj);
    }

    default boolean isInValidId(Integer id) {
        return NumberUtil.isInValidId(id);
    }

    default boolean isValidId(Integer id) {
        return NumberUtil.isValidId(id);
    }

    default boolean isNotZero(Integer num) {
        return num != 0;
    }

    default boolean isZero(Integer num) {
        return num != null && num == 0;
    }

    default boolean isZero(Long num) {
        return num != null && num.equals(0L);
    }

    default boolean isOne(Integer num) {
        return num != null && num == 1;
    }

    default boolean isOne(byte num) {
        return BYTE_1 == num;
    }

    /**
     * 全是0
     */
    default boolean allIsZero(Integer... nums) {
        for (Integer num:nums) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 不全是0
     */
    default boolean notAllIsZero(Integer... nums) {
        return !allIsZero(nums);
    }

    default boolean lessThanZero(Integer num) {
        return num != null && num < 0;
    }

    default boolean isNotEmpty(Collection collection) {
        return StringUtil.isNotEmpty(collection);
    }

    default boolean isNotEmpty(String str) {
        return StringUtil.isNotEmpty(str);
    }

    default boolean isEmpty(Collection collection) {
        return StringUtil.isEmpty(collection);
    }

    default boolean isEmpty(String str) {
        return StringUtil.isEmpty(str);
    }

    default <T> String obj2Json(T t) {
        return JSONUtil.obj2Json(t);
    }

    default <T> String obj2PrettyJson(T t) {
        return JSONUtil.obj2PrettyJson(t);
    }

    default <T> T json2obj(String json, Class<T> c) {
        return JSONUtil.json2Obj(json,c);
    }

    default boolean isNotBlank(CharSequence cs) {
        return StringUtil.isNotBlank(cs);
    }

    default boolean isBlank(CharSequence cs) {
        return StringUtil.isBlank(cs);
    }

    /**
     * 获取指定时间对应的毫秒数
     */
    default long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            OBJ.logger.error(e.getMessage());
        }
        return 0;
    }

    default void throwParamError(String message) {
        throw paramError(message);
    }

    default CodeException paramError(String message) {
        return new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS,message);
    }

    default String errMsg(String k) {
        return I18N.getErrorMsg(k);
    }

    default String errMsg(String k, String s) {
        return I18N.getErrorMsg(k) + s;
    }

    default String paramCantEmpty(String s) {
        return s + "不可以为空";
    }

    default <T> String errMsgObj(String k, T t) {
        return I18N.getErrorMsg(k) + obj2Json(t);
    }

    /**
     */
    default void check(String field, String message) {
        if (StringUtil.isEmpty(field)) {
            throwParamError(message);
        }
    }

    /**
     */
    default void check(Integer field, String message) {
        if (NumberUtil.isInValidId(field)) {
            throwParamError(message);
        }
    }

    /**
     * 检查属性是否合法
     */
    default void check(String field) {
        if (StringUtil.isEmpty(field)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
    }

    /**
     */
    default void check(Integer field) {
        if (NumberUtil.isInValidId(field)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
    }

    /**
     * base64 加密(默认编码格式ISO_8859_1)
     */
    default <T> String base64Encode(T t) {
        try {
            return Base64.getEncoder().encodeToString(JSONUtil.obj2Json(t).getBytes(ISO_8859_1));
        } catch (Exception e) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS,"Base64 加密失败");
        }
    }

    /**
     * base64 解密(默认编码格式ISO_8859_1)
     */
    default <T> T base64Decode(String key, Class<T> c) {
        try {
            return JSONUtil.json2Obj(new String(Base64.getDecoder().decode(key),ISO_8859_1), c);
        } catch (Exception e) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS,"Base64 解密失败");
        }
    }

    /**
     * 屏蔽list为空的情况取得安全的list
     */
    default <T> List<T> safeList(List<T> list) {
        return Optional.ofNullable(list).orElseGet(ArrayList::new);
    }

    /**
     */
    default void checkObj(Object obj) {
        if (Objects.isNull(obj)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }
    }

    /**
     */
    default void checkObj(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throwParamError(message);
        }
    }

    /**
     * 获取系统时间
     */
    default long now() {
        return optionOfNullable(redisMap())
                .map(RedisService::getCurrentTime)
                .orElseGet(System::currentTimeMillis);
    }

    @SuppressWarnings("unchecked")
    default  <T> List<T> asList(T... a) {
        return Arrays.asList(a);
    }

    /**
     * 如果 val 不为空则执行 c
     */
    default <T> void whenNonNullDo(Consumer<T> c, T val) {
        optionOfNullable(val).ifPresent(c);
    }

    /**
     * 如果 list 不为空 则执行 c
     */
    default <T> void whenNonNullDo(Consumer<List<T>> c, List<T> list) {
        optionOfNullable(list).filter(this::isNotEmpty).ifPresent(c);
    }

    /**
     * 如果 val 不为空则执行 c
     */
    default  void whenValidIdDo(Consumer<Integer> c, Integer id) {
        optionOfNullable(id).filter(this::isValidId).ifPresent(c);
    }

    default <T> Function<List<T>,T> first() {
        return e -> isNotEmpty(e) ? e.get(0) : null;
    }

    /**
     * 将分组后的map的 list 转化成为size
     */
    default <T> Map<Integer,Integer> sizeMap(Map<Integer, List<T>> listMap) {
        return listMap.keySet().stream()
                .collect(toMap(identity(),e -> optionOfNullable(listMap.get(e)).map(List::size).orElse(0)));
    }

    default <T> Optional<T> optionOf(T t) {
        return Optional.of(t);
    }

    default <T> Optional<T> optionOfNullable(T t) {
        return Optional.ofNullable(t);
    }

    default Function<String,String> addPrefix(String prefix) {
        return e -> prefix + e;
    }

    default <T> Collector<T, ?, List<T>> toList() {
        return Collectors.toList();
    }

    /**
     * 将map的元素转化成为List
     */
    default <T> List<T> toList(Map<Integer, T> map) {
        return map.keySet().stream().map(map::get).collect(toList());
    }

    default <T> Function<Map<Integer,T>,List<T>> tolist() {
        return this::toList;
    }

    default <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>>
        groupingByConcurrent(Function<? super T, ? extends K> classifier) {
        return Collectors.groupingByConcurrent(classifier);
    }

    default <T, K> Collector<T, ?, Map<K, List<T>>>
        groupingBy(Function<? super T, ? extends K> classifier) {
        return Collectors.groupingBy(classifier);
    }

    default  <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper,valueMapper);
    }

    /**
     * 根据list的某个主键来将其转化成为map
     */
    default <T> Map<Integer,T> toMap(Function<T, Integer> func, List<T> list) {
        return list.stream().collect(toMap(func,identity()));
    }

    default <T> UnaryOperator<T> identity() {
        return t -> t;
    }

    /**
     * 也是list转map 这个可以防止主键重复的异常
     */
    default <T> Map<Integer,T> toMapList0(Function<T, Integer> func, List<T> list) {
        Map<Integer,List<T>> map = list.stream().collect(groupingByConcurrent(func));
        return map.keySet().stream().collect(toMap(identity(),e -> map.get(e).get(0)));
    }

    /**
     * 连接条件
     */
    default Predicate<Integer> condition(boolean con) {
        return e -> con;
    }

    /**
     * 连接条件
     */
    default <T> Predicate<T> asCondition(boolean con) {
        return e -> con;
    }

    default Integer[] toArray(Set<Integer> set) {
        return set.stream().collect(toList()).toArray(new Integer[set.size()]);
    }

    int MIN_THREADS = 15;

    int MAX_THREADS = 30;

    ExecutorService EXECUTOR = new ThreadPoolExecutor(MIN_THREADS, MAX_THREADS,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    /**
     * 组合式异步
     */
    default <U> CompletableFuture<U> async(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier,EXECUTOR);
    }

    /**
     * 异步执行
     */
    default void async(Runnable task) {
        CompletableFuture.runAsync(task,EXECUTOR);
    }

    /**
     * Like条件
     */
    default String similarStr(String val) {
        return optionOfNullable(val).map(e -> "%" + e + "%").orElse(null);
    }

    default int parseInt(String s) {
        return Integer.parseInt(s);
    }

    default <T,R> R feach(T t, Function<T, R> func) {
        return optionOf(t).map(func).orElse(null);
    }

    default <T,R> R feach(T t, Function<T, R> func, R defaut) {
        return optionOf(t).map(func).orElse(defaut);
    }

    default <T,R> R feach(T t, Function<T, R> func, Supplier<R> defaut) {
        return optionOf(t).map(func).orElseGet(defaut);
    }

    default <T,R> R feach(T t, Function<T, R> func, String errmsg) {
        return optionOf(t).map(func).orElseThrow(() -> paramError(errmsg));
    }

    /**
     * list反转
     */
    default <T> List<T> reverse(List<T> list) {
        Collections.reverse(new ArrayList<>(list));
        return list;
    }

    /**
     * 自增
     */
    default Long increase(Long num) {
        return num + 1;
    }

    default Integer increase(Integer num) {
        return num + 1;
    }

    /**
     * I/O 将BufferedReader转化成json格式
     */
    default String bufferedReader2json(BufferedReader bufferedReader) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                sb.append(line);
            }
        } catch (Throwable e) {
            throw error(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * 将HttpResponse请求转化成json
     */
    default String resp2json(HttpResponse response) {
        try {
            return bufferedReader2json(
                    new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent(), "UTF-8")));
        } catch (Throwable e) {
            throw error(e.getMessage());
        }
    }

    /**
     * 从第三方Bean容器 获取缓存RedisService的实体
     */
    default RedisService redisMap() {
        if (isNull(OBJ.redisService)) {
            OBJ.redisService = SingleAppAutoConfiguration.getBean(RedisService.class);
        }
        return OBJ.redisService;
    }

    Long FIVEMIN = 1000 * 60 * 5L;

    Long WEEK = 1000 * 60 * 60 * 24 * 7L;

    Long DAY = 1000 * 60 * 60 * 24L;

    /**
     * bool缓存(五分钟)
     */
    default Function<BooleanSupplier, Boolean> boolRedis5Min(String key) {
        return getter -> boolRedis(key, getter, FIVEMIN);
    }

    /**
     * bool缓存(一天)
     */
    default Function<BooleanSupplier, Boolean> boolRedisDay(String key) {
        return getter -> boolRedis(key, getter, DAY);
    }

    /**
     * 从缓存中获取数据，如果缓存中没有则直接通过getter获取数据，并将结果存入缓存
     */
    default boolean boolRedis(String key, BooleanSupplier getter, long time) {
        if ("true".equals(redisMap().getString(key))) {
            return true;
        } else {
            final boolean result;
            if (result = getter.getAsBoolean()) {
                redisMap().putString(key, "true", time);
            }
            return result;
        }
    }

    /**
     * 缓存对象
     */
    default <T> String redisObj(String key, Supplier<T> getter, long time) {
        final String val = redisMap().getString(key); //从缓存中获取value信息
        final String json;
        if (StringUtil.isNotEmpty(val)) {
            json = val;
        } else {
            json = JSONUtil.obj2Json(getter.get());
            redisMap().putString(key, json, time);
        }
        return json;
    }

    /**
     * 缓存对象(一个星期)
     */
    default <T> Function<Supplier<T>, T> redisObjWeek(String key, Class<T> c) {
        return getter -> json2obj(redisObj(key, getter, WEEK), c);
    }

    /**
     * 缓存对象(五分钟)
     */
    default <T> Function<Supplier<T>, T> redisObj5Min(String key, Class<T> c) {
        return getter -> json2obj(redisObj(key, getter, FIVEMIN), c);
    }

    /**
     * 缓存对象(一天)
     */
    default <T> Function<Supplier<T>, T> redisObjDay(String key, Class<T> c) {
        return getter -> json2obj(redisObj(key, getter, DAY), c);
    }

    /**
     * 缓存String
     */
    default String redisString(String key, Supplier<String> getter, long time) {
        String val = redisMap().getString(key); //从缓存中获取value信息
        if (StringUtil.isNotEmpty(val)) {
            return val;
        } else {
            val = getter.get();
            redisMap().putString(key, val, time);
        }
        return val;
    }

    /**
     */
    default Function<Supplier<String>, String> redisString5Min(String key) {
        return getter -> redisString(key,getter,FIVEMIN);
    }

    /**
     */
    default Function<Supplier<String>, String> redisStringDay(String key) {
        return getter -> redisString(key,getter,DAY);
    }

    /**
     */
    default Function<Supplier<String>, String> redisStringWeek(String key) {
        return getter -> redisString(key,getter,WEEK);
    }

    /**
     * 清除多个缓存
     */
    default void deleteKeys(String... keys) {
        Stream.of(keys).forEach(redisMap()::delete);
    }

    /**
     * 执行一块只能被一个服务的一个线程访问的接口
     */
    default <T> Function<T,Object> block(String lockname, Consumer<T> c) {
        if (StringUtil.isNotEmpty(redisMap().getString(lockname))) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_UNKNOW,"缓存锁未解开请稍后");
        }
        redisMap().putString(lockname,"locked",1000 * 30L); //加一个三十秒的锁
        return t -> {
            c.accept(t);
            redisMap().delete(lockname);
            return null;
        };
    }

    /**
     * 获取一个自增数，如果自增数不存在则按照getter的规则生成自增数
     */
    default Long getIncrementValue(String key, Supplier<Long> getter, long time) {
        Long val = redisMap().getLong(key,0L); //从缓存中获取value信息
        if (NumberUtil.isValidId(val)) {
            final Long result = val + 1;
            redisMap().putLong(key,result,time);
            return result;
        } else {
            val = getter.get();
            redisMap().putLong(key, val, time);
        }
        return val;
    }

    /**
     * 获取一个自增数，缓存时长为一个星期
     */
    default Function<Supplier<Long>, Long> incrementValueWeek(String key) {
        return getter -> getIncrementValue(key,getter,WEEK);
    }

    default <T> Integer sumListbyInt(List<T> list, Function<T, Integer> getter) {
        return list.stream().map(getter).map(this::null2Zero).reduce(0, Integer::sum);
    }

    default <T> BigDecimal sumListbyBigDec(List<T> list, Function<T, BigDecimal> getter) {
        return list.stream().map(getter).filter(Objects::nonNull).reduce(new BigDecimal(0),BigDecimal::add);
    }

    default <T> Long sumListbyLong(List<T> list, Function<T, Long> getter) {
        return list.stream().map(getter).filter(Objects::nonNull).reduce(0L,Long::sum);
    }

    /**
     *
     */
    default <T> void print(T t) {
        OBJ.logger.info(t.toString());
    }

    /**
     * 等待若干毫秒
     */
    default long waitFor(long ms) {
        try {
            Thread.sleep(ms);
            print("等待" + ms + "ms");
        } catch (Exception e) {
            print("时间" + ms + e.getMessage());
            throw error(e.getMessage());
        }
        return ms;
    }

    /**
     * 生成8位 uuid
     */
    default String getShortUuid() {
        final StringBuilder stringBuilder = new StringBuilder();
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            stringBuilder.append(OBJ.chars[x % 0x3E]);
        }
        return stringBuilder.toString();
    }

    /**
     *
     */
    default <T> PageData<T> pagedata(List<T> list) {
        PageData<T> result = new PageData<>();
        result.count = Optional.ofNullable(list).map(List::size).map(e -> (long)e).orElse(0L);
        result.list = list;
        return result;
    }

    /**
     *
     */
    default <T> PageData<T> pagedata(Long count, List<T> list) {
        PageData<T> result = new PageData<>();
        result.count = count;
        result.list = list;
        return result;
    }

    /**
     * 获取byte值
     */
    default Byte byteValue(Integer num) {
        return Optional.ofNullable(num)
                .filter(e -> e > -128)
                .filter(e -> e < 127)
                .map(e -> (byte)(int)num)
                .orElse((byte)0);
    }

    /**
     * 获取byte值
     */
    default Byte byteValue(Long num) {
        return Optional.ofNullable(num)
                .filter(e -> e > -128)
                .filter(e -> e < 127)
                .map(e -> (byte)(long)num)
                .orElse((byte)0);
    }
}