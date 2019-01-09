package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventUaInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public EventUaInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andResourceIsNull() {
            addCriterion("resource is null");
            return (Criteria) this;
        }

        public Criteria andResourceIsNotNull() {
            addCriterion("resource is not null");
            return (Criteria) this;
        }

        public Criteria andResourceEqualTo(Byte value) {
            addCriterion("resource =", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotEqualTo(Byte value) {
            addCriterion("resource <>", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThan(Byte value) {
            addCriterion("resource >", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("resource >=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThan(Byte value) {
            addCriterion("resource <", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceLessThanOrEqualTo(Byte value) {
            addCriterion("resource <=", value, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceIn(List<Byte> values) {
            addCriterion("resource in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotIn(List<Byte> values) {
            addCriterion("resource not in", values, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceBetween(Byte value1, Byte value2) {
            addCriterion("resource between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andResourceNotBetween(Byte value1, Byte value2) {
            addCriterion("resource not between", value1, value2, "resource");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUaIsNull() {
            addCriterion("ua is null");
            return (Criteria) this;
        }

        public Criteria andUaIsNotNull() {
            addCriterion("ua is not null");
            return (Criteria) this;
        }

        public Criteria andUaEqualTo(String value) {
            addCriterion("ua =", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaNotEqualTo(String value) {
            addCriterion("ua <>", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaGreaterThan(String value) {
            addCriterion("ua >", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaGreaterThanOrEqualTo(String value) {
            addCriterion("ua >=", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaLessThan(String value) {
            addCriterion("ua <", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaLessThanOrEqualTo(String value) {
            addCriterion("ua <=", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaLike(String value) {
            addCriterion("ua like", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaNotLike(String value) {
            addCriterion("ua not like", value, "ua");
            return (Criteria) this;
        }

        public Criteria andUaIn(List<String> values) {
            addCriterion("ua in", values, "ua");
            return (Criteria) this;
        }

        public Criteria andUaNotIn(List<String> values) {
            addCriterion("ua not in", values, "ua");
            return (Criteria) this;
        }

        public Criteria andUaBetween(String value1, String value2) {
            addCriterion("ua between", value1, value2, "ua");
            return (Criteria) this;
        }

        public Criteria andUaNotBetween(String value1, String value2) {
            addCriterion("ua not between", value1, value2, "ua");
            return (Criteria) this;
        }

        public Criteria andBrowserNameIsNull() {
            addCriterion("browser_name is null");
            return (Criteria) this;
        }

        public Criteria andBrowserNameIsNotNull() {
            addCriterion("browser_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrowserNameEqualTo(String value) {
            addCriterion("browser_name =", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameNotEqualTo(String value) {
            addCriterion("browser_name <>", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameGreaterThan(String value) {
            addCriterion("browser_name >", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameGreaterThanOrEqualTo(String value) {
            addCriterion("browser_name >=", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameLessThan(String value) {
            addCriterion("browser_name <", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameLessThanOrEqualTo(String value) {
            addCriterion("browser_name <=", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameLike(String value) {
            addCriterion("browser_name like", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameNotLike(String value) {
            addCriterion("browser_name not like", value, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameIn(List<String> values) {
            addCriterion("browser_name in", values, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameNotIn(List<String> values) {
            addCriterion("browser_name not in", values, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameBetween(String value1, String value2) {
            addCriterion("browser_name between", value1, value2, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserNameNotBetween(String value1, String value2) {
            addCriterion("browser_name not between", value1, value2, "browserName");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionIsNull() {
            addCriterion("browser_version is null");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionIsNotNull() {
            addCriterion("browser_version is not null");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionEqualTo(String value) {
            addCriterion("browser_version =", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionNotEqualTo(String value) {
            addCriterion("browser_version <>", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionGreaterThan(String value) {
            addCriterion("browser_version >", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionGreaterThanOrEqualTo(String value) {
            addCriterion("browser_version >=", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionLessThan(String value) {
            addCriterion("browser_version <", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionLessThanOrEqualTo(String value) {
            addCriterion("browser_version <=", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionLike(String value) {
            addCriterion("browser_version like", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionNotLike(String value) {
            addCriterion("browser_version not like", value, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionIn(List<String> values) {
            addCriterion("browser_version in", values, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionNotIn(List<String> values) {
            addCriterion("browser_version not in", values, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionBetween(String value1, String value2) {
            addCriterion("browser_version between", value1, value2, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserVersionNotBetween(String value1, String value2) {
            addCriterion("browser_version not between", value1, value2, "browserVersion");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorIsNull() {
            addCriterion("browser_major is null");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorIsNotNull() {
            addCriterion("browser_major is not null");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorEqualTo(String value) {
            addCriterion("browser_major =", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorNotEqualTo(String value) {
            addCriterion("browser_major <>", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorGreaterThan(String value) {
            addCriterion("browser_major >", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorGreaterThanOrEqualTo(String value) {
            addCriterion("browser_major >=", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorLessThan(String value) {
            addCriterion("browser_major <", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorLessThanOrEqualTo(String value) {
            addCriterion("browser_major <=", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorLike(String value) {
            addCriterion("browser_major like", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorNotLike(String value) {
            addCriterion("browser_major not like", value, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorIn(List<String> values) {
            addCriterion("browser_major in", values, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorNotIn(List<String> values) {
            addCriterion("browser_major not in", values, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorBetween(String value1, String value2) {
            addCriterion("browser_major between", value1, value2, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andBrowserMajorNotBetween(String value1, String value2) {
            addCriterion("browser_major not between", value1, value2, "browserMajor");
            return (Criteria) this;
        }

        public Criteria andEngineNameIsNull() {
            addCriterion("engine_name is null");
            return (Criteria) this;
        }

        public Criteria andEngineNameIsNotNull() {
            addCriterion("engine_name is not null");
            return (Criteria) this;
        }

        public Criteria andEngineNameEqualTo(String value) {
            addCriterion("engine_name =", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameNotEqualTo(String value) {
            addCriterion("engine_name <>", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameGreaterThan(String value) {
            addCriterion("engine_name >", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameGreaterThanOrEqualTo(String value) {
            addCriterion("engine_name >=", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameLessThan(String value) {
            addCriterion("engine_name <", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameLessThanOrEqualTo(String value) {
            addCriterion("engine_name <=", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameLike(String value) {
            addCriterion("engine_name like", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameNotLike(String value) {
            addCriterion("engine_name not like", value, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameIn(List<String> values) {
            addCriterion("engine_name in", values, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameNotIn(List<String> values) {
            addCriterion("engine_name not in", values, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameBetween(String value1, String value2) {
            addCriterion("engine_name between", value1, value2, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineNameNotBetween(String value1, String value2) {
            addCriterion("engine_name not between", value1, value2, "engineName");
            return (Criteria) this;
        }

        public Criteria andEngineVersionIsNull() {
            addCriterion("engine_version is null");
            return (Criteria) this;
        }

        public Criteria andEngineVersionIsNotNull() {
            addCriterion("engine_version is not null");
            return (Criteria) this;
        }

        public Criteria andEngineVersionEqualTo(String value) {
            addCriterion("engine_version =", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionNotEqualTo(String value) {
            addCriterion("engine_version <>", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionGreaterThan(String value) {
            addCriterion("engine_version >", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionGreaterThanOrEqualTo(String value) {
            addCriterion("engine_version >=", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionLessThan(String value) {
            addCriterion("engine_version <", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionLessThanOrEqualTo(String value) {
            addCriterion("engine_version <=", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionLike(String value) {
            addCriterion("engine_version like", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionNotLike(String value) {
            addCriterion("engine_version not like", value, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionIn(List<String> values) {
            addCriterion("engine_version in", values, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionNotIn(List<String> values) {
            addCriterion("engine_version not in", values, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionBetween(String value1, String value2) {
            addCriterion("engine_version between", value1, value2, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andEngineVersionNotBetween(String value1, String value2) {
            addCriterion("engine_version not between", value1, value2, "engineVersion");
            return (Criteria) this;
        }

        public Criteria andOsNameIsNull() {
            addCriterion("os_name is null");
            return (Criteria) this;
        }

        public Criteria andOsNameIsNotNull() {
            addCriterion("os_name is not null");
            return (Criteria) this;
        }

        public Criteria andOsNameEqualTo(String value) {
            addCriterion("os_name =", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameNotEqualTo(String value) {
            addCriterion("os_name <>", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameGreaterThan(String value) {
            addCriterion("os_name >", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameGreaterThanOrEqualTo(String value) {
            addCriterion("os_name >=", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameLessThan(String value) {
            addCriterion("os_name <", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameLessThanOrEqualTo(String value) {
            addCriterion("os_name <=", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameLike(String value) {
            addCriterion("os_name like", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameNotLike(String value) {
            addCriterion("os_name not like", value, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameIn(List<String> values) {
            addCriterion("os_name in", values, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameNotIn(List<String> values) {
            addCriterion("os_name not in", values, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameBetween(String value1, String value2) {
            addCriterion("os_name between", value1, value2, "osName");
            return (Criteria) this;
        }

        public Criteria andOsNameNotBetween(String value1, String value2) {
            addCriterion("os_name not between", value1, value2, "osName");
            return (Criteria) this;
        }

        public Criteria andOsVersionIsNull() {
            addCriterion("os_version is null");
            return (Criteria) this;
        }

        public Criteria andOsVersionIsNotNull() {
            addCriterion("os_version is not null");
            return (Criteria) this;
        }

        public Criteria andOsVersionEqualTo(String value) {
            addCriterion("os_version =", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionNotEqualTo(String value) {
            addCriterion("os_version <>", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionGreaterThan(String value) {
            addCriterion("os_version >", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionGreaterThanOrEqualTo(String value) {
            addCriterion("os_version >=", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionLessThan(String value) {
            addCriterion("os_version <", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionLessThanOrEqualTo(String value) {
            addCriterion("os_version <=", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionLike(String value) {
            addCriterion("os_version like", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionNotLike(String value) {
            addCriterion("os_version not like", value, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionIn(List<String> values) {
            addCriterion("os_version in", values, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionNotIn(List<String> values) {
            addCriterion("os_version not in", values, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionBetween(String value1, String value2) {
            addCriterion("os_version between", value1, value2, "osVersion");
            return (Criteria) this;
        }

        public Criteria andOsVersionNotBetween(String value1, String value2) {
            addCriterion("os_version not between", value1, value2, "osVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIsNull() {
            addCriterion("device_model is null");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIsNotNull() {
            addCriterion("device_model is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceModelEqualTo(String value) {
            addCriterion("device_model =", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotEqualTo(String value) {
            addCriterion("device_model <>", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelGreaterThan(String value) {
            addCriterion("device_model >", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelGreaterThanOrEqualTo(String value) {
            addCriterion("device_model >=", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLessThan(String value) {
            addCriterion("device_model <", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLessThanOrEqualTo(String value) {
            addCriterion("device_model <=", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelLike(String value) {
            addCriterion("device_model like", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotLike(String value) {
            addCriterion("device_model not like", value, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelIn(List<String> values) {
            addCriterion("device_model in", values, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotIn(List<String> values) {
            addCriterion("device_model not in", values, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelBetween(String value1, String value2) {
            addCriterion("device_model between", value1, value2, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceModelNotBetween(String value1, String value2) {
            addCriterion("device_model not between", value1, value2, "deviceModel");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("device_type is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("device_type is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("device_type =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(String value) {
            addCriterion("device_type <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(String value) {
            addCriterion("device_type >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("device_type >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(String value) {
            addCriterion("device_type <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(String value) {
            addCriterion("device_type <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLike(String value) {
            addCriterion("device_type like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotLike(String value) {
            addCriterion("device_type not like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("device_type in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<String> values) {
            addCriterion("device_type not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(String value1, String value2) {
            addCriterion("device_type between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(String value1, String value2) {
            addCriterion("device_type not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorIsNull() {
            addCriterion("device_vendor is null");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorIsNotNull() {
            addCriterion("device_vendor is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorEqualTo(String value) {
            addCriterion("device_vendor =", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorNotEqualTo(String value) {
            addCriterion("device_vendor <>", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorGreaterThan(String value) {
            addCriterion("device_vendor >", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorGreaterThanOrEqualTo(String value) {
            addCriterion("device_vendor >=", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorLessThan(String value) {
            addCriterion("device_vendor <", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorLessThanOrEqualTo(String value) {
            addCriterion("device_vendor <=", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorLike(String value) {
            addCriterion("device_vendor like", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorNotLike(String value) {
            addCriterion("device_vendor not like", value, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorIn(List<String> values) {
            addCriterion("device_vendor in", values, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorNotIn(List<String> values) {
            addCriterion("device_vendor not in", values, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorBetween(String value1, String value2) {
            addCriterion("device_vendor between", value1, value2, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andDeviceVendorNotBetween(String value1, String value2) {
            addCriterion("device_vendor not between", value1, value2, "deviceVendor");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureIsNull() {
            addCriterion("cpu_architecture is null");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureIsNotNull() {
            addCriterion("cpu_architecture is not null");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureEqualTo(String value) {
            addCriterion("cpu_architecture =", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureNotEqualTo(String value) {
            addCriterion("cpu_architecture <>", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureGreaterThan(String value) {
            addCriterion("cpu_architecture >", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureGreaterThanOrEqualTo(String value) {
            addCriterion("cpu_architecture >=", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureLessThan(String value) {
            addCriterion("cpu_architecture <", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureLessThanOrEqualTo(String value) {
            addCriterion("cpu_architecture <=", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureLike(String value) {
            addCriterion("cpu_architecture like", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureNotLike(String value) {
            addCriterion("cpu_architecture not like", value, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureIn(List<String> values) {
            addCriterion("cpu_architecture in", values, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureNotIn(List<String> values) {
            addCriterion("cpu_architecture not in", values, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureBetween(String value1, String value2) {
            addCriterion("cpu_architecture between", value1, value2, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCpuArchitectureNotBetween(String value1, String value2) {
            addCriterion("cpu_architecture not between", value1, value2, "cpuArchitecture");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Long value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Long value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Long value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Long value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Long value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Long> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Long> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Long value1, Long value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Long value1, Long value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Long value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Long value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Long value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Long value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Long value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Long> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Long> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Long value1, Long value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Long value1, Long value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}