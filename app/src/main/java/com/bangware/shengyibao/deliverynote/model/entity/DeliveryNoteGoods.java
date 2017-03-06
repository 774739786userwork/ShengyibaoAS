package com.bangware.shengyibao.deliverynote.model.entity;

import com.bangware.shengyibao.model.Product;

import java.math.BigDecimal;


/**
 * 购物车商品清单
 * @author luming.tang
 *
 */
public class DeliveryNoteGoods implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6115024539286808273L;
	private Product product;//产品
	private int salesVolume;//销售数量
	private int giftsVolume;//赠送数量
	private double price;//单价
	private double p_totalforegift;
	private double amount;//销售金额
	
	public DeliveryNoteGoods() {
		
	}
	
	public DeliveryNoteGoods(int salesVolume, int giftsVolume, double price,
			double amount) {
		super();
		this.salesVolume = salesVolume;
		this.giftsVolume = giftsVolume;
		this.price = price;
		this.amount = amount;
	}

	public double getP_totalforegift() {
		return p_totalforegift;
	}

	public void setP_totalforegift(double p_totalforegift) {
		this.p_totalforegift = p_totalforegift;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	public int getGiftsVolume() {
		return giftsVolume;
	}
	public void setGiftsVolume(int giftsVolume) {
		this.giftsVolume = giftsVolume;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		BigDecimal   b   =   new   BigDecimal(amount);  
		this.amount = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
	}
	public int getTotalVolume(){
		return salesVolume + giftsVolume;
	}
	
	public double getTotalAmount(){
		double total=salesVolume * price;
		return total;
	}
}
