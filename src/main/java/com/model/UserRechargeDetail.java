package com.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserRechargeDetail {
    private Long id;

    private String rectype;

    private Long userId;

    private String flowno;

    private BigDecimal amount;

    private Date rectime;

    private Integer status;

    private Date acttime;

    private Integer updateUid;

    private String bankId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRectype() {
        return rectype;
    }

    public void setRectype(String rectype) {
        this.rectype = rectype == null ? null : rectype.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFlowno() {
        return flowno;
    }

    public void setFlowno(String flowno) {
        this.flowno = flowno == null ? null : flowno.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRectime() {
        return rectime;
    }

    public void setRectime(Date rectime) {
        this.rectime = rectime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getActtime() {
        return acttime;
    }

    public void setActtime(Date acttime) {
        this.acttime = acttime;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}