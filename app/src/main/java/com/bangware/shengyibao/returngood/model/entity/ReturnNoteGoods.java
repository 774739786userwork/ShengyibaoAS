package com.bangware.shengyibao.returngood.model.entity;

import android.util.Log;

import com.bangware.shengyibao.model.Product;

import java.io.Serializable;


/**
 * 退货单产品详情数据模型
 * @author ccssll
 *
 */
public class ReturnNoteGoods implements Serializable {
	private static final long serialVersionUID = -6115024539286808273L;
	private Product product;
	private int returnsVolume;//退货数量
	private int giftsVolume;//赠送数量
	private int retailVolume;//零售数量
	private double price;//单价
	private double basic_price;//产品基础单价
	private double returnsAmount;//退货金额
	
	public ReturnNoteGoods() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReturnNoteGoods(int returnsVolume, int giftsVolume,int retailVolume,double price,
			double returnsAmount) {
		super();
		this.returnsVolume = returnsVolume;
		this.giftsVolume = giftsVolume;
		this.price = price;
		this.returnsAmount = returnsAmount;
		this.retailVolume = retailVolume;
	}
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public double getBasic_price() {
		return basic_price;
	}

	public void setBasic_price(double basic_price) {
		this.basic_price = basic_price;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getReturnsVolume() {
		return returnsVolume;
	}
	public void setReturnsVolume(int returnsVolume) {
		this.returnsVolume = returnsVolume;
	}
	public int getGiftsVolume() {
		return giftsVolume;
	}
	public void setGiftsVolume(int giftsVolume) {
		this.giftsVolume = giftsVolume;
	}
	public double getReturnsAmount() {
		return returnsAmount;
	}
	public void setReturnsAmount(double returnsAmount) {
		this.returnsAmount = returnsAmount;
	}
	//总数量
	public int getTotalVolume(){
		return returnsVolume + giftsVolume + retailVolume;
	}
    //总的商品金额
	public double getTotalAmount(){
		Double totalAmount = returnsVolume * price + retailVolume * basic_price;
		return totalAmount;
	}

	public double getTotalBasicPrice(){
		return retailVolume * basic_price;
	}

    //总押金
	public double getTotalForegift()
	{
		return product.getForegift()*getTotalVolume();
	}

	public int getRetailVolume() {
		return retailVolume;
	}

	public void setRetailVolume(int retailVolume) {
		this.retailVolume = retailVolume;
	}

}
