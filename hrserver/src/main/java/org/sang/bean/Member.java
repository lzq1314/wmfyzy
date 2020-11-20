package org.sang.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Member {
    private Long id;
    private String name;//会员名称
    private String sex;//性别
    private Date createDate;//创建日期
    private String phoneNumber;//手机号
    private String telPhoneNumber;//电话号
    private String idCardNumber;//身份证号
    private String memberCardNumber;//会员卡号
    private Double balance;//余额
    private String memberStatus;//会员状态
    private String remark;//备注
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelPhoneNumber() {
        return telPhoneNumber;
    }

    public void setTelPhoneNumber(String telPhoneNumber) {
        this.telPhoneNumber = telPhoneNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getMemberCardNumber() {
        return memberCardNumber;
    }

    public void setMemberCardNumber(String memberCardNumber) {
        this.memberCardNumber = memberCardNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}