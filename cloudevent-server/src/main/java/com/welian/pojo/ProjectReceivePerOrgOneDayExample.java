package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProjectReceivePerOrgOneDayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public ProjectReceivePerOrgOneDayExample() {
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

        public Criteria andProjectReceiveCountIsNull() {
            addCriterion("project_receive_count is null");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountIsNotNull() {
            addCriterion("project_receive_count is not null");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountEqualTo(Integer value) {
            addCriterion("project_receive_count =", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountNotEqualTo(Integer value) {
            addCriterion("project_receive_count <>", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountGreaterThan(Integer value) {
            addCriterion("project_receive_count >", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_receive_count >=", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountLessThan(Integer value) {
            addCriterion("project_receive_count <", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountLessThanOrEqualTo(Integer value) {
            addCriterion("project_receive_count <=", value, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountIn(List<Integer> values) {
            addCriterion("project_receive_count in", values, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountNotIn(List<Integer> values) {
            addCriterion("project_receive_count not in", values, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountBetween(Integer value1, Integer value2) {
            addCriterion("project_receive_count between", value1, value2, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andProjectReceiveCountNotBetween(Integer value1, Integer value2) {
            addCriterion("project_receive_count not between", value1, value2, "projectReceiveCount");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Long value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Long value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Long value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Long value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Long value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Long value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Long> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Long> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Long value1, Long value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Long value1, Long value2) {
            addCriterion("date not between", value1, value2, "date");
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

        public Criteria andTicketReceiveCountIsNull() {
            addCriterion("ticket_receive_count is null");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountIsNotNull() {
            addCriterion("ticket_receive_count is not null");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountEqualTo(Integer value) {
            addCriterion("ticket_receive_count =", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountNotEqualTo(Integer value) {
            addCriterion("ticket_receive_count <>", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountGreaterThan(Integer value) {
            addCriterion("ticket_receive_count >", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ticket_receive_count >=", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountLessThan(Integer value) {
            addCriterion("ticket_receive_count <", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountLessThanOrEqualTo(Integer value) {
            addCriterion("ticket_receive_count <=", value, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountIn(List<Integer> values) {
            addCriterion("ticket_receive_count in", values, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountNotIn(List<Integer> values) {
            addCriterion("ticket_receive_count not in", values, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountBetween(Integer value1, Integer value2) {
            addCriterion("ticket_receive_count between", value1, value2, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andTicketReceiveCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ticket_receive_count not between", value1, value2, "ticketReceiveCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountIsNull() {
            addCriterion("sign_in_count is null");
            return (Criteria) this;
        }

        public Criteria andSignInCountIsNotNull() {
            addCriterion("sign_in_count is not null");
            return (Criteria) this;
        }

        public Criteria andSignInCountEqualTo(Integer value) {
            addCriterion("sign_in_count =", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountNotEqualTo(Integer value) {
            addCriterion("sign_in_count <>", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountGreaterThan(Integer value) {
            addCriterion("sign_in_count >", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_in_count >=", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountLessThan(Integer value) {
            addCriterion("sign_in_count <", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountLessThanOrEqualTo(Integer value) {
            addCriterion("sign_in_count <=", value, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountIn(List<Integer> values) {
            addCriterion("sign_in_count in", values, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountNotIn(List<Integer> values) {
            addCriterion("sign_in_count not in", values, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountBetween(Integer value1, Integer value2) {
            addCriterion("sign_in_count between", value1, value2, "signInCount");
            return (Criteria) this;
        }

        public Criteria andSignInCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_in_count not between", value1, value2, "signInCount");
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