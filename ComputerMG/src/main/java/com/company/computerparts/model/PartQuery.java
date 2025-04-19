package com.company.computerparts.model;

public class PartQuery {
    private String searchType; // 编号/名称/类型
    private String searchValue;

    // 构造方法、getter和setter
    public PartQuery(String searchType, String searchValue) {
        this.searchType = searchType;
        this.searchValue = searchValue;
    }

    public PartQuery() {
    }

    // 验证查询条件是否有效
    public boolean isValid() {
        return searchValue != null && !searchValue.trim().isEmpty();
    }


    /**
     * 获取
     * @return searchType
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * 设置
     * @param searchType
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * 获取
     * @return searchValue
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * 设置
     * @param searchValue
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String toString() {
        return "PartQuery{searchType = " + searchType + ", searchValue = " + searchValue + "}";
    }
}