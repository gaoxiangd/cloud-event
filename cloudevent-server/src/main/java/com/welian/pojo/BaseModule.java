package com.welian.pojo;

import java.util.List;

/**
 * Created by dayangshu on 17/7/7.
 */
public abstract class BaseModule<T> {

    /**
     * 批量保存活动的关联信息
     *
     * @param eventId
     * @param object
     */
    public void save(Integer eventId, List<T> object) {

    }

    /**
     * 保存活动的关联信息
     *
     * @param eventId
     * @param object
     */
    public void save(Integer eventId, T object) {

    }

    /**
     * 删除活动的关联信息
     *
     * @param eventId
     */
    public abstract void delete(Integer eventId);

    /**
     * 根据活动id获取关联信息
     *
     * @param eventId
     * @return
     */
    public abstract Object get(Integer eventId);

    /**
     * 转换
     *
     * @param object
     * @return
     */
    public abstract Object conversePara(Object object);


}
