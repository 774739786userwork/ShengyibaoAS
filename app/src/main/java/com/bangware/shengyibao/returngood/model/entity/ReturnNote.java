package com.bangware.shengyibao.returngood.model.entity;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.NumberUtils;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * 退货单查询实体bean
 * @author ccssll
 *
 */
public class ReturnNote implements Serializable{
	private static final long serialVersionUID = -6115024539286808273L;
	private String return_id; //退货单ID
	private String saler_id;  //业务员ID
	private String serial_number; //退货单编号
	private String return_date; //退货日期
	private Customer customer;
	private CarBean carBean;
	private User user;
	private String salerName;   //送货人
	private String return_reson; //退货原因
	private String total_sum;    //金额
	private int total_record; //总记录数
	private String return_total_sum;//总计金额
	private int returntotalVolumes=0;
	private double totalForeigft=0;//总押金
	
	private List<ReturnNoteGoods> goodslist = new ArrayList<ReturnNoteGoods>();
	
	public List<ReturnNoteGoods> getGoodslist() {
		return goodslist;
	}
	public void setGoodslist(List<ReturnNoteGoods> goodslist) {
		this.goodslist = goodslist;
	}
	public String getReturn_id() {
		return return_id;
	}
	public void setReturn_id(String return_id) {
		this.return_id = return_id;
	}
	public String getSaler_id() {
		return saler_id;
	}
	public void setSaler_id(String saler_id) {
		this.saler_id = saler_id;
	}
	
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getReturn_date() {
		return return_date;
	}
	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CarBean getCarBean() {
		return carBean;
	}

	public void setCarBean(CarBean carBean) {
		this.carBean = carBean;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSalerName() {
		return salerName;
	}
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	public String getReturn_reson() {
		return return_reson;
	}
	public void setReturn_reson(String return_reson) {
		this.return_reson = return_reson;
	}
	public String getTotal_sum() {
		return total_sum;
	}
	public void setTotal_sum(String total_sum) {
		this.total_sum = total_sum;
	}
	
	public int getTotal_record() {
		return total_record;
	}
	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}
	public String getReturn_total_sum() {
		return return_total_sum;
	}
	public void setReturn_total_sum(String return_total_sum) {
		this.return_total_sum = return_total_sum;
	}
	public double getTotalForeigft() {
		return totalForeigft;
	}
	public void setTotalForeigft(double totalForeigft) {
		this.totalForeigft = totalForeigft;
	}

	public int getReturntotalVolumes() {
		return returntotalVolumes;
	}

	public void setReturntotalVolumes(int returntotalVolumes) {
		this.returntotalVolumes = returntotalVolumes;
	}
}
