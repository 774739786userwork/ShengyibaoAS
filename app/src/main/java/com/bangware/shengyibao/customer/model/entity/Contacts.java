package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 * @author luming.tang
 *
 */
public class Contacts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132060356108708680L;

	private String id;
	private String name;
	private String position;
	private String mobile1;
	private String mobile2;
	private boolean isFirst;//是否首要联系人
	private Customer customer;

	
	/*public Contacts(String id,String name, String mobile1,String mobile2) {
		super();
		this.id= id;
		this.name = name;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
	}*/
	public Contacts(String name, String position, String mobile1, String mobile2) {
		super();
		this.name = name;
		this.position = position;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
	}

	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Contacts() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	
	
}
