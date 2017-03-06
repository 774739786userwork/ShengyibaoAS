package com.bangware.shengyibao.shopcart.model.entity;

import com.bangware.shengyibao.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;



public class ShopCartGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7264801786671875024L;
	private Product product;//产品
	private int salesVolume=0;//销售数量
	private int giftsVolume=0;//赠送数量
	private double price=0;//单价
	private double p_totalforegift;
	private double amount=0;//销售金额

	public double getP_totalforegift() {
		return getTotalForegift();
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
		double total=salesVolume*price;
		/*BigDecimal bd = new BigDecimal(total);
		bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);*/
		return total;//bd.doubleValue();
	}

	public double getTotalForegift()
	{
		return product.getForegift()*getTotalVolume();
	}
}
