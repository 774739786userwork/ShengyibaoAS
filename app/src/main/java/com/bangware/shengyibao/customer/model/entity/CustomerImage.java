package com.bangware.shengyibao.customer.model.entity;

import java.io.Serializable;
/**
 * 客户图片实体bean
 * @author ccssll
 *
 */
public class CustomerImage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String img_id;
	private String img_url;
	
	public CustomerImage() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerImage(String img_id,String img_url) {
		// TODO Auto-generated constructor stub
		this.img_id= img_id;
		this.img_url=img_url;
	}
	
	public String getImg_id() {
		return img_id;
	}
	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
}
