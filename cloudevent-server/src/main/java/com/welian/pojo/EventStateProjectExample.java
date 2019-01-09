package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventStateProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public EventStateProjectExample() {
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

        public Criteria andCostTypeIsNull() {
            addCriterion("cost_type is null");
            return (Criteria) this;
        }

        public Criteria andCostTypeIsNotNull() {
            addCriterion("cost_type is not null");
            return (Criteria) this;
        }

        public Criteria andCostTypeEqualTo(Byte value) {
            addCriterion("cost_type =", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotEqualTo(Byte value) {
            addCriterion("cost_type <>", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThan(Byte value) {
            addCriterion("cost_type >", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cost_type >=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThan(Byte value) {
            addCriterion("cost_type <", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cost_type <=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeIn(List<Byte> values) {
            addCriterion("cost_type in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotIn(List<Byte> values) {
            addCriterion("cost_type not in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeBetween(Byte value1, Byte value2) {
            addCriterion("cost_type between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cost_type not between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeIsNull() {
            addCriterion("verify_type is null");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeIsNotNull() {
            addCriterion("verify_type is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeEqualTo(Byte value) {
            addCriterion("verify_type =", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotEqualTo(Byte value) {
            addCriterion("verify_type <>", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeGreaterThan(Byte value) {
            addCriterion("verify_type >", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("verify_type >=", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeLessThan(Byte value) {
            addCriterion("verify_type <", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeLessThanOrEqualTo(Byte value) {
            addCriterion("verify_type <=", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeIn(List<Byte> values) {
            addCriterion("verify_type in", values, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotIn(List<Byte> values) {
            addCriterion("verify_type not in", values, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeBetween(Byte value1, Byte value2) {
            addCriterion("verify_type between", value1, value2, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("verify_type not between", value1, value2, "verifyType");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListIsNull() {
            addCriterion("open_sign_up_list is null");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListIsNotNull() {
            addCriterion("open_sign_up_list is not null");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListEqualTo(Byte value) {
            addCriterion("open_sign_up_list =", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListNotEqualTo(Byte value) {
            addCriterion("open_sign_up_list <>", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListGreaterThan(Byte value) {
            addCriterion("open_sign_up_list >", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListGreaterThanOrEqualTo(Byte value) {
            addCriterion("open_sign_up_list >=", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListLessThan(Byte value) {
            addCriterion("open_sign_up_list <", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListLessThanOrEqualTo(Byte value) {
            addCriterion("open_sign_up_list <=", value, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListIn(List<Byte> values) {
            addCriterion("open_sign_up_list in", values, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListNotIn(List<Byte> values) {
            addCriterion("open_sign_up_list not in", values, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListBetween(Byte value1, Byte value2) {
            addCriterion("open_sign_up_list between", value1, value2, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andOpenSignUpListNotBetween(Byte value1, Byte value2) {
            addCriterion("open_sign_up_list not between", value1, value2, "openSignUpList");
            return (Criteria) this;
        }

        public Criteria andJoinedCountIsNull() {
            addCriterion("joined_count is null");
            return (Criteria) this;
        }

        public Criteria andJoinedCountIsNotNull() {
            addCriterion("joined_count is not null");
            return (Criteria) this;
        }

        public Criteria andJoinedCountEqualTo(Integer value) {
            addCriterion("joined_count =", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountNotEqualTo(Integer value) {
            addCriterion("joined_count <>", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountGreaterThan(Integer value) {
            addCriterion("joined_count >", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("joined_count >=", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountLessThan(Integer value) {
            addCriterion("joined_count <", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountLessThanOrEqualTo(Integer value) {
            addCriterion("joined_count <=", value, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountIn(List<Integer> values) {
            addCriterion("joined_count in", values, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountNotIn(List<Integer> values) {
            addCriterion("joined_count not in", values, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountBetween(Integer value1, Integer value2) {
            addCriterion("joined_count between", value1, value2, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andJoinedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("joined_count not between", value1, value2, "joinedCount");
            return (Criteria) this;
        }

        public Criteria andGroupChatIsNull() {
            addCriterion("group_chat is null");
            return (Criteria) this;
        }

        public Criteria andGroupChatIsNotNull() {
            addCriterion("group_chat is not null");
            return (Criteria) this;
        }

        public Criteria andGroupChatEqualTo(String value) {
            addCriterion("group_chat =", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatNotEqualTo(String value) {
            addCriterion("group_chat <>", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatGreaterThan(String value) {
            addCriterion("group_chat >", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatGreaterThanOrEqualTo(String value) {
            addCriterion("group_chat >=", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatLessThan(String value) {
            addCriterion("group_chat <", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatLessThanOrEqualTo(String value) {
            addCriterion("group_chat <=", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatLike(String value) {
            addCriterion("group_chat like", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatNotLike(String value) {
            addCriterion("group_chat not like", value, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatIn(List<String> values) {
            addCriterion("group_chat in", values, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatNotIn(List<String> values) {
            addCriterion("group_chat not in", values, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatBetween(String value1, String value2) {
            addCriterion("group_chat between", value1, value2, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatNotBetween(String value1, String value2) {
            addCriterion("group_chat not between", value1, value2, "groupChat");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateIsNull() {
            addCriterion("group_chat_state is null");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateIsNotNull() {
            addCriterion("group_chat_state is not null");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateEqualTo(Byte value) {
            addCriterion("group_chat_state =", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateNotEqualTo(Byte value) {
            addCriterion("group_chat_state <>", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateGreaterThan(Byte value) {
            addCriterion("group_chat_state >", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("group_chat_state >=", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateLessThan(Byte value) {
            addCriterion("group_chat_state <", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateLessThanOrEqualTo(Byte value) {
            addCriterion("group_chat_state <=", value, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateIn(List<Byte> values) {
            addCriterion("group_chat_state in", values, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateNotIn(List<Byte> values) {
            addCriterion("group_chat_state not in", values, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateBetween(Byte value1, Byte value2) {
            addCriterion("group_chat_state between", value1, value2, "groupChatState");
            return (Criteria) this;
        }

        public Criteria andGroupChatStateNotBetween(Byte value1, Byte value2) {
            addCriterion("group_chat_state not between", value1, value2, "groupChatState");
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

        public Criteria andIsOpenFinancingIsNull() {
            addCriterion("is_open_financing is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingIsNotNull() {
            addCriterion("is_open_financing is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingEqualTo(Byte value) {
            addCriterion("is_open_financing =", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingNotEqualTo(Byte value) {
            addCriterion("is_open_financing <>", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingGreaterThan(Byte value) {
            addCriterion("is_open_financing >", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_open_financing >=", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingLessThan(Byte value) {
            addCriterion("is_open_financing <", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingLessThanOrEqualTo(Byte value) {
            addCriterion("is_open_financing <=", value, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingIn(List<Byte> values) {
            addCriterion("is_open_financing in", values, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingNotIn(List<Byte> values) {
            addCriterion("is_open_financing not in", values, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingBetween(Byte value1, Byte value2) {
            addCriterion("is_open_financing between", value1, value2, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andIsOpenFinancingNotBetween(Byte value1, Byte value2) {
            addCriterion("is_open_financing not between", value1, value2, "isOpenFinancing");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountIsNull() {
            addCriterion("investor_match_count is null");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountIsNotNull() {
            addCriterion("investor_match_count is not null");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountEqualTo(Integer value) {
            addCriterion("investor_match_count =", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountNotEqualTo(Integer value) {
            addCriterion("investor_match_count <>", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountGreaterThan(Integer value) {
            addCriterion("investor_match_count >", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("investor_match_count >=", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountLessThan(Integer value) {
            addCriterion("investor_match_count <", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountLessThanOrEqualTo(Integer value) {
            addCriterion("investor_match_count <=", value, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountIn(List<Integer> values) {
            addCriterion("investor_match_count in", values, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountNotIn(List<Integer> values) {
            addCriterion("investor_match_count not in", values, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountBetween(Integer value1, Integer value2) {
            addCriterion("investor_match_count between", value1, value2, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andInvestorMatchCountNotBetween(Integer value1, Integer value2) {
            addCriterion("investor_match_count not between", value1, value2, "investorMatchCount");
            return (Criteria) this;
        }

        public Criteria andGroupSettingIsNull() {
            addCriterion("group_setting is null");
            return (Criteria) this;
        }

        public Criteria andGroupSettingIsNotNull() {
            addCriterion("group_setting is not null");
            return (Criteria) this;
        }

        public Criteria andGroupSettingEqualTo(Byte value) {
            addCriterion("group_setting =", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingNotEqualTo(Byte value) {
            addCriterion("group_setting <>", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingGreaterThan(Byte value) {
            addCriterion("group_setting >", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingGreaterThanOrEqualTo(Byte value) {
            addCriterion("group_setting >=", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingLessThan(Byte value) {
            addCriterion("group_setting <", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingLessThanOrEqualTo(Byte value) {
            addCriterion("group_setting <=", value, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingIn(List<Byte> values) {
            addCriterion("group_setting in", values, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingNotIn(List<Byte> values) {
            addCriterion("group_setting not in", values, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingBetween(Byte value1, Byte value2) {
            addCriterion("group_setting between", value1, value2, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andGroupSettingNotBetween(Byte value1, Byte value2) {
            addCriterion("group_setting not between", value1, value2, "groupSetting");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeIsNull() {
            addCriterion("project_input_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeIsNotNull() {
            addCriterion("project_input_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeEqualTo(Byte value) {
            addCriterion("project_input_type =", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeNotEqualTo(Byte value) {
            addCriterion("project_input_type <>", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeGreaterThan(Byte value) {
            addCriterion("project_input_type >", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("project_input_type >=", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeLessThan(Byte value) {
            addCriterion("project_input_type <", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeLessThanOrEqualTo(Byte value) {
            addCriterion("project_input_type <=", value, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeIn(List<Byte> values) {
            addCriterion("project_input_type in", values, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeNotIn(List<Byte> values) {
            addCriterion("project_input_type not in", values, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeBetween(Byte value1, Byte value2) {
            addCriterion("project_input_type between", value1, value2, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectInputTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("project_input_type not between", value1, value2, "projectInputType");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateIsNull() {
            addCriterion("project_number_state is null");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateIsNotNull() {
            addCriterion("project_number_state is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateEqualTo(Byte value) {
            addCriterion("project_number_state =", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateNotEqualTo(Byte value) {
            addCriterion("project_number_state <>", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateGreaterThan(Byte value) {
            addCriterion("project_number_state >", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateGreaterThanOrEqualTo(Byte value) {
            addCriterion("project_number_state >=", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateLessThan(Byte value) {
            addCriterion("project_number_state <", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateLessThanOrEqualTo(Byte value) {
            addCriterion("project_number_state <=", value, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateIn(List<Byte> values) {
            addCriterion("project_number_state in", values, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateNotIn(List<Byte> values) {
            addCriterion("project_number_state not in", values, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateBetween(Byte value1, Byte value2) {
            addCriterion("project_number_state between", value1, value2, "projectNumberState");
            return (Criteria) this;
        }

        public Criteria andProjectNumberStateNotBetween(Byte value1, Byte value2) {
            addCriterion("project_number_state not between", value1, value2, "projectNumberState");
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