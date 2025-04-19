package com.company.computerparts.model;

import java.awt.*;

public class InventoryRecord {
    private Integer id;
    private String type;
    private Integer part_id;
    private String part_name;
    private String data;
    private Integer quantity;
    private String remarks;


    public InventoryRecord() {
    }

    public InventoryRecord(Integer id, String type, Integer part_id, String part_name, String data, Integer quantity, String remarks) {
        this.id = id;
        this.type = type;
        this.part_id = part_id;
        this.part_name = part_name;
        this.data = data;
        this.quantity = quantity;
        this.remarks = remarks;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 设置
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取
     * @return part_id
     */
    public Integer getPart_id() {
        return part_id;
    }

    /**
     * 设置
     * @param part_id
     */
    public void setPart_id(Integer part_id) {
        this.part_id = part_id;
    }

    /**
     * 获取
     * @return part_name
     */
    public String getPart_name() {
        return part_name;
    }

    /**
     * 设置
     * @param part_name
     */
    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    /**
     * 获取
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toString() {
        return "InventoryRecord{id = " + id + ", type = " + type + ", part_id = " + part_id + ", part_name = " + part_name + ", data = " + data + ", quantity = " + quantity + ", remarks = " + remarks + "}";
    }
}
