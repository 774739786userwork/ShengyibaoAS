package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户详情实体类
 * @author ccssll
 *
 */
public class CustomerInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Customer customer;
	private List<CustomerKind> kinds = new ArrayList<CustomerKind>();
	private List<Contacts> contacts = new ArrayList<Contacts>();

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	public List<CustomerKind> getKinds() {
		return kinds;
	}

	public void setKinds(List<CustomerKind> kinds) {
		this.kinds = kinds;
	}
	public void addCustomerKind(CustomerKind kinds){
		this.kinds.add(kinds);
	}

	public List<Contacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	
	public void addContacts(Contacts contacts){
		this.contacts.add(contacts);
	}
}
