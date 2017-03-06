package com.bangware.shengyibao.main.model.entity;

public class ToDaySummary {
	private static final long serialVersionUID = 1L;
	private String todaytime;  //当天时间
	private String todaysaler; //当天销售额
	
	private int ladingbill_sum;//提货单记录数
	private int deliverynote_sum;//送货单记录数
	private int ordernote_sum;//订货单记录数
	private int returngood_sum;//退货单记录数
	
	public String getTodaytime() {
		return todaytime;
	}
	public void setTodaytime(String todaytime) {
		this.todaytime = todaytime;
	}
	public String getTodaysaler() {
		return todaysaler;
	}
	public void setTodaysaler(String todaysaler) {
		this.todaysaler = todaysaler;
	}
	public int getLadingbill_sum() {
		return ladingbill_sum;
	}
	
	public int getDeliverynote_sum() {
		return deliverynote_sum;
	}
	public void setDeliverynote_sum(int deliverynote_sum) {
		this.deliverynote_sum = deliverynote_sum;
	}
	public void setLadingbill_sum(int ladingbill_sum) {
		this.ladingbill_sum = ladingbill_sum;
	}
	
	public int getOrdernote_sum() {
		return ordernote_sum;
	}
	public void setOrdernote_sum(int ordernote_sum) {
		this.ordernote_sum = ordernote_sum;
	}
	public int getReturngood_sum() {
		return returngood_sum;
	}
	public void setReturngood_sum(int returngood_sum) {
		this.returngood_sum = returngood_sum;
	}
	
}
