package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;

/**
 * 客户种类
 * @author luming.tang
 *
 */
public class CustomerKind implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4938080282064926478L;

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
