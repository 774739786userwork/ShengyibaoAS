package com.bangware.shengyibao.customer.model.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 营销区域数据解析
 * @author ccssll
 *
 */
public class CustomerSalerAreaInfo {
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_NODATA = "1";
	private String msg;
	private String result;
	private Data data;
	
	class Data{
		List<CustomerSalerArea> saleAreaList = new ArrayList<CustomerSalerArea>();

		public List<CustomerSalerArea> getSaleAreaList() {
			return saleAreaList;
		}

		public void setSaleAreaList(List<CustomerSalerArea> saleAreaList) {
			this.saleAreaList = saleAreaList;
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
	
	public List<CustomerSalerArea> getCustomerSalerAreas(){
		return this.data.getSaleAreaList();
	}
}
