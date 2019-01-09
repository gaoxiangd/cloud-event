package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    protected String groupByClause;

    public String getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
    }

    public EventRecordExample() {
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

        public Criteria andExtensionLinkIdIsNull() {
            addCriterion("extension_link_id is null");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdIsNotNull() {
            addCriterion("extension_link_id is not null");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdEqualTo(Integer value) {
            addCriterion("extension_link_id =", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdNotEqualTo(Integer value) {
            addCriterion("extension_link_id <>", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdGreaterThan(Integer value) {
            addCriterion("extension_link_id >", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("extension_link_id >=", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdLessThan(Integer value) {
            addCriterion("extension_link_id <", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdLessThanOrEqualTo(Integer value) {
            addCriterion("extension_link_id <=", value, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdIn(List<Integer> values) {
            addCriterion("extension_link_id in", values, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdNotIn(List<Integer> values) {
            addCriterion("extension_link_id not in", values, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdBetween(Integer value1, Integer value2) {
            addCriterion("extension_link_id between", value1, value2, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andExtensionLinkIdNotBetween(Integer value1, Integer value2) {
            addCriterion("extension_link_id not between", value1, value2, "extensionLinkId");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeIsNull() {
            addCriterion("sign_up_type is null");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeIsNotNull() {
            addCriterion("sign_up_type is not null");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeEqualTo(Byte value) {
            addCriterion("sign_up_type =", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeNotEqualTo(Byte value) {
            addCriterion("sign_up_type <>", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeGreaterThan(Byte value) {
            addCriterion("sign_up_type >", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("sign_up_type >=", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeLessThan(Byte value) {
            addCriterion("sign_up_type <", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeLessThanOrEqualTo(Byte value) {
            addCriterion("sign_up_type <=", value, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeIn(List<Byte> values) {
            addCriterion("sign_up_type in", values, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeNotIn(List<Byte> values) {
            addCriterion("sign_up_type not in", values, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeBetween(Byte value1, Byte value2) {
            addCriterion("sign_up_type between", value1, value2, "signUpType");
            return (Criteria) this;
        }

        public Criteria andSignUpTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("sign_up_type not between", value1, value2, "signUpType");
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

        public Criteria andTicketLockIsNull() {
            addCriterion("ticket_lock is null");
            return (Criteria) this;
        }

        public Criteria andTicketLockIsNotNull() {
            addCriterion("ticket_lock is not null");
            return (Criteria) this;
        }

        public Criteria andTicketLockEqualTo(Integer value) {
            addCriterion("ticket_lock =", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockNotEqualTo(Integer value) {
            addCriterion("ticket_lock <>", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockGreaterThan(Integer value) {
            addCriterion("ticket_lock >", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockGreaterThanOrEqualTo(Integer value) {
            addCriterion("ticket_lock >=", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockLessThan(Integer value) {
            addCriterion("ticket_lock <", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockLessThanOrEqualTo(Integer value) {
            addCriterion("ticket_lock <=", value, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockIn(List<Integer> values) {
            addCriterion("ticket_lock in", values, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockNotIn(List<Integer> values) {
            addCriterion("ticket_lock not in", values, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockBetween(Integer value1, Integer value2) {
            addCriterion("ticket_lock between", value1, value2, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andTicketLockNotBetween(Integer value1, Integer value2) {
            addCriterion("ticket_lock not between", value1, value2, "ticketLock");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Byte value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Byte value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Byte value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Byte value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Byte value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Byte> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Byte> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Byte value1, Byte value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("source not between", value1, value2, "source");
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

        public Criteria andOrderNumberIsNull() {
            addCriterion("order_number is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNotNull() {
            addCriterion("order_number is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberEqualTo(String value) {
            addCriterion("order_number =", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotEqualTo(String value) {
            addCriterion("order_number <>", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThan(String value) {
            addCriterion("order_number >", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThanOrEqualTo(String value) {
            addCriterion("order_number >=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThan(String value) {
            addCriterion("order_number <", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThanOrEqualTo(String value) {
            addCriterion("order_number <=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLike(String value) {
            addCriterion("order_number like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotLike(String value) {
            addCriterion("order_number not like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIn(List<String> values) {
            addCriterion("order_number in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotIn(List<String> values) {
            addCriterion("order_number not in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberBetween(String value1, String value2) {
            addCriterion("order_number between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotBetween(String value1, String value2) {
            addCriterion("order_number not between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andTicketNumberIsNull() {
            addCriterion("ticket_number is null");
            return (Criteria) this;
        }

        public Criteria andTicketNumberIsNotNull() {
            addCriterion("ticket_number is not null");
            return (Criteria) this;
        }

        public Criteria andTicketNumberEqualTo(String value) {
            addCriterion("ticket_number =", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberNotEqualTo(String value) {
            addCriterion("ticket_number <>", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberGreaterThan(String value) {
            addCriterion("ticket_number >", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberGreaterThanOrEqualTo(String value) {
            addCriterion("ticket_number >=", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberLessThan(String value) {
            addCriterion("ticket_number <", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberLessThanOrEqualTo(String value) {
            addCriterion("ticket_number <=", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberLike(String value) {
            addCriterion("ticket_number like", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberNotLike(String value) {
            addCriterion("ticket_number not like", value, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberIn(List<String> values) {
            addCriterion("ticket_number in", values, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberNotIn(List<String> values) {
            addCriterion("ticket_number not in", values, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberBetween(String value1, String value2) {
            addCriterion("ticket_number between", value1, value2, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTicketNumberNotBetween(String value1, String value2) {
            addCriterion("ticket_number not between", value1, value2, "ticketNumber");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNull() {
            addCriterion("trade_no is null");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNotNull() {
            addCriterion("trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNoEqualTo(String value) {
            addCriterion("trade_no =", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotEqualTo(String value) {
            addCriterion("trade_no <>", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThan(String value) {
            addCriterion("trade_no >", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("trade_no >=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThan(String value) {
            addCriterion("trade_no <", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThanOrEqualTo(String value) {
            addCriterion("trade_no <=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLike(String value) {
            addCriterion("trade_no like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotLike(String value) {
            addCriterion("trade_no not like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoIn(List<String> values) {
            addCriterion("trade_no in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotIn(List<String> values) {
            addCriterion("trade_no not in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoBetween(String value1, String value2) {
            addCriterion("trade_no between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotBetween(String value1, String value2) {
            addCriterion("trade_no not between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIsNull() {
            addCriterion("trade_end_time is null");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIsNotNull() {
            addCriterion("trade_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeEqualTo(Long value) {
            addCriterion("trade_end_time =", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotEqualTo(Long value) {
            addCriterion("trade_end_time <>", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeGreaterThan(Long value) {
            addCriterion("trade_end_time >", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("trade_end_time >=", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeLessThan(Long value) {
            addCriterion("trade_end_time <", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeLessThanOrEqualTo(Long value) {
            addCriterion("trade_end_time <=", value, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeIn(List<Long> values) {
            addCriterion("trade_end_time in", values, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotIn(List<Long> values) {
            addCriterion("trade_end_time not in", values, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeBetween(Long value1, Long value2) {
            addCriterion("trade_end_time between", value1, value2, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTradeEndTimeNotBetween(Long value1, Long value2) {
            addCriterion("trade_end_time not between", value1, value2, "tradeEndTime");
            return (Criteria) this;
        }

        public Criteria andTicketUrlIsNull() {
            addCriterion("ticket_url is null");
            return (Criteria) this;
        }

        public Criteria andTicketUrlIsNotNull() {
            addCriterion("ticket_url is not null");
            return (Criteria) this;
        }

        public Criteria andTicketUrlEqualTo(String value) {
            addCriterion("ticket_url =", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlNotEqualTo(String value) {
            addCriterion("ticket_url <>", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlGreaterThan(String value) {
            addCriterion("ticket_url >", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlGreaterThanOrEqualTo(String value) {
            addCriterion("ticket_url >=", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlLessThan(String value) {
            addCriterion("ticket_url <", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlLessThanOrEqualTo(String value) {
            addCriterion("ticket_url <=", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlLike(String value) {
            addCriterion("ticket_url like", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlNotLike(String value) {
            addCriterion("ticket_url not like", value, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlIn(List<String> values) {
            addCriterion("ticket_url in", values, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlNotIn(List<String> values) {
            addCriterion("ticket_url not in", values, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlBetween(String value1, String value2) {
            addCriterion("ticket_url between", value1, value2, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andTicketUrlNotBetween(String value1, String value2) {
            addCriterion("ticket_url not between", value1, value2, "ticketUrl");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdIsNull() {
            addCriterion("old_record_id is null");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdIsNotNull() {
            addCriterion("old_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdEqualTo(Integer value) {
            addCriterion("old_record_id =", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdNotEqualTo(Integer value) {
            addCriterion("old_record_id <>", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdGreaterThan(Integer value) {
            addCriterion("old_record_id >", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_record_id >=", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdLessThan(Integer value) {
            addCriterion("old_record_id <", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_record_id <=", value, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdIn(List<Integer> values) {
            addCriterion("old_record_id in", values, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdNotIn(List<Integer> values) {
            addCriterion("old_record_id not in", values, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("old_record_id between", value1, value2, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andOldRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_record_id not between", value1, value2, "oldRecordId");
            return (Criteria) this;
        }

        public Criteria andIsImportIsNull() {
            addCriterion("is_import is null");
            return (Criteria) this;
        }

        public Criteria andIsImportIsNotNull() {
            addCriterion("is_import is not null");
            return (Criteria) this;
        }

        public Criteria andIsImportEqualTo(Integer value) {
            addCriterion("is_import =", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportNotEqualTo(Integer value) {
            addCriterion("is_import <>", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportGreaterThan(Integer value) {
            addCriterion("is_import >", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_import >=", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportLessThan(Integer value) {
            addCriterion("is_import <", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportLessThanOrEqualTo(Integer value) {
            addCriterion("is_import <=", value, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportIn(List<Integer> values) {
            addCriterion("is_import in", values, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportNotIn(List<Integer> values) {
            addCriterion("is_import not in", values, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportBetween(Integer value1, Integer value2) {
            addCriterion("is_import between", value1, value2, "isImport");
            return (Criteria) this;
        }

        public Criteria andIsImportNotBetween(Integer value1, Integer value2) {
            addCriterion("is_import not between", value1, value2, "isImport");
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