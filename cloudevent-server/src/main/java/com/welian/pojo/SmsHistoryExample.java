package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class SmsHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public SmsHistoryExample() {
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

        public Criteria andEventIdsIsNull() {
            addCriterion("event_ids is null");
            return (Criteria) this;
        }

        public Criteria andEventIdsIsNotNull() {
            addCriterion("event_ids is not null");
            return (Criteria) this;
        }

        public Criteria andEventIdsEqualTo(String value) {
            addCriterion("event_ids =", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsNotEqualTo(String value) {
            addCriterion("event_ids <>", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsGreaterThan(String value) {
            addCriterion("event_ids >", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsGreaterThanOrEqualTo(String value) {
            addCriterion("event_ids >=", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsLessThan(String value) {
            addCriterion("event_ids <", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsLessThanOrEqualTo(String value) {
            addCriterion("event_ids <=", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsLike(String value) {
            addCriterion("event_ids like", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsNotLike(String value) {
            addCriterion("event_ids not like", value, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsIn(List<String> values) {
            addCriterion("event_ids in", values, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsNotIn(List<String> values) {
            addCriterion("event_ids not in", values, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsBetween(String value1, String value2) {
            addCriterion("event_ids between", value1, value2, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventIdsNotBetween(String value1, String value2) {
            addCriterion("event_ids not between", value1, value2, "eventIds");
            return (Criteria) this;
        }

        public Criteria andEventTitlesIsNull() {
            addCriterion("event_titles is null");
            return (Criteria) this;
        }

        public Criteria andEventTitlesIsNotNull() {
            addCriterion("event_titles is not null");
            return (Criteria) this;
        }

        public Criteria andEventTitlesEqualTo(String value) {
            addCriterion("event_titles =", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesNotEqualTo(String value) {
            addCriterion("event_titles <>", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesGreaterThan(String value) {
            addCriterion("event_titles >", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesGreaterThanOrEqualTo(String value) {
            addCriterion("event_titles >=", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesLessThan(String value) {
            addCriterion("event_titles <", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesLessThanOrEqualTo(String value) {
            addCriterion("event_titles <=", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesLike(String value) {
            addCriterion("event_titles like", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesNotLike(String value) {
            addCriterion("event_titles not like", value, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesIn(List<String> values) {
            addCriterion("event_titles in", values, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesNotIn(List<String> values) {
            addCriterion("event_titles not in", values, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesBetween(String value1, String value2) {
            addCriterion("event_titles between", value1, value2, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andEventTitlesNotBetween(String value1, String value2) {
            addCriterion("event_titles not between", value1, value2, "eventTitles");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountIsNull() {
            addCriterion("push_sms_count is null");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountIsNotNull() {
            addCriterion("push_sms_count is not null");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountEqualTo(Integer value) {
            addCriterion("push_sms_count =", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountNotEqualTo(Integer value) {
            addCriterion("push_sms_count <>", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountGreaterThan(Integer value) {
            addCriterion("push_sms_count >", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("push_sms_count >=", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountLessThan(Integer value) {
            addCriterion("push_sms_count <", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountLessThanOrEqualTo(Integer value) {
            addCriterion("push_sms_count <=", value, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountIn(List<Integer> values) {
            addCriterion("push_sms_count in", values, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountNotIn(List<Integer> values) {
            addCriterion("push_sms_count not in", values, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountBetween(Integer value1, Integer value2) {
            addCriterion("push_sms_count between", value1, value2, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andPushSmsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("push_sms_count not between", value1, value2, "pushSmsCount");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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