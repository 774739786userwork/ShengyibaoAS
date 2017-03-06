package com.bangware.shengyibao.shopcart.model;

import com.bangware.shengyibao.shopcart.presenter.ShopCartListener;

import java.util.Date;

/**
 * 购物车Model
 * @author luming.tang
 *
 */
public interface ShopCartModel {
	/**
	 * 加载库存
	 * @param salerId 业务员
	 * @param date 日期
	 */
	public void loadStocks(String requestTag, String salerId, Date date, String token, ShopCartListener listener);
	
}
