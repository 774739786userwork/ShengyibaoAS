package com.bangware.shengyibao.customer.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户进货记录数据解析封装
 * @author ccssll
 *
 */
public class CustomerPurchaseInfo {
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_NODATA = "1";
	private String msg;
	private String result;
	private Data data;
	
	class Data{
		private String purchase_total_sum; //进货金额总计
		private String purchase_untotal_sum; //未付款总计
		private int total_record;  //分页的总记录数
		private List<CustomerPurchase> purchase_list = new ArrayList<CustomerPurchase>();
		
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
		
		public List<CustomerPurchase> getPurchase_list() {
			return purchase_list;
		}

		public void setPurchase_list(List<CustomerPurchase> purchase_list) {
			this.purchase_list = purchase_list;
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
	
	public List<CustomerPurchase> getCustomerPurcahses(){
		return this.data.getPurchase_list();
	}
	
	public String getPurchase_total_sum(){
		return this.data.getPurchase_total_sum();
	}
	
	public String getPurchase_untotal_sum(){
		return this.data.getPurchase_untotal_sum();
	}
	public int getTotalRecord() {
		return this.data.getTotal_record();
	}
}
