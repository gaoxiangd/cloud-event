package com.welian.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by zhaopu on 2018/6/28.
 */
public class OrderNoGenerator {

    private int size;
    private int length;
    private List<String> orderNoList;
    private Set<String> orderNoSet;

    public static int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static Random random = new Random();


    /**
     * 订单号生成器
     * <p>
     * 为了保证生成性能需满足条件size<10^length/4
     *
     * @param size   保证连续不重读的数
     * @param length 生成随机数的长度
     */
    public OrderNoGenerator(int size, int length) {
        this.size = size;
        this.length = length;
        AssertUtils.requireTrue(size < Math.pow(10, length) / 4, "参数不符合要求");
        orderNoList = new ArrayList<>();
        orderNoSet = new HashSet<>();
    }

    /**
     * 获取不重复的随机数
     *
     * @return
     */
    public synchronized String generatorOrderNo() {
        String randomNumber = randomNumber(length);
        while (orderNoSet.contains(randomNumber)) {
            randomNumber = randomNumber(length);
        }
        orderNoList.add(randomNumber);
        orderNoSet.add(randomNumber);
        reduce();
        return randomNumber;
    }

    private void reduce() {
        if (orderNoList.size() >= size * 2) {
            List<String> removes = orderNoList.subList(0, size);
            orderNoSet.removeAll(removes);
            removes.clear();
        }
    }


    /**
     * 生成随机数字
     *
     * @param length 随机数长度
     * @return
     */
    public String randomNumber(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            builder.append(nums[index]);
        }
        return builder.toString();
    }

}
