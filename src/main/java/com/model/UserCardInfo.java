package com.model;

import com.utils.StringUtils;
import com.utils.security.Security;

import java.math.BigDecimal;
import java.util.Date;

public class UserCardInfo {
    private Long id;

    private String account;

    private String customername;

    private Integer bank;

    private String cardnumber;

    private String mobile;

    private int status;

    private Date createtime;

    private BigDecimal balance;

    /*加密后的超盘账号*/
    private String encodeAccount;

    private String banknameStr;

    private String maskCardnumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEncodeAccount() {
        return Security.encrypt(account);
    }

    public void setEncodeAccount(String encodeAccount) {
        this.encodeAccount = encodeAccount;
    }

    public String getBanknameStr() {
        return banknameStr;
    }

    public void setBanknameStr(String banknameStr) {
        this.banknameStr = banknameStr;
    }

    public String getMaskCardnumber() {
        if(!StringUtils.isEmpty(this.cardnumber)) {
            maskCardnumber = StringUtils.maskString(this.cardnumber,4,10,"*");
        }
        return maskCardnumber;
    }

    public void setMaskCardnumber(String maskCardnumber) {
        this.maskCardnumber = maskCardnumber;
    }
}