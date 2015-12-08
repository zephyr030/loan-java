package com.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysTableCode {
    private Integer id;

    private String typeid;

    private String typecode;

    private String text;

    private String disc;

    private Integer available;

    private Date createtime;

    private String strval;

    private Long longval;

    private BigDecimal numberval;

    private String textval;

    private Integer sIndex;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode == null ? null : typecode.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc == null ? null : disc.trim();
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStrval() {
        return strval;
    }

    public void setStrval(String strval) {
        this.strval = strval == null ? null : strval.trim();
    }

    public Long getLongval() {
        return longval;
    }

    public void setLongval(Long longval) {
        this.longval = longval;
    }

    public BigDecimal getNumberval() {
        return numberval;
    }

    public void setNumberval(BigDecimal numberval) {
        this.numberval = numberval;
    }

    public String getTextval() {
        return textval;
    }

    public void setTextval(String textval) {
        this.textval = textval == null ? null : textval.trim();
    }

    public Integer getsIndex() {
        return sIndex;
    }

    public void setsIndex(Integer sIndex) {
        this.sIndex = sIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}