package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;

/**
 * 客户营销区域模型
 * @author ccssll
 *
 */
public class CustomerSalerArea implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
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
	
	
}
