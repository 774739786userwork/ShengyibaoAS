package com.bangware.shengyibao.deliverynote.model.entity;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.io.Serializable;


public class DeliveryDetailProduct implements Serializable {
	private static final long serialVersionUID = -4033663599091826822L;
	
	private Customer customer;
	private String img_url;
	private String product_name;
	private String sale_quantity;
	private String price;
	private String gifts_quantity;
	private String sum;
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getSale_quantity() {
		return sale_quantity;
	}
	public void setSale_quantity(String sale_quantity) {
		this.sale_quantity = sale_quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGifts_quantity() {
		return gifts_quantity;
	}
	public void setGifts_quantity(String gifts_quantity) {
		this.gifts_quantity = gifts_quantity;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	
	
}
