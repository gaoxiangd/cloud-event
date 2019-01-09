package com.welian.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProjectBackupInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public ProjectBackupInfoExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIntroIsNull() {
            addCriterion("intro is null");
            return (Criteria) this;
        }

        public Criteria andIntroIsNotNull() {
            addCriterion("intro is not null");
            return (Criteria) this;
        }

        public Criteria andIntroEqualTo(String value) {
            addCriterion("intro =", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotEqualTo(String value) {
            addCriterion("intro <>", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThan(String value) {
            addCriterion("intro >", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThanOrEqualTo(String value) {
            addCriterion("intro >=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThan(String value) {
            addCriterion("intro <", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThanOrEqualTo(String value) {
            addCriterion("intro <=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLike(String value) {
            addCriterion("intro like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotLike(String value) {
            addCriterion("intro not like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroIn(List<String> values) {
            addCriterion("intro in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotIn(List<String> values) {
            addCriterion("intro not in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroBetween(String value1, String value2) {
            addCriterion("intro between", value1, value2, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotBetween(String value1, String value2) {
            addCriterion("intro not between", value1, value2, "intro");
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

        public Criteria andIndustryIdIsNull() {
            addCriterion("industry_id is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIsNotNull() {
            addCriterion("industry_id is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdEqualTo(Integer value) {
            addCriterion("industry_id =", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotEqualTo(Integer value) {
            addCriterion("industry_id <>", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThan(Integer value) {
            addCriterion("industry_id >", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("industry_id >=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThan(Integer value) {
            addCriterion("industry_id <", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThanOrEqualTo(Integer value) {
            addCriterion("industry_id <=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIn(List<Integer> values) {
            addCriterion("industry_id in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotIn(List<Integer> values) {
            addCriterion("industry_id not in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdBetween(Integer value1, Integer value2) {
            addCriterion("industry_id between", value1, value2, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("industry_id not between", value1, value2, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNull() {
            addCriterion("industry_name is null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNotNull() {
            addCriterion("industry_name is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameEqualTo(String value) {
            addCriterion("industry_name =", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotEqualTo(String value) {
            addCriterion("industry_name <>", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThan(String value) {
            addCriterion("industry_name >", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThanOrEqualTo(String value) {
            addCriterion("industry_name >=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThan(String value) {
            addCriterion("industry_name <", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThanOrEqualTo(String value) {
            addCriterion("industry_name <=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLike(String value) {
            addCriterion("industry_name like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotLike(String value) {
            addCriterion("industry_name not like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIn(List<String> values) {
            addCriterion("industry_name in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotIn(List<String> values) {
            addCriterion("industry_name not in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameBetween(String value1, String value2) {
            addCriterion("industry_name between", value1, value2, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotBetween(String value1, String value2) {
            addCriterion("industry_name not between", value1, value2, "industryName");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidIsNull() {
            addCriterion("project_create_uid is null");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidIsNotNull() {
            addCriterion("project_create_uid is not null");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidEqualTo(Integer value) {
            addCriterion("project_create_uid =", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidNotEqualTo(Integer value) {
            addCriterion("project_create_uid <>", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidGreaterThan(Integer value) {
            addCriterion("project_create_uid >", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_create_uid >=", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidLessThan(Integer value) {
            addCriterion("project_create_uid <", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidLessThanOrEqualTo(Integer value) {
            addCriterion("project_create_uid <=", value, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidIn(List<Integer> values) {
            addCriterion("project_create_uid in", values, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidNotIn(List<Integer> values) {
            addCriterion("project_create_uid not in", values, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidBetween(Integer value1, Integer value2) {
            addCriterion("project_create_uid between", value1, value2, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectCreateUidNotBetween(Integer value1, Integer value2) {
            addCriterion("project_create_uid not between", value1, value2, "projectCreateUid");
            return (Criteria) this;
        }

        public Criteria andProjectShareIsNull() {
            addCriterion("project_share is null");
            return (Criteria) this;
        }

        public Criteria andProjectShareIsNotNull() {
            addCriterion("project_share is not null");
            return (Criteria) this;
        }

        public Criteria andProjectShareEqualTo(Double value) {
            addCriterion("project_share =", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareNotEqualTo(Double value) {
            addCriterion("project_share <>", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareGreaterThan(Double value) {
            addCriterion("project_share >", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareGreaterThanOrEqualTo(Double value) {
            addCriterion("project_share >=", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareLessThan(Double value) {
            addCriterion("project_share <", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareLessThanOrEqualTo(Double value) {
            addCriterion("project_share <=", value, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareIn(List<Double> values) {
            addCriterion("project_share in", values, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareNotIn(List<Double> values) {
            addCriterion("project_share not in", values, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareBetween(Double value1, Double value2) {
            addCriterion("project_share between", value1, value2, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectShareNotBetween(Double value1, Double value2) {
            addCriterion("project_share not between", value1, value2, "projectShare");
            return (Criteria) this;
        }

        public Criteria andProjectAmountIsNull() {
            addCriterion("project_amount is null");
            return (Criteria) this;
        }

        public Criteria andProjectAmountIsNotNull() {
            addCriterion("project_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProjectAmountEqualTo(Integer value) {
            addCriterion("project_amount =", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountNotEqualTo(Integer value) {
            addCriterion("project_amount <>", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountGreaterThan(Integer value) {
            addCriterion("project_amount >", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_amount >=", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountLessThan(Integer value) {
            addCriterion("project_amount <", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountLessThanOrEqualTo(Integer value) {
            addCriterion("project_amount <=", value, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountIn(List<Integer> values) {
            addCriterion("project_amount in", values, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountNotIn(List<Integer> values) {
            addCriterion("project_amount not in", values, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountBetween(Integer value1, Integer value2) {
            addCriterion("project_amount between", value1, value2, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("project_amount not between", value1, value2, "projectAmount");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitIsNull() {
            addCriterion("project_amount_unit is null");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitIsNotNull() {
            addCriterion("project_amount_unit is not null");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitEqualTo(Byte value) {
            addCriterion("project_amount_unit =", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitNotEqualTo(Byte value) {
            addCriterion("project_amount_unit <>", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitGreaterThan(Byte value) {
            addCriterion("project_amount_unit >", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitGreaterThanOrEqualTo(Byte value) {
            addCriterion("project_amount_unit >=", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitLessThan(Byte value) {
            addCriterion("project_amount_unit <", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitLessThanOrEqualTo(Byte value) {
            addCriterion("project_amount_unit <=", value, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitIn(List<Byte> values) {
            addCriterion("project_amount_unit in", values, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitNotIn(List<Byte> values) {
            addCriterion("project_amount_unit not in", values, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitBetween(Byte value1, Byte value2) {
            addCriterion("project_amount_unit between", value1, value2, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectAmountUnitNotBetween(Byte value1, Byte value2) {
            addCriterion("project_amount_unit not between", value1, value2, "projectAmountUnit");
            return (Criteria) this;
        }

        public Criteria andProjectStageIsNull() {
            addCriterion("project_stage is null");
            return (Criteria) this;
        }

        public Criteria andProjectStageIsNotNull() {
            addCriterion("project_stage is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStageEqualTo(Integer value) {
            addCriterion("project_stage =", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotEqualTo(Integer value) {
            addCriterion("project_stage <>", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageGreaterThan(Integer value) {
            addCriterion("project_stage >", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_stage >=", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageLessThan(Integer value) {
            addCriterion("project_stage <", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageLessThanOrEqualTo(Integer value) {
            addCriterion("project_stage <=", value, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageIn(List<Integer> values) {
            addCriterion("project_stage in", values, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotIn(List<Integer> values) {
            addCriterion("project_stage not in", values, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageBetween(Integer value1, Integer value2) {
            addCriterion("project_stage between", value1, value2, "projectStage");
            return (Criteria) this;
        }

        public Criteria andProjectStageNotBetween(Integer value1, Integer value2) {
            addCriterion("project_stage not between", value1, value2, "projectStage");
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

        public Criteria andProjectSignBpIdIsNull() {
            addCriterion("project_sign_bp_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdIsNotNull() {
            addCriterion("project_sign_bp_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdEqualTo(Integer value) {
            addCriterion("project_sign_bp_id =", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdNotEqualTo(Integer value) {
            addCriterion("project_sign_bp_id <>", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdGreaterThan(Integer value) {
            addCriterion("project_sign_bp_id >", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_sign_bp_id >=", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdLessThan(Integer value) {
            addCriterion("project_sign_bp_id <", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_sign_bp_id <=", value, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdIn(List<Integer> values) {
            addCriterion("project_sign_bp_id in", values, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdNotIn(List<Integer> values) {
            addCriterion("project_sign_bp_id not in", values, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdBetween(Integer value1, Integer value2) {
            addCriterion("project_sign_bp_id between", value1, value2, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_sign_bp_id not between", value1, value2, "projectSignBpId");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlIsNull() {
            addCriterion("project_sign_bp_url is null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlIsNotNull() {
            addCriterion("project_sign_bp_url is not null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlEqualTo(String value) {
            addCriterion("project_sign_bp_url =", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlNotEqualTo(String value) {
            addCriterion("project_sign_bp_url <>", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlGreaterThan(String value) {
            addCriterion("project_sign_bp_url >", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlGreaterThanOrEqualTo(String value) {
            addCriterion("project_sign_bp_url >=", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlLessThan(String value) {
            addCriterion("project_sign_bp_url <", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlLessThanOrEqualTo(String value) {
            addCriterion("project_sign_bp_url <=", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlLike(String value) {
            addCriterion("project_sign_bp_url like", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlNotLike(String value) {
            addCriterion("project_sign_bp_url not like", value, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlIn(List<String> values) {
            addCriterion("project_sign_bp_url in", values, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlNotIn(List<String> values) {
            addCriterion("project_sign_bp_url not in", values, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlBetween(String value1, String value2) {
            addCriterion("project_sign_bp_url between", value1, value2, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpUrlNotBetween(String value1, String value2) {
            addCriterion("project_sign_bp_url not between", value1, value2, "projectSignBpUrl");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameIsNull() {
            addCriterion("project_sign_bp_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameIsNotNull() {
            addCriterion("project_sign_bp_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameEqualTo(String value) {
            addCriterion("project_sign_bp_name =", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameNotEqualTo(String value) {
            addCriterion("project_sign_bp_name <>", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameGreaterThan(String value) {
            addCriterion("project_sign_bp_name >", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_sign_bp_name >=", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameLessThan(String value) {
            addCriterion("project_sign_bp_name <", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameLessThanOrEqualTo(String value) {
            addCriterion("project_sign_bp_name <=", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameLike(String value) {
            addCriterion("project_sign_bp_name like", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameNotLike(String value) {
            addCriterion("project_sign_bp_name not like", value, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameIn(List<String> values) {
            addCriterion("project_sign_bp_name in", values, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameNotIn(List<String> values) {
            addCriterion("project_sign_bp_name not in", values, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameBetween(String value1, String value2) {
            addCriterion("project_sign_bp_name between", value1, value2, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectSignBpNameNotBetween(String value1, String value2) {
            addCriterion("project_sign_bp_name not between", value1, value2, "projectSignBpName");
            return (Criteria) this;
        }

        public Criteria andProjectVersionIsNull() {
            addCriterion("project_version is null");
            return (Criteria) this;
        }

        public Criteria andProjectVersionIsNotNull() {
            addCriterion("project_version is not null");
            return (Criteria) this;
        }

        public Criteria andProjectVersionEqualTo(String value) {
            addCriterion("project_version =", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionNotEqualTo(String value) {
            addCriterion("project_version <>", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionGreaterThan(String value) {
            addCriterion("project_version >", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionGreaterThanOrEqualTo(String value) {
            addCriterion("project_version >=", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionLessThan(String value) {
            addCriterion("project_version <", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionLessThanOrEqualTo(String value) {
            addCriterion("project_version <=", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionLike(String value) {
            addCriterion("project_version like", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionNotLike(String value) {
            addCriterion("project_version not like", value, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionIn(List<String> values) {
            addCriterion("project_version in", values, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionNotIn(List<String> values) {
            addCriterion("project_version not in", values, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionBetween(String value1, String value2) {
            addCriterion("project_version between", value1, value2, "projectVersion");
            return (Criteria) this;
        }

        public Criteria andProjectVersionNotBetween(String value1, String value2) {
            addCriterion("project_version not between", value1, value2, "projectVersion");
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