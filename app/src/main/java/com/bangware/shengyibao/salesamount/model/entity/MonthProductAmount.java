package com.bangware.shengyibao.salesamount.model.entity;

import android.widget.TextView;

/**
 * 月销售金额展示实体类
 * @author zcb
 *
 */
public class MonthProductAmount {
   private String Sale_date;//时间
   private String Sales_amount_total_sum;//总销售额
   private String Sales_amount_unpaid_sum;//未付总金额
   private String Sales_amount_coutomer;//开单客户数
   private int ProductId;//产品id
   private String SalesAomuntName;//产品名
   private String SalesAomuntNumber;//产品总数量
   private String SalesAomuntSum;//产品总金额
   private int total_record;  //分页的总记录数
   private String SalesAomuntGiver;//产品总赠送量

public int getProductId() {
	return ProductId;
}

public void setProductId(int productId) {
	ProductId = productId;
}

public String getSalesAomuntName() {
	return SalesAomuntName;
}
public void setSalesAomuntName(String salesAomuntName) {
	SalesAomuntName = salesAomuntName;
}
public String getSalesAomuntNumber() {
	return SalesAomuntNumber;
}
public void setSalesAomuntNumber(String salesAomuntNumber) {
	SalesAomuntNumber = salesAomuntNumber;
}
public String getSalesAomuntSum() {
	return SalesAomuntSum;
}
public void setSalesAomuntSum(String salesAomuntSum) {
	SalesAomuntSum = salesAomuntSum;
}
public String getSalesAomuntGiver() {
	return SalesAomuntGiver;
}
public void setSalesAomuntGiver(String salesAomuntGiver) {
	SalesAomuntGiver = salesAomuntGiver;
}

public int getTotal_record() {
	return total_record;
}

public void setTotal_record(int total_record) {
	this.total_record = total_record;
}

public String getSale_date() {
	return Sale_date;
}

public void setSale_date(String sale_date) {
	Sale_date = sale_date;
}

public String getSales_amount_total_sum() {
	return Sales_amount_total_sum;
}

public void setSales_amount_total_sum(String sales_amount_total_sum) {
	Sales_amount_total_sum = sales_amount_total_sum;
}

public String getSales_amount_unpaid_sum() {
	return Sales_amount_unpaid_sum;
}

public void setSales_amount_unpaid_sum(String sales_amount_unpaid_sum) {
	Sales_amount_unpaid_sum = sales_amount_unpaid_sum;
}

public String getSales_amount_coutomer() {
	return Sales_amount_coutomer;
}

public void setSales_amount_coutomer(String sales_amount_coutomer) {
	Sales_amount_coutomer = sales_amount_coutomer;
}

   
}
