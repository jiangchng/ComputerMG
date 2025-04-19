package com.company.computerparts.model;

import java.math.BigDecimal;

public class Part {
    private Integer id;
    private String name;
    private String specification;
    private String type;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private Integer StockQuantity;

    public Part() {
    }

    public Part(Integer id, String name, String specification, String type, BigDecimal purchasePrice, BigDecimal salePrice, Integer StockQuantity) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.type = type;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.StockQuantity = StockQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String toString() {
        return "Part{id = " + id + ", name = " + name + ", specification = " + specification + ", type = " + type + ", purchasePrice = " + purchasePrice + ", salePrice = " + salePrice + "}";
    }

    public Integer getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(Integer StockQuantity) {
        this.StockQuantity = StockQuantity;
    }

    // 构造方法、getter和setter...
}