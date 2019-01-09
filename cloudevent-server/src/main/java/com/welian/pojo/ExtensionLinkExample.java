package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class ExtensionLinkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public ExtensionLinkExample() {
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

        public Criteria andLinkNameIsNull() {
            addCriterion("link_name is null");
            return (Criteria) this;
        }

        public Criteria andLinkNameIsNotNull() {
            addCriterion("link_name is not null");
            return (Criteria) this;
        }

        public Criteria andLinkNameEqualTo(String value) {
            addCriterion("link_name =", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotEqualTo(String value) {
            addCriterion("link_name <>", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameGreaterThan(String value) {
            addCriterion("link_name >", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameGreaterThanOrEqualTo(String value) {
            addCriterion("link_name >=", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLessThan(String value) {
            addCriterion("link_name <", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLessThanOrEqualTo(String value) {
            addCriterion("link_name <=", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLike(String value) {
            addCriterion("link_name like", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotLike(String value) {
            addCriterion("link_name not like", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameIn(List<String> values) {
            addCriterion("link_name in", values, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotIn(List<String> values) {
            addCriterion("link_name not in", values, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameBetween(String value1, String value2) {
            addCriterion("link_name between", value1, value2, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotBetween(String value1, String value2) {
            addCriterion("link_name not between", value1, value2, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormIsNull() {
            addCriterion("link_url_form is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormIsNotNull() {
            addCriterion("link_url_form is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormEqualTo(String value) {
            addCriterion("link_url_form =", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormNotEqualTo(String value) {
            addCriterion("link_url_form <>", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormGreaterThan(String value) {
            addCriterion("link_url_form >", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormGreaterThanOrEqualTo(String value) {
            addCriterion("link_url_form >=", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormLessThan(String value) {
            addCriterion("link_url_form <", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormLessThanOrEqualTo(String value) {
            addCriterion("link_url_form <=", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormLike(String value) {
            addCriterion("link_url_form like", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormNotLike(String value) {
            addCriterion("link_url_form not like", value, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormIn(List<String> values) {
            addCriterion("link_url_form in", values, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormNotIn(List<String> values) {
            addCriterion("link_url_form not in", values, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormBetween(String value1, String value2) {
            addCriterion("link_url_form between", value1, value2, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlFormNotBetween(String value1, String value2) {
            addCriterion("link_url_form not between", value1, value2, "linkUrlForm");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonIsNull() {
            addCriterion("link_url_common is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonIsNotNull() {
            addCriterion("link_url_common is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonEqualTo(String value) {
            addCriterion("link_url_common =", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonNotEqualTo(String value) {
            addCriterion("link_url_common <>", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonGreaterThan(String value) {
            addCriterion("link_url_common >", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonGreaterThanOrEqualTo(String value) {
            addCriterion("link_url_common >=", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonLessThan(String value) {
            addCriterion("link_url_common <", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonLessThanOrEqualTo(String value) {
            addCriterion("link_url_common <=", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonLike(String value) {
            addCriterion("link_url_common like", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonNotLike(String value) {
            addCriterion("link_url_common not like", value, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonIn(List<String> values) {
            addCriterion("link_url_common in", values, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonNotIn(List<String> values) {
            addCriterion("link_url_common not in", values, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonBetween(String value1, String value2) {
            addCriterion("link_url_common between", value1, value2, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCommonNotBetween(String value1, String value2) {
            addCriterion("link_url_common not between", value1, value2, "linkUrlCommon");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomIsNull() {
            addCriterion("link_url_custom is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomIsNotNull() {
            addCriterion("link_url_custom is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomEqualTo(String value) {
            addCriterion("link_url_custom =", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomNotEqualTo(String value) {
            addCriterion("link_url_custom <>", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomGreaterThan(String value) {
            addCriterion("link_url_custom >", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomGreaterThanOrEqualTo(String value) {
            addCriterion("link_url_custom >=", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomLessThan(String value) {
            addCriterion("link_url_custom <", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomLessThanOrEqualTo(String value) {
            addCriterion("link_url_custom <=", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomLike(String value) {
            addCriterion("link_url_custom like", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomNotLike(String value) {
            addCriterion("link_url_custom not like", value, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomIn(List<String> values) {
            addCriterion("link_url_custom in", values, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomNotIn(List<String> values) {
            addCriterion("link_url_custom not in", values, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomBetween(String value1, String value2) {
            addCriterion("link_url_custom between", value1, value2, "linkUrlCustom");
            return (Criteria) this;
        }

        public Criteria andLinkUrlCustomNotBetween(String value1, String value2) {
            addCriterion("link_url_custom not between", value1, value2, "linkUrlCustom");
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

        public Criteria andLinkTypeIsNull() {
            addCriterion("link_type is null");
            return (Criteria) this;
        }

        public Criteria andLinkTypeIsNotNull() {
            addCriterion("link_type is not null");
            return (Criteria) this;
        }

        public Criteria andLinkTypeEqualTo(Byte value) {
            addCriterion("link_type =", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotEqualTo(Byte value) {
            addCriterion("link_type <>", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeGreaterThan(Byte value) {
            addCriterion("link_type >", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("link_type >=", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeLessThan(Byte value) {
            addCriterion("link_type <", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeLessThanOrEqualTo(Byte value) {
            addCriterion("link_type <=", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeIn(List<Byte> values) {
            addCriterion("link_type in", values, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotIn(List<Byte> values) {
            addCriterion("link_type not in", values, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeBetween(Byte value1, Byte value2) {
            addCriterion("link_type between", value1, value2, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("link_type not between", value1, value2, "linkType");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyIsNull() {
            addCriterion("unique_key is null");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyIsNotNull() {
            addCriterion("unique_key is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyEqualTo(String value) {
            addCriterion("unique_key =", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyNotEqualTo(String value) {
            addCriterion("unique_key <>", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyGreaterThan(String value) {
            addCriterion("unique_key >", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyGreaterThanOrEqualTo(String value) {
            addCriterion("unique_key >=", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyLessThan(String value) {
            addCriterion("unique_key <", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyLessThanOrEqualTo(String value) {
            addCriterion("unique_key <=", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyLike(String value) {
            addCriterion("unique_key like", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyNotLike(String value) {
            addCriterion("unique_key not like", value, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyIn(List<String> values) {
            addCriterion("unique_key in", values, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyNotIn(List<String> values) {
            addCriterion("unique_key not in", values, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyBetween(String value1, String value2) {
            addCriterion("unique_key between", value1, value2, "uniqueKey");
            return (Criteria) this;
        }

        public Criteria andUniqueKeyNotBetween(String value1, String value2) {
            addCriterion("unique_key not between", value1, value2, "uniqueKey");
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