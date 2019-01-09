package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventTicketOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public EventTicketOrderExample() {
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

        public Criteria andEventIdIsNull() {
            addCriterion("event_id is null");
            return (Criteria) this;
        }

        public Criteria andEventIdIsNotNull() {
            addCriterion("event_id is not null");
            return (Criteria) this;
        }

        public Criteria andEventIdEqualTo(Integer value) {
            addCriterion("event_id =", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotEqualTo(Integer value) {
            addCriterion("event_id <>", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThan(Integer value) {
            addCriterion("event_id >", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_id >=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThan(Integer value) {
            addCriterion("event_id <", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThanOrEqualTo(Integer value) {
            addCriterion("event_id <=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdIn(List<Integer> values) {
            addCriterion("event_id in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotIn(List<Integer> values) {
            addCriterion("event_id not in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdBetween(Integer value1, Integer value2) {
            addCriterion("event_id between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotBetween(Integer value1, Integer value2) {
            addCriterion("event_id not between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andTicketNoIsNull() {
            addCriterion("ticket_no is null");
            return (Criteria) this;
        }

        public Criteria andTicketNoIsNotNull() {
            addCriterion("ticket_no is not null");
            return (Criteria) this;
        }

        public Criteria andTicketNoEqualTo(String value) {
            addCriterion("ticket_no =", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotEqualTo(String value) {
            addCriterion("ticket_no <>", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoGreaterThan(String value) {
            addCriterion("ticket_no >", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoGreaterThanOrEqualTo(String value) {
            addCriterion("ticket_no >=", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLessThan(String value) {
            addCriterion("ticket_no <", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLessThanOrEqualTo(String value) {
            addCriterion("ticket_no <=", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoLike(String value) {
            addCriterion("ticket_no like", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotLike(String value) {
            addCriterion("ticket_no not like", value, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoIn(List<String> values) {
            addCriterion("ticket_no in", values, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotIn(List<String> values) {
            addCriterion("ticket_no not in", values, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoBetween(String value1, String value2) {
            addCriterion("ticket_no between", value1, value2, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andTicketNoNotBetween(String value1, String value2) {
            addCriterion("ticket_no not between", value1, value2, "ticketNo");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdIsNull() {
            addCriterion("commodity_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdIsNotNull() {
            addCriterion("commodity_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdEqualTo(Integer value) {
            addCriterion("commodity_detail_id =", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdNotEqualTo(Integer value) {
            addCriterion("commodity_detail_id <>", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdGreaterThan(Integer value) {
            addCriterion("commodity_detail_id >", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("commodity_detail_id >=", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdLessThan(Integer value) {
            addCriterion("commodity_detail_id <", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("commodity_detail_id <=", value, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdIn(List<Integer> values) {
            addCriterion("commodity_detail_id in", values, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdNotIn(List<Integer> values) {
            addCriterion("commodity_detail_id not in", values, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("commodity_detail_id between", value1, value2, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andCommodityDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("commodity_detail_id not between", value1, value2, "commodityDetailId");
            return (Criteria) this;
        }

        public Criteria andTicketStateIsNull() {
            addCriterion("ticket_state is null");
            return (Criteria) this;
        }

        public Criteria andTicketStateIsNotNull() {
            addCriterion("ticket_state is not null");
            return (Criteria) this;
        }

        public Criteria andTicketStateEqualTo(Integer value) {
            addCriterion("ticket_state =", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateNotEqualTo(Integer value) {
            addCriterion("ticket_state <>", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateGreaterThan(Integer value) {
            addCriterion("ticket_state >", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("ticket_state >=", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateLessThan(Integer value) {
            addCriterion("ticket_state <", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateLessThanOrEqualTo(Integer value) {
            addCriterion("ticket_state <=", value, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateIn(List<Integer> values) {
            addCriterion("ticket_state in", values, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateNotIn(List<Integer> values) {
            addCriterion("ticket_state not in", values, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateBetween(Integer value1, Integer value2) {
            addCriterion("ticket_state between", value1, value2, "ticketState");
            return (Criteria) this;
        }

        public Criteria andTicketStateNotBetween(Integer value1, Integer value2) {
            addCriterion("ticket_state not between", value1, value2, "ticketState");
            return (Criteria) this;
        }

        public Criteria andSignStateIsNull() {
            addCriterion("sign_state is null");
            return (Criteria) this;
        }

        public Criteria andSignStateIsNotNull() {
            addCriterion("sign_state is not null");
            return (Criteria) this;
        }

        public Criteria andSignStateEqualTo(Integer value) {
            addCriterion("sign_state =", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateNotEqualTo(Integer value) {
            addCriterion("sign_state <>", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateGreaterThan(Integer value) {
            addCriterion("sign_state >", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_state >=", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateLessThan(Integer value) {
            addCriterion("sign_state <", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateLessThanOrEqualTo(Integer value) {
            addCriterion("sign_state <=", value, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateIn(List<Integer> values) {
            addCriterion("sign_state in", values, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateNotIn(List<Integer> values) {
            addCriterion("sign_state not in", values, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateBetween(Integer value1, Integer value2) {
            addCriterion("sign_state between", value1, value2, "signState");
            return (Criteria) this;
        }

        public Criteria andSignStateNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_state not between", value1, value2, "signState");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNull() {
            addCriterion("sign_time is null");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNotNull() {
            addCriterion("sign_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignTimeEqualTo(Long value) {
            addCriterion("sign_time =", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotEqualTo(Long value) {
            addCriterion("sign_time <>", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThan(Long value) {
            addCriterion("sign_time >", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("sign_time >=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThan(Long value) {
            addCriterion("sign_time <", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThanOrEqualTo(Long value) {
            addCriterion("sign_time <=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIn(List<Long> values) {
            addCriterion("sign_time in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotIn(List<Long> values) {
            addCriterion("sign_time not in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeBetween(Long value1, Long value2) {
            addCriterion("sign_time between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotBetween(Long value1, Long value2) {
            addCriterion("sign_time not between", value1, value2, "signTime");
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

        public Criteria andTicketIdIsNull() {
            addCriterion("ticket_id is null");
            return (Criteria) this;
        }

        public Criteria andTicketIdIsNotNull() {
            addCriterion("ticket_id is not null");
            return (Criteria) this;
        }

        public Criteria andTicketIdEqualTo(Integer value) {
            addCriterion("ticket_id =", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdNotEqualTo(Integer value) {
            addCriterion("ticket_id <>", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdGreaterThan(Integer value) {
            addCriterion("ticket_id >", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ticket_id >=", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdLessThan(Integer value) {
            addCriterion("ticket_id <", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdLessThanOrEqualTo(Integer value) {
            addCriterion("ticket_id <=", value, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdIn(List<Integer> values) {
            addCriterion("ticket_id in", values, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdNotIn(List<Integer> values) {
            addCriterion("ticket_id not in", values, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdBetween(Integer value1, Integer value2) {
            addCriterion("ticket_id between", value1, value2, "ticketId");
            return (Criteria) this;
        }

        public Criteria andTicketIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ticket_id not between", value1, value2, "ticketId");
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