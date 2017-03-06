package com.bangware.shengyibao.ladingbilling.model.entity;

import java.io.Serializable;

/**
 * 提货单模型
 * @author ccssll
 *
 */
public class LadingbillingQuery implements Serializable{
	private static final long serialVersionUID = -7060210544600464481L;
	
	private String ladingbilling_date; //提货日期
	private String ladingbilling_numer; //提货编号
	private String ladingbilling_product; //提货产品
	private String loadingcount;			//车次
	private String ladingbilling_person;    //开单人
	
	public String getLadingbilling_date() {
		return ladingbilling_date;
	}
	public void setLadingbilling_date(String ladingbilling_date) {
		this.ladingbilling_date = ladingbilling_date;
	}
	public String getLadingbilling_numer() {
		return ladingbilling_numer;
	}
	public void setLadingbilling_numer(String ladingbilling_numer) {
		this.ladingbilling_numer = ladingbilling_numer;
	}
	public String getLadingbilling_product() {
		return ladingbilling_product;
	}
	public void setLadingbilling_product(String ladingbilling_product) {
		this.ladingbilling_product = ladingbilling_product;
	}
	public String getLoadingcount() {
		return loadingcount;
	}
	public void setLoadingcount(String loadingcount) {
		this.loadingcount = loadingcount;
	}
	public String getLadingbilling_person() {
		return ladingbilling_person;
	}
	public void setLadingbilling_person(String ladingbilling_person) {
		this.ladingbilling_person = ladingbilling_person;
	}
	
	private String ladingbilling_total_count; //提货总次数

	public String getLadingbilling_total_count() {
		return ladingbilling_total_count;
	}
	public void setLadingbilling_total_count(String ladingbilling_total_count) {
		this.ladingbilling_total_count = ladingbilling_total_count;
	}
	
		
}
