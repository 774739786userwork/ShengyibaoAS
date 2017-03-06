package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;
/**
 * 客户进货记录模型
 * @author ccssll
 *
 */
public class CustomerPurchase implements Serializable{
	private static final long serialVersionUID = -5622063816597717236L;
	
	private String delivery_id;
	private String delivery_date;//进货时间
	private String serial_number;//订单编号
	private String employee_name;//送货人
	private String product_edit;//产品详情
	private double total_sum;//进货金额
	private double unpaid_total_sum;//未付金额

	public String getProduct_edit() {
		return product_edit;
	}

	public void setProduct_edit(String product_edit) {
		this.product_edit = product_edit;
	}

	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public double getTotal_sum() {
		return total_sum;
	}
	public void setTotal_sum(double total_sum) {
		this.total_sum = total_sum;
	}
	public double getUnpaid_total_sum() {
		return unpaid_total_sum;
	}
	public void setUnpaid_total_sum(double unpaid_total_sum) {
		this.unpaid_total_sum = unpaid_total_sum;
	}
	
	private String purchase_total_sum; //进货金额总计
	private String purchase_untotal_sum; //未付款总计
	private int total_record;  //分页的总记录数

	public String getPurchase_total_sum() {
		return purchase_total_sum;
	}
	public void setPurchase_total_sum(String purchase_total_sum) {
		this.purchase_total_sum = purchase_total_sum;
	}
	public String getPurchase_untotal_sum() {
		return purchase_untotal_sum;
	}
	public void setPurchase_untotal_sum(String purchase_untotal_sum) {
		this.purchase_untotal_sum = purchase_untotal_sum;
	}
	public int getTotal_record() {
		return total_record;
	}
	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}
	
	
}
