package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventRecordProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public EventRecordProjectExample() {
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

        public Criteria andEventRecordIdIsNull() {
            addCriterion("event_record_id is null");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdIsNotNull() {
            addCriterion("event_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdEqualTo(Integer value) {
            addCriterion("event_record_id =", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdNotEqualTo(Integer value) {
            addCriterion("event_record_id <>", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdGreaterThan(Integer value) {
            addCriterion("event_record_id >", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_record_id >=", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdLessThan(Integer value) {
            addCriterion("event_record_id <", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("event_record_id <=", value, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdIn(List<Integer> values) {
            addCriterion("event_record_id in", values, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdNotIn(List<Integer> values) {
            addCriterion("event_record_id not in", values, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("event_record_id between", value1, value2, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andEventRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("event_record_id not between", value1, value2, "eventRecordId");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andStarLevelIsNull() {
            addCriterion("star_level is null");
            return (Criteria) this;
        }

        public Criteria andStarLevelIsNotNull() {
            addCriterion("star_level is not null");
            return (Criteria) this;
        }

        public Criteria andStarLevelEqualTo(Byte value) {
            addCriterion("star_level =", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelNotEqualTo(Byte value) {
            addCriterion("star_level <>", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelGreaterThan(Byte value) {
            addCriterion("star_level >", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("star_level >=", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelLessThan(Byte value) {
            addCriterion("star_level <", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelLessThanOrEqualTo(Byte value) {
            addCriterion("star_level <=", value, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelIn(List<Byte> values) {
            addCriterion("star_level in", values, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelNotIn(List<Byte> values) {
            addCriterion("star_level not in", values, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelBetween(Byte value1, Byte value2) {
            addCriterion("star_level between", value1, value2, "starLevel");
            return (Criteria) this;
        }

        public Criteria andStarLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("star_level not between", value1, value2, "starLevel");
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

        public Criteria andSignUpTimeIsNull() {
            addCriterion("sign_up_time is null");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeIsNotNull() {
            addCriterion("sign_up_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeEqualTo(Long value) {
            addCriterion("sign_up_time =", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeNotEqualTo(Long value) {
            addCriterion("sign_up_time <>", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeGreaterThan(Long value) {
            addCriterion("sign_up_time >", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("sign_up_time >=", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeLessThan(Long value) {
            addCriterion("sign_up_time <", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeLessThanOrEqualTo(Long value) {
            addCriterion("sign_up_time <=", value, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeIn(List<Long> values) {
            addCriterion("sign_up_time in", values, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeNotIn(List<Long> values) {
            addCriterion("sign_up_time not in", values, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeBetween(Long value1, Long value2) {
            addCriterion("sign_up_time between", value1, value2, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andSignUpTimeNotBetween(Long value1, Long value2) {
            addCriterion("sign_up_time not between", value1, value2, "signUpTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountIsNull() {
            addCriterion("delivery_count is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountIsNotNull() {
            addCriterion("delivery_count is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountEqualTo(Integer value) {
            addCriterion("delivery_count =", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountNotEqualTo(Integer value) {
            addCriterion("delivery_count <>", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountGreaterThan(Integer value) {
            addCriterion("delivery_count >", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_count >=", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountLessThan(Integer value) {
            addCriterion("delivery_count <", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_count <=", value, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountIn(List<Integer> values) {
            addCriterion("delivery_count in", values, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountNotIn(List<Integer> values) {
            addCriterion("delivery_count not in", values, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountBetween(Integer value1, Integer value2) {
            addCriterion("delivery_count between", value1, value2, "deliveryCount");
            return (Criteria) this;
        }

        public Criteria andDeliveryCountNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_count not between", value1, value2, "deliveryCount");
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