package org.sang.bean;

import java.util.Date;

public class Commodity {
    private Long id;
    private String commodity_name;//商品名称
    private Double commodity_amount;//金额
    private Date create_time;//创建时间
    private String commodity_status;//0正常，1下架
    private int cnt;
    
    
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCommodity_status() {
		return commodity_status;
	}
	public void setCommodity_status(String commodity_status) {
		this.commodity_status = commodity_status;
	}
}