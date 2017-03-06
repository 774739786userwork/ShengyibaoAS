package com.bangware.shengyibao.deliverynote.model.entity;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.shopcart.model.entity.Payment;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.NumberUtils;


/**
 * 购物车实体Bean
 */
public class DeliveryNote implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6566901298045990351L;
	private Customer customer;
	private User user;
	private int flag;
	private Payment payment;
	private List<DeliveryNoteGoods> goodsList = new ArrayList<DeliveryNoteGoods>();
	private String contact_phone;
	private String contact_name;
	private double totalAmount=0;
	private int totalVolumes=0;
	private String carId="";
	private String carNumber="";
	private double p_totalForeigft=0;//送货单单个产品的总押金
	private double totalForeigft=0;//送货单的总押金
	private double receiveAmount=0;//已收
	private double unpaidAmount=0;//未付
	private String deliveryNote_product;//查询界面产品展示
	private Date deliveryDate=new Date();
	
	private double lng=0;
	private double lat=0;
	
	private String remark="";
	private String delivery_goods_count = "";

	private double rounding; //四舍五入
	private double smallchange;//抹零

	public double getRounding() {
		return rounding;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setRounding(double rounding) {
		this.rounding = rounding;
	}

	public double getSmallchange() {
		return smallchange;
	}

	public void setSmallchange(double smallchange) {
		this.smallchange = smallchange;
	}

	public DeliveryNote(){
	}
	
	public String getRemark() {
		return remark;
	}

	public String getDeliveryNote_product() {
		return deliveryNote_product;
	}

	public void setDeliveryNote_product(String deliveryNote_product) {
		this.deliveryNote_product = deliveryNote_product;
	}

	public double getTotalForeigft() {
		return totalForeigft;
	}

	public double getP_totalForeigft() {
		return p_totalForeigft;
	}

	public void setP_totalForeigft(double p_totalForeigft) {
		this.p_totalForeigft = p_totalForeigft;
	}

	public void setTotalForeigft(double totalForeigft) {
		this.totalForeigft = totalForeigft;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public double getReceiveAmount() {
		return receiveAmount;
	}


	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}


	public double getUnpaidAmount() {
		return unpaidAmount;
	}


	public void setUnpaidAmount(double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getDelivery_goods_count() {
		return delivery_goods_count;
	}

	public void setDelivery_goods_count(String delivery_goods_count) {
		this.delivery_goods_count = delivery_goods_count;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getCarId() {
		return carId;
	}


	public void setCarId(String carId) {
		this.carId = carId;
	}


	public String getCarNumber() {
		return carNumber;
	}


	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public List<DeliveryNoteGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<DeliveryNoteGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalVolumes() {
		return totalVolumes;
	}

	public void setTotalVolumes(int totalVolumes) {
		this.totalVolumes = totalVolumes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	private String delivery_id;//送货单id
	private String delivery_date; //时间
	private String serial_number;	//编号
	//mobile1
	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	private String d_total_sum; //总计销售额
	private String d_unpaid_total_sum; //未收款总计
	private int total_record;  //分页的总记录数
	private String customer_number;//客户数

	public String getD_total_sum() {
		return d_total_sum;
	}

	public void setD_total_sum(String d_total_sum) {
		this.d_total_sum = d_total_sum;
	}

	public String getD_unpaid_total_sum() {
		return d_unpaid_total_sum;
	}

	public void setD_unpaid_total_sum(String d_unpaid_total_sum) {
		this.d_unpaid_total_sum = d_unpaid_total_sum;
	}

	public int getTotal_record() {
		return total_record;
	}

	public void setTotal_record(int total_record) {
		this.total_record = total_record;
	}

	public String getCustomer_number() {
		return customer_number;
	}

	public void setCustomer_number(String customer_number) {
		this.customer_number = customer_number;
	}

}
