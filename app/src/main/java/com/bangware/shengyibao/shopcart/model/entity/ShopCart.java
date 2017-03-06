package com.bangware.shengyibao.shopcart.model.entity;

import android.util.Log;

import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNoteGoods;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.user.model.entity.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车
 * @author luming.tang
 *
 */
public class ShopCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2011428588817025994L;
	private Customer customer;
	private User user;
	private Map<String,ShopCartGoods> goodsList = new HashMap<String,ShopCartGoods>();
	private double totalAmount=0;
	private int totalVolumes=0;
	private String carId;
	private String contact_phone;
	private String contact_name;
	private String carNumber;
	private double p_totalForegift;//单个产品押金总计
	private int totalForegift=0;//整个购物车的押金

	public ShopCart(){
	}

	public double getP_totalForegift() {
		return p_totalForegift*totalVolumes;
	}

	public void setP_totalForegift(double p_totalForegift) {
		this.p_totalForegift = p_totalForegift;
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

	public void setUser(User user){
		this.user = user;
	}
	public User getUser(){
		return user;
	}
	
	public List<ShopCartGoods> getAllGoodsList(){
		List<ShopCartGoods> list = new ArrayList<ShopCartGoods>();
		for(String key : goodsList.keySet()){
			list.add(goodsList.get(key));
		}
		return list; 
	}
	
	public Map<String, ShopCartGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(Map<String, ShopCartGoods> goodsList) {
		this.goodsList = goodsList;
	}
	
	public ShopCartGoods getGoods(String productId){
		return goodsList.get(productId);
	}
	public void addGoods(ShopCartGoods goods){
		this.goodsList.put(goods.getProduct().getId(), goods);
	}
	public void removeGoods(Product product){
		this.goodsList.remove(product.getId());
	}
	
	public void removeAllGoods(){
		this.goodsList.clear();
		totalAmount = 0;
		totalVolumes = 0;
		totalForegift = 0;
	}
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalForegift() {
		return totalForegift;
	}

	public void setTotalForegift(int totalForegift) {
		this.totalForegift = totalForegift;
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
	

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	/**
	 * 将购物车转换成送货单
	 * @return DeliveryNote
	 */
	public DeliveryNote toDeliveryNote(){
		
		DeliveryNote deliveryNote = new DeliveryNote();
		deliveryNote.setCustomer(this.getCustomer());
		deliveryNote.setTotalAmount(this.getTotalAmount());
		deliveryNote.setTotalForeigft(this.getTotalForegift());
		deliveryNote.setTotalVolumes(this.getTotalVolumes());
		deliveryNote.setCarId(this.getCarId());
		deliveryNote.setCarNumber(this.getCarNumber());
		deliveryNote.setContact_phone(this.getContact_phone());
		deliveryNote.setContact_name(this.getContact_name());
		deliveryNote.setUser(this.getUser());
		deliveryNote.setP_totalForeigft(this.getP_totalForegift());
		for(ShopCartGoods goods : this.getAllGoodsList()){
			DeliveryNoteGoods dgoods = new DeliveryNoteGoods();
			dgoods.setAmount(goods.getAmount());
			dgoods.setP_totalforegift(goods.getP_totalforegift());
			dgoods.setSalesVolume(goods.getSalesVolume());
			dgoods.setGiftsVolume(goods.getGiftsVolume());
			dgoods.setPrice(goods.getPrice());
			dgoods.setProduct(goods.getProduct());
			deliveryNote.getGoodsList().add(dgoods);
		}
		
		return deliveryNote;
	}



}
