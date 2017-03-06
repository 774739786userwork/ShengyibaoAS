package com.bangware.shengyibao.deliverynote.model.entity;

import java.util.ArrayList;
import java.util.List;

public class DeliveryInfo {
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_NODATA = "1";
	private String msg;
	private String result;
	private Data data;
	
	class Data{
		List<DeliveryNote> delivery_order_list = new ArrayList<DeliveryNote>();
		private String d_total_sum; //总计销售额
		private String d_unpaid_total_sum; //未收款总计
		private int total_record;  //分页的总记录数
		private String customer_number;//客户数
		//mobile1
		private String customer_phone;
		public List<DeliveryNote> getDelivery_order_list() {
			return delivery_order_list;
		}
		public void setDelivery_order_list(List<DeliveryNote> delivery_order_list) {
			this.delivery_order_list = delivery_order_list;
		}
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
		public String getCustomer_phone() {
			return customer_phone;
		}
		public void setCustomer_phone(String customer_phone) {
			this.customer_phone = customer_phone;
		}
		
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	public List<DeliveryNote> getDeliveryNote(){
		return this.data.getDelivery_order_list();
	}
	public String getCustomerNumber(){
		return this.data.getCustomer_number();
	}
	public String getDTotalSum(){
		return this.data.getD_total_sum();
	}
	
	public String getDUnpaidTotalSum(){
		return this.data.getD_unpaid_total_sum();
	}
	public int getTotalRecord() {
		return this.data.getTotal_record();
	}
	
}
