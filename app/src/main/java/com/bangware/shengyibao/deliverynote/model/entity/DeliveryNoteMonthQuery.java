package com.bangware.shengyibao.deliverynote.model.entity;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.io.Serializable;


/**
 * 销售记录数据模型
 * @author zcb
 *
 */
public class DeliveryNoteMonthQuery implements Serializable{
	private static final long serialVersionUID = -7060210544600464481L;
	
	private String delivery_id;//送货单id
	private String order_Time; //时间
	private String order_Id;	//编号
	private String order_shop_name; //店名
	private String order_total_money; //金额
	private String order_unpaid;//未付
	private Customer customer;//客户
	private String customer_phone;
	private String contact_name;
	private String deliveryNote_product;//查询界面产品展示;


	public String getDeliveryNote_product() {
		return deliveryNote_product;
	}

	public void setDeliveryNote_product(String deliveryNote_product) {
		this.deliveryNote_product = deliveryNote_product;
	}

	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
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
	public String getOrder_shop_name() {
		return order_shop_name;
	}
	public void setOrder_shop_name(String order_shop_name) {
		this.order_shop_name = order_shop_name;
	}
	public String getOrder_total_money() {
		return order_total_money;
	}
	public void setOrder_total_money(String order_total_money) {
		this.order_total_money = order_total_money;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	private String d_total_sum; //总计销售额
	private String d_unpaid_total_sum; //未收款总计
	private int total_record;  //分页的总记录数
	private String customer_number;
	
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
	
}
