package com.bangware.shengyibao.main.model.entity;

import java.io.Serializable;

public class ThisMonthSummary implements Serializable {
private static final long serialVersionUID = 1L;
	
	private String monthtime; //本月时间
	private String monthtop;	//月排名情况
	private String customersum; //总客户数
	private String billingcustomer; //开单客户
	private String monthsalermoney; //月销售总额
	private String monthsalerunpaid; //月未付款
	
	public String getMonthtime() {
		return monthtime;
	}
	public void setMonthtime(String monthtime) {
		this.monthtime = monthtime;
	}
	public String getMonthtop() {
		return monthtop;
	}
	public void setMonthtop(String monthtop) {
		this.monthtop = monthtop;
	}
	public String getCustomersum() {
		return customersum;
	}
	public void setCustomersum(String customersum) {
		this.customersum = customersum;
	}
	public String getBillingcustomer() {
		return billingcustomer;
	}
	public void setBillingcustomer(String billingcustomer) {
		this.billingcustomer = billingcustomer;
	}
	public String getMonthsalermoney() {
		return monthsalermoney;
	}
	public void setMonthsalermoney(String monthsalermoney) {
		this.monthsalermoney = monthsalermoney;
	}
	public String getMonthsalerunpaid() {
		return monthsalerunpaid;
	}
	public void setMonthsalerunpaid(String monthsalerunpaid) {
		this.monthsalerunpaid = monthsalerunpaid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
