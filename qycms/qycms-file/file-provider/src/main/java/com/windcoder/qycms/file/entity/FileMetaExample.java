package com.windcoder.qycms.file.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileMetaExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FileMetaExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameIsNull() {
            addCriterion("origin_file_name is null");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameIsNotNull() {
            addCriterion("origin_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameEqualTo(String value) {
            addCriterion("origin_file_name =", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameNotEqualTo(String value) {
            addCriterion("origin_file_name <>", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameGreaterThan(String value) {
            addCriterion("origin_file_name >", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("origin_file_name >=", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameLessThan(String value) {
            addCriterion("origin_file_name <", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameLessThanOrEqualTo(String value) {
            addCriterion("origin_file_name <=", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameLike(String value) {
            addCriterion("origin_file_name like", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameNotLike(String value) {
            addCriterion("origin_file_name not like", value, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameIn(List<String> values) {
            addCriterion("origin_file_name in", values, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameNotIn(List<String> values) {
            addCriterion("origin_file_name not in", values, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameBetween(String value1, String value2) {
            addCriterion("origin_file_name between", value1, value2, "originFileName");
            return (Criteria) this;
        }

        public Criteria andOriginFileNameNotBetween(String value1, String value2) {
            addCriterion("origin_file_name not between", value1, value2, "originFileName");
            return (Criteria) this;
        }

        public Criteria andFnameIsNull() {
            addCriterion("fname is null");
            return (Criteria) this;
        }

        public Criteria andFnameIsNotNull() {
            addCriterion("fname is not null");
            return (Criteria) this;
        }

        public Criteria andFnameEqualTo(String value) {
            addCriterion("fname =", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotEqualTo(String value) {
            addCriterion("fname <>", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameGreaterThan(String value) {
            addCriterion("fname >", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameGreaterThanOrEqualTo(String value) {
            addCriterion("fname >=", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLessThan(String value) {
            addCriterion("fname <", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLessThanOrEqualTo(String value) {
            addCriterion("fname <=", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameLike(String value) {
            addCriterion("fname like", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotLike(String value) {
            addCriterion("fname not like", value, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameIn(List<String> values) {
            addCriterion("fname in", values, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotIn(List<String> values) {
            addCriterion("fname not in", values, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameBetween(String value1, String value2) {
            addCriterion("fname between", value1, value2, "fname");
            return (Criteria) this;
        }

        public Criteria andFnameNotBetween(String value1, String value2) {
            addCriterion("fname not between", value1, value2, "fname");
            return (Criteria) this;
        }

        public Criteria andFsizeIsNull() {
            addCriterion("fsize is null");
            return (Criteria) this;
        }

        public Criteria andFsizeIsNotNull() {
            addCriterion("fsize is not null");
            return (Criteria) this;
        }

        public Criteria andFsizeEqualTo(Long value) {
            addCriterion("fsize =", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeNotEqualTo(Long value) {
            addCriterion("fsize <>", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeGreaterThan(Long value) {
            addCriterion("fsize >", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeGreaterThanOrEqualTo(Long value) {
            addCriterion("fsize >=", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeLessThan(Long value) {
            addCriterion("fsize <", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeLessThanOrEqualTo(Long value) {
            addCriterion("fsize <=", value, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeIn(List<Long> values) {
            addCriterion("fsize in", values, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeNotIn(List<Long> values) {
            addCriterion("fsize not in", values, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeBetween(Long value1, Long value2) {
            addCriterion("fsize between", value1, value2, "fsize");
            return (Criteria) this;
        }

        public Criteria andFsizeNotBetween(Long value1, Long value2) {
            addCriterion("fsize not between", value1, value2, "fsize");
            return (Criteria) this;
        }

        public Criteria andExtentionIsNull() {
            addCriterion("extention is null");
            return (Criteria) this;
        }

        public Criteria andExtentionIsNotNull() {
            addCriterion("extention is not null");
            return (Criteria) this;
        }

        public Criteria andExtentionEqualTo(String value) {
            addCriterion("extention =", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionNotEqualTo(String value) {
            addCriterion("extention <>", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionGreaterThan(String value) {
            addCriterion("extention >", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionGreaterThanOrEqualTo(String value) {
            addCriterion("extention >=", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionLessThan(String value) {
            addCriterion("extention <", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionLessThanOrEqualTo(String value) {
            addCriterion("extention <=", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionLike(String value) {
            addCriterion("extention like", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionNotLike(String value) {
            addCriterion("extention not like", value, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionIn(List<String> values) {
            addCriterion("extention in", values, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionNotIn(List<String> values) {
            addCriterion("extention not in", values, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionBetween(String value1, String value2) {
            addCriterion("extention between", value1, value2, "extention");
            return (Criteria) this;
        }

        public Criteria andExtentionNotBetween(String value1, String value2) {
            addCriterion("extention not between", value1, value2, "extention");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIsNull() {
            addCriterion("mime_type is null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIsNotNull() {
            addCriterion("mime_type is not null");
            return (Criteria) this;
        }

        public Criteria andMimeTypeEqualTo(String value) {
            addCriterion("mime_type =", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotEqualTo(String value) {
            addCriterion("mime_type <>", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThan(String value) {
            addCriterion("mime_type >", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mime_type >=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThan(String value) {
            addCriterion("mime_type <", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLessThanOrEqualTo(String value) {
            addCriterion("mime_type <=", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeLike(String value) {
            addCriterion("mime_type like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotLike(String value) {
            addCriterion("mime_type not like", value, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeIn(List<String> values) {
            addCriterion("mime_type in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotIn(List<String> values) {
            addCriterion("mime_type not in", values, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeBetween(String value1, String value2) {
            addCriterion("mime_type between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andMimeTypeNotBetween(String value1, String value2) {
            addCriterion("mime_type not between", value1, value2, "mimeType");
            return (Criteria) this;
        }

        public Criteria andFhashIsNull() {
            addCriterion("fhash is null");
            return (Criteria) this;
        }

        public Criteria andFhashIsNotNull() {
            addCriterion("fhash is not null");
            return (Criteria) this;
        }

        public Criteria andFhashEqualTo(String value) {
            addCriterion("fhash =", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashNotEqualTo(String value) {
            addCriterion("fhash <>", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashGreaterThan(String value) {
            addCriterion("fhash >", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashGreaterThanOrEqualTo(String value) {
            addCriterion("fhash >=", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashLessThan(String value) {
            addCriterion("fhash <", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashLessThanOrEqualTo(String value) {
            addCriterion("fhash <=", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashLike(String value) {
            addCriterion("fhash like", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashNotLike(String value) {
            addCriterion("fhash not like", value, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashIn(List<String> values) {
            addCriterion("fhash in", values, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashNotIn(List<String> values) {
            addCriterion("fhash not in", values, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashBetween(String value1, String value2) {
            addCriterion("fhash between", value1, value2, "fhash");
            return (Criteria) this;
        }

        public Criteria andFhashNotBetween(String value1, String value2) {
            addCriterion("fhash not between", value1, value2, "fhash");
            return (Criteria) this;
        }

        public Criteria andFmd5IsNull() {
            addCriterion("fmd5 is null");
            return (Criteria) this;
        }

        public Criteria andFmd5IsNotNull() {
            addCriterion("fmd5 is not null");
            return (Criteria) this;
        }

        public Criteria andFmd5EqualTo(String value) {
            addCriterion("fmd5 =", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5NotEqualTo(String value) {
            addCriterion("fmd5 <>", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5GreaterThan(String value) {
            addCriterion("fmd5 >", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5GreaterThanOrEqualTo(String value) {
            addCriterion("fmd5 >=", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5LessThan(String value) {
            addCriterion("fmd5 <", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5LessThanOrEqualTo(String value) {
            addCriterion("fmd5 <=", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5Like(String value) {
            addCriterion("fmd5 like", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5NotLike(String value) {
            addCriterion("fmd5 not like", value, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5In(List<String> values) {
            addCriterion("fmd5 in", values, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5NotIn(List<String> values) {
            addCriterion("fmd5 not in", values, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5Between(String value1, String value2) {
            addCriterion("fmd5 between", value1, value2, "fmd5");
            return (Criteria) this;
        }

        public Criteria andFmd5NotBetween(String value1, String value2) {
            addCriterion("fmd5 not between", value1, value2, "fmd5");
            return (Criteria) this;
        }

        public Criteria andRelativePathIsNull() {
            addCriterion("relative_path is null");
            return (Criteria) this;
        }

        public Criteria andRelativePathIsNotNull() {
            addCriterion("relative_path is not null");
            return (Criteria) this;
        }

        public Criteria andRelativePathEqualTo(String value) {
            addCriterion("relative_path =", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathNotEqualTo(String value) {
            addCriterion("relative_path <>", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathGreaterThan(String value) {
            addCriterion("relative_path >", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathGreaterThanOrEqualTo(String value) {
            addCriterion("relative_path >=", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathLessThan(String value) {
            addCriterion("relative_path <", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathLessThanOrEqualTo(String value) {
            addCriterion("relative_path <=", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathLike(String value) {
            addCriterion("relative_path like", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathNotLike(String value) {
            addCriterion("relative_path not like", value, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathIn(List<String> values) {
            addCriterion("relative_path in", values, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathNotIn(List<String> values) {
            addCriterion("relative_path not in", values, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathBetween(String value1, String value2) {
            addCriterion("relative_path between", value1, value2, "relativePath");
            return (Criteria) this;
        }

        public Criteria andRelativePathNotBetween(String value1, String value2) {
            addCriterion("relative_path not between", value1, value2, "relativePath");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByIsNull() {
            addCriterion("last_modified_by is null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByIsNotNull() {
            addCriterion("last_modified_by is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByEqualTo(Long value) {
            addCriterion("last_modified_by =", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByNotEqualTo(Long value) {
            addCriterion("last_modified_by <>", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByGreaterThan(Long value) {
            addCriterion("last_modified_by >", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByGreaterThanOrEqualTo(Long value) {
            addCriterion("last_modified_by >=", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByLessThan(Long value) {
            addCriterion("last_modified_by <", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByLessThanOrEqualTo(Long value) {
            addCriterion("last_modified_by <=", value, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByIn(List<Long> values) {
            addCriterion("last_modified_by in", values, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByNotIn(List<Long> values) {
            addCriterion("last_modified_by not in", values, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByBetween(Long value1, Long value2) {
            addCriterion("last_modified_by between", value1, value2, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andLastModifiedByNotBetween(Long value1, Long value2) {
            addCriterion("last_modified_by not between", value1, value2, "lastModifiedBy");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIsNull() {
            addCriterion("last_modified_date is null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIsNotNull() {
            addCriterion("last_modified_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateEqualTo(Date value) {
            addCriterion("last_modified_date =", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotEqualTo(Date value) {
            addCriterion("last_modified_date <>", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateGreaterThan(Date value) {
            addCriterion("last_modified_date >", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_modified_date >=", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateLessThan(Date value) {
            addCriterion("last_modified_date <", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateLessThanOrEqualTo(Date value) {
            addCriterion("last_modified_date <=", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIn(List<Date> values) {
            addCriterion("last_modified_date in", values, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotIn(List<Date> values) {
            addCriterion("last_modified_date not in", values, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateBetween(Date value1, Date value2) {
            addCriterion("last_modified_date between", value1, value2, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotBetween(Date value1, Date value2) {
            addCriterion("last_modified_date not between", value1, value2, "lastModifiedDate");
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