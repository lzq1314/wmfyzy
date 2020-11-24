package org.sang.bean;

import java.util.Date;

public class Order {
    private Long id;
    private Long commodity;//商品id
    private Long member_id;//会员id
    private String commodity_name;//商品名称
    private Double commodity_amount;//金额
    private Date create_time;//创建时间
    private String phoneNumber;//手机号
    private String memberCardNumber;//会员卡号
    private int commodity_count; //商品数量
    private String member_name;//会员名称
    
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public int getCommodity_count() {
		return commodity_count;
	}
	public void setCommodity_count(int commodity_count) {
		this.commodity_count = commodity_count;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCommodity() {
		return commodity;
	}
	public void setCommodity(Long commodity) {
		this.commodity = commodity;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public Double getCommodity_amount() {
		return commodity_amount;
	}
	public void setCommodity_amount(Double commodity_amount) {
		this.commodity_amount = commodity_amount;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMemberCardNumber() {
		return memberCardNumber;
	}
	public void setMemberCardNumber(String memberCardNumber) {
		this.memberCardNumber = memberCardNumber;
	}
}