package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public EventExample() {
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

    protected String groupByClause;

    public String getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
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

        public Criteria andPublishUidIsNull() {
            addCriterion("publish_uid is null");
            return (Criteria) this;
        }

        public Criteria andPublishUidIsNotNull() {
            addCriterion("publish_uid is not null");
            return (Criteria) this;
        }

        public Criteria andPublishUidEqualTo(Integer value) {
            addCriterion("publish_uid =", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidNotEqualTo(Integer value) {
            addCriterion("publish_uid <>", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidGreaterThan(Integer value) {
            addCriterion("publish_uid >", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_uid >=", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidLessThan(Integer value) {
            addCriterion("publish_uid <", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidLessThanOrEqualTo(Integer value) {
            addCriterion("publish_uid <=", value, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidIn(List<Integer> values) {
            addCriterion("publish_uid in", values, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidNotIn(List<Integer> values) {
            addCriterion("publish_uid not in", values, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidBetween(Integer value1, Integer value2) {
            addCriterion("publish_uid between", value1, value2, "publishUid");
            return (Criteria) this;
        }

        public Criteria andPublishUidNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_uid not between", value1, value2, "publishUid");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andLineTypeIsNull() {
            addCriterion("line_type is null");
            return (Criteria) this;
        }

        public Criteria andLineTypeIsNotNull() {
            addCriterion("line_type is not null");
            return (Criteria) this;
        }

        public Criteria andLineTypeEqualTo(Byte value) {
            addCriterion("line_type =", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotEqualTo(Byte value) {
            addCriterion("line_type <>", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThan(Byte value) {
            addCriterion("line_type >", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("line_type >=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThan(Byte value) {
            addCriterion("line_type <", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThanOrEqualTo(Byte value) {
            addCriterion("line_type <=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeIn(List<Byte> values) {
            addCriterion("line_type in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotIn(List<Byte> values) {
            addCriterion("line_type not in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeBetween(Byte value1, Byte value2) {
            addCriterion("line_type between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("line_type not between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }

        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }

        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Long value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Long value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Long value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Long value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Long value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Long> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Long> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Long value1, Long value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Long value1, Long value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Long value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Long value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Long value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Long value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Long value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Long> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Long> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Long value1, Long value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Long value1, Long value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeIsNull() {
            addCriterion("deadline_time is null");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeIsNotNull() {
            addCriterion("deadline_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeEqualTo(Long value) {
            addCriterion("deadline_time =", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeNotEqualTo(Long value) {
            addCriterion("deadline_time <>", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeGreaterThan(Long value) {
            addCriterion("deadline_time >", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("deadline_time >=", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeLessThan(Long value) {
            addCriterion("deadline_time <", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeLessThanOrEqualTo(Long value) {
            addCriterion("deadline_time <=", value, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeIn(List<Long> values) {
            addCriterion("deadline_time in", values, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeNotIn(List<Long> values) {
            addCriterion("deadline_time not in", values, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeBetween(Long value1, Long value2) {
            addCriterion("deadline_time between", value1, value2, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andDeadlineTimeNotBetween(Long value1, Long value2) {
            addCriterion("deadline_time not between", value1, value2, "deadlineTime");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionIsNull() {
            addCriterion("open_extension is null");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionIsNotNull() {
            addCriterion("open_extension is not null");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionEqualTo(Byte value) {
            addCriterion("open_extension =", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionNotEqualTo(Byte value) {
            addCriterion("open_extension <>", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionGreaterThan(Byte value) {
            addCriterion("open_extension >", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionGreaterThanOrEqualTo(Byte value) {
            addCriterion("open_extension >=", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionLessThan(Byte value) {
            addCriterion("open_extension <", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionLessThanOrEqualTo(Byte value) {
            addCriterion("open_extension <=", value, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionIn(List<Byte> values) {
            addCriterion("open_extension in", values, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionNotIn(List<Byte> values) {
            addCriterion("open_extension not in", values, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionBetween(Byte value1, Byte value2) {
            addCriterion("open_extension between", value1, value2, "openExtension");
            return (Criteria) this;
        }

        public Criteria andOpenExtensionNotBetween(Byte value1, Byte value2) {
            addCriterion("open_extension not between", value1, value2, "openExtension");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountIsNull() {
            addCriterion("favorite_count is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountIsNotNull() {
            addCriterion("favorite_count is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountEqualTo(Integer value) {
            addCriterion("favorite_count =", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountNotEqualTo(Integer value) {
            addCriterion("favorite_count <>", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountGreaterThan(Integer value) {
            addCriterion("favorite_count >", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorite_count >=", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountLessThan(Integer value) {
            addCriterion("favorite_count <", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountLessThanOrEqualTo(Integer value) {
            addCriterion("favorite_count <=", value, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountIn(List<Integer> values) {
            addCriterion("favorite_count in", values, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountNotIn(List<Integer> values) {
            addCriterion("favorite_count not in", values, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountBetween(Integer value1, Integer value2) {
            addCriterion("favorite_count between", value1, value2, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andFavoriteCountNotBetween(Integer value1, Integer value2) {
            addCriterion("favorite_count not between", value1, value2, "favoriteCount");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Byte value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Byte value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Byte value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Byte value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Byte value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Byte> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Byte> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Byte value1, Byte value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Byte value1, Byte value2) {
            addCriterion("state not between", value1, value2, "state");
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

        public Criteria andRecommendStatusIsNull() {
            addCriterion("recommend_status is null");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusIsNotNull() {
            addCriterion("recommend_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusEqualTo(Byte value) {
            addCriterion("recommend_status =", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusNotEqualTo(Byte value) {
            addCriterion("recommend_status <>", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusGreaterThan(Byte value) {
            addCriterion("recommend_status >", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("recommend_status >=", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusLessThan(Byte value) {
            addCriterion("recommend_status <", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusLessThanOrEqualTo(Byte value) {
            addCriterion("recommend_status <=", value, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusIn(List<Byte> values) {
            addCriterion("recommend_status in", values, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusNotIn(List<Byte> values) {
            addCriterion("recommend_status not in", values, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusBetween(Byte value1, Byte value2) {
            addCriterion("recommend_status between", value1, value2, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andRecommendStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("recommend_status not between", value1, value2, "recommendStatus");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountIsNull() {
            addCriterion("link_url_count is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountIsNotNull() {
            addCriterion("link_url_count is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountEqualTo(Integer value) {
            addCriterion("link_url_count =", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountNotEqualTo(Integer value) {
            addCriterion("link_url_count <>", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountGreaterThan(Integer value) {
            addCriterion("link_url_count >", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("link_url_count >=", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountLessThan(Integer value) {
            addCriterion("link_url_count <", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountLessThanOrEqualTo(Integer value) {
            addCriterion("link_url_count <=", value, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountIn(List<Integer> values) {
            addCriterion("link_url_count in", values, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountNotIn(List<Integer> values) {
            addCriterion("link_url_count not in", values, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountBetween(Integer value1, Integer value2) {
            addCriterion("link_url_count between", value1, value2, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCountNotBetween(Integer value1, Integer value2) {
            addCriterion("link_url_count not between", value1, value2, "linkUrlCount");
            return (Criteria) this;
        }

        public Criteria andAdCountIsNull() {
            addCriterion("ad_count is null");
            return (Criteria) this;
        }

        public Criteria andAdCountIsNotNull() {
            addCriterion("ad_count is not null");
            return (Criteria) this;
        }

        public Criteria andAdCountEqualTo(Integer value) {
            addCriterion("ad_count =", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountNotEqualTo(Integer value) {
            addCriterion("ad_count <>", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountGreaterThan(Integer value) {
            addCriterion("ad_count >", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_count >=", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountLessThan(Integer value) {
            addCriterion("ad_count <", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountLessThanOrEqualTo(Integer value) {
            addCriterion("ad_count <=", value, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountIn(List<Integer> values) {
            addCriterion("ad_count in", values, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountNotIn(List<Integer> values) {
            addCriterion("ad_count not in", values, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountBetween(Integer value1, Integer value2) {
            addCriterion("ad_count between", value1, value2, "adCount");
            return (Criteria) this;
        }

        public Criteria andAdCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_count not between", value1, value2, "adCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountIsNull() {
            addCriterion("detail_browse_count is null");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountIsNotNull() {
            addCriterion("detail_browse_count is not null");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountEqualTo(Integer value) {
            addCriterion("detail_browse_count =", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountNotEqualTo(Integer value) {
            addCriterion("detail_browse_count <>", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountGreaterThan(Integer value) {
            addCriterion("detail_browse_count >", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("detail_browse_count >=", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountLessThan(Integer value) {
            addCriterion("detail_browse_count <", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountLessThanOrEqualTo(Integer value) {
            addCriterion("detail_browse_count <=", value, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountIn(List<Integer> values) {
            addCriterion("detail_browse_count in", values, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountNotIn(List<Integer> values) {
            addCriterion("detail_browse_count not in", values, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountBetween(Integer value1, Integer value2) {
            addCriterion("detail_browse_count between", value1, value2, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andDetailBrowseCountNotBetween(Integer value1, Integer value2) {
            addCriterion("detail_browse_count not between", value1, value2, "detailBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountIsNull() {
            addCriterion("form_browse_count is null");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountIsNotNull() {
            addCriterion("form_browse_count is not null");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountEqualTo(Integer value) {
            addCriterion("form_browse_count =", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountNotEqualTo(Integer value) {
            addCriterion("form_browse_count <>", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountGreaterThan(Integer value) {
            addCriterion("form_browse_count >", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("form_browse_count >=", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountLessThan(Integer value) {
            addCriterion("form_browse_count <", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountLessThanOrEqualTo(Integer value) {
            addCriterion("form_browse_count <=", value, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountIn(List<Integer> values) {
            addCriterion("form_browse_count in", values, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountNotIn(List<Integer> values) {
            addCriterion("form_browse_count not in", values, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountBetween(Integer value1, Integer value2) {
            addCriterion("form_browse_count between", value1, value2, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andFormBrowseCountNotBetween(Integer value1, Integer value2) {
            addCriterion("form_browse_count not between", value1, value2, "formBrowseCount");
            return (Criteria) this;
        }

        public Criteria andCommodityIdIsNull() {
            addCriterion("commodity_id is null");
            return (Criteria) this;
        }

        public Criteria andCommodityIdIsNotNull() {
            addCriterion("commodity_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityIdEqualTo(Integer value) {
            addCriterion("commodity_id =", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotEqualTo(Integer value) {
            addCriterion("commodity_id <>", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdGreaterThan(Integer value) {
            addCriterion("commodity_id >", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("commodity_id >=", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdLessThan(Integer value) {
            addCriterion("commodity_id <", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdLessThanOrEqualTo(Integer value) {
            addCriterion("commodity_id <=", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdIn(List<Integer> values) {
            addCriterion("commodity_id in", values, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotIn(List<Integer> values) {
            addCriterion("commodity_id not in", values, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdBetween(Integer value1, Integer value2) {
            addCriterion("commodity_id between", value1, value2, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("commodity_id not between", value1, value2, "commodityId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdIsNull() {
            addCriterion("old_event_id is null");
            return (Criteria) this;
        }

        public Criteria andOldEventIdIsNotNull() {
            addCriterion("old_event_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldEventIdEqualTo(Integer value) {
            addCriterion("old_event_id =", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdNotEqualTo(Integer value) {
            addCriterion("old_event_id <>", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdGreaterThan(Integer value) {
            addCriterion("old_event_id >", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_event_id >=", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdLessThan(Integer value) {
            addCriterion("old_event_id <", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_event_id <=", value, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdIn(List<Integer> values) {
            addCriterion("old_event_id in", values, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdNotIn(List<Integer> values) {
            addCriterion("old_event_id not in", values, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdBetween(Integer value1, Integer value2) {
            addCriterion("old_event_id between", value1, value2, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andOldEventIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_event_id not between", value1, value2, "oldEventId");
            return (Criteria) this;
        }

        public Criteria andCouponStatusIsNull() {
            addCriterion("coupon_status is null");
            return (Criteria) this;
        }

        public Criteria andCouponStatusIsNotNull() {
            addCriterion("coupon_status is not null");
            return (Criteria) this;
        }

        public Criteria andCouponStatusEqualTo(Integer value) {
            addCriterion("coupon_status =", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusNotEqualTo(Integer value) {
            addCriterion("coupon_status <>", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusGreaterThan(Integer value) {
            addCriterion("coupon_status >", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupon_status >=", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusLessThan(Integer value) {
            addCriterion("coupon_status <", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusLessThanOrEqualTo(Integer value) {
            addCriterion("coupon_status <=", value, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusIn(List<Integer> values) {
            addCriterion("coupon_status in", values, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusNotIn(List<Integer> values) {
            addCriterion("coupon_status not in", values, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusBetween(Integer value1, Integer value2) {
            addCriterion("coupon_status between", value1, value2, "couponStatus");
            return (Criteria) this;
        }

        public Criteria andCouponStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("coupon_status not between", value1, value2, "couponStatus");
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