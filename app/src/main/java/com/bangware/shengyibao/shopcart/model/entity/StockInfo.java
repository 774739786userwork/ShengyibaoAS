package com.bangware.shengyibao.shopcart.model.entity;

import com.bangware.shengyibao.model.Product;

import java.util.ArrayList;
import java.util.List;



public class StockInfo {
	public static final String RESULT_SUCCESS = "0";
	public static final String RESULT_NODATA = "1";
	private String msg;
	private String result;
	private Data data;
	
	class Data{
		private List<Product> good_list = new ArrayList<Product>();
		private String car_id;
		private String car_number;
		public List<Product> getGood_list() {
			return good_list;
		}
		public void setGood_list(List<Product> good_list) {
			this.good_list = good_list;
		}
		public String getCar_id() {
			return car_id;
		}
		public void setCar_id(String car_id) {
			this.car_id = car_id;
		}
		public String getCar_number() {
			return car_number;
		}
		public void setCar_number(String car_number) {
			this.car_number = car_number;
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
	
	public List<Product> getProducts(){
		return this.data.getGood_list();
	}
	public String getCarId(){
		return this.data.getCar_id();
	}
	public String getCarNumber(){
		return this.data.getCar_number();
	}
	
}
