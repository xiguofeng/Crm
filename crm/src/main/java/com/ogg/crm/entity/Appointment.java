package com.ogg.crm.entity;

import java.io.Serializable;

public class Appointment implements Serializable {

	private static final long serialVersionUID = 8912365559481657349L;

    /**
     * 客户名
     */
	private String customerName;

    /**
     * 创建时间
     */
	private String createTime;

    /**
     * 状态 0 1
     */
	private String status;

    /**
     * 电话号码
     */
	private String customerTel;

    /**
     * 所属人
     */
    private String customerManagerId;

    /**
     * 提醒时间
     */
    private String reserveTime;

    /**
     * 主键：提醒ID
     */
    private String remindId;

    /**
     * 内容
     */
    private String shortDesc;

    /**
     * 业务类型
     */
    private String bussinessDes;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerManagerId() {
        return customerManagerId;
    }

    public void setCustomerManagerId(String customerManagerId) {
        this.customerManagerId = customerManagerId;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getRemindId() {
        return remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getBussinessDes() {
        return bussinessDes;
    }

    public void setBussinessDes(String bussinessDes) {
        this.bussinessDes = bussinessDes;
    }
}
