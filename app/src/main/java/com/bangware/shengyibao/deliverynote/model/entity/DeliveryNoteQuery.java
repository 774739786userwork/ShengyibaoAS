package com.bangware.shengyibao.deliverynote.model.entity;

import java.io.Serializable;

/**
 * 送货单查询数据模型
 * @author ccssll
 *
 */
public class DeliveryNoteQuery implements Serializable{
	private static final long serialVersionUID = -7060210544600464481L;
	
	private String delivery_id;//送货单id
	private String order_Time; //时间
	private String order_Id;	//编号
	private String order_total_money; //金额
	private String order_unpaid;//未付
	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
	public String getOrder_unpaid() {
		return order_unpaid;
	}
	public void setOrder_unpaid(String order_unpaid) {
		this.order_unpaid = order_unpaid;
	}
	public String getOrder_Time() {
		return order_Time;
	}
	public void setOrder_Time(String order_Time) {
		this.order_Time = order_Time;
	}
	public String getOrder_Id() {
		return order_Id;
	}
	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}
	public String getOrder_total_money() {
		return order_total_money;
	}
	public void setOrder_total_money(String order_total_money) {
		this.order_total_money = order_total_money;
	}
	
	private String d_total_sum; //总计销售额
	private String d_unpaid_total_sum; //未收款总计
	private int total_record;  //分页的总记录数
	private String customer_number;//客户数



	public String getCustomer_number() {
		return customer_number;
	}
	public void setCustomer_number(String customer_number) {
		this.customer_number = customer_number;
	}
	public String getD_total_sum() {
		return d_total_sum;
	}
	public void setD_total_sum(String d_total_sum) {
		this.d_total_sum = d_total_sum;
	}
	public String getD_unpaid_total_sum() {
		return d_unpaid_total_sum;
	}
	public void setD_unpaid_total_sum(String d_unpaid_total_sum) {
		this.d_unpaid_total_sum = d_unpaid_total_sum;
	}
	public int getTotal_record() {
		return total_record;
	}
	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}
	public DeliveryNoteQuery(String delivery_id, String order_Time,
			String order_Id, String order_total_money, String order_unpaid,
			String d_total_sum, String d_unpaid_total_sum, int total_record) {
		super();
		this.delivery_id = delivery_id;
		this.order_Time = order_Time;
		this.order_Id = order_Id;
		this.order_total_money = order_total_money;
		this.order_unpaid = order_unpaid;
		this.d_total_sum = d_total_sum;
		this.d_unpaid_total_sum = d_unpaid_total_sum;
		this.total_record = total_record;
	}
	public DeliveryNoteQuery() {
		super();
	}
	
	

}
