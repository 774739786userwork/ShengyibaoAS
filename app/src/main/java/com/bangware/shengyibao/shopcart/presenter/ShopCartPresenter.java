package com.bangware.shengyibao.shopcart.presenter;


import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.user.model.entity.User;


public interface ShopCartPresenter {
	/**
	 * 加载库存
	 */
	public void loadStocks(User user);
	/**
	 * 加载送货单
	 * @param deliveryNoteId
	 */
	public void loadDeliveryNote(String deliveryNoteId);
	/**
	 * 添加商品
	 * @param product
	 * @param salesVolume
	 * @param giftsVolume
	 * @param price
	 */
	public void addGoods(ShopCartGoods goods);
	
	/**
	 * 删除商品
	 * @param product
	 */
	public void removeGoods(Product product);
	/**
	 * 清空购物车
	 */
	public void removeAllGoods();
	/**
	 * 获取购物车
	 * @return
	 */
	public ShopCart getShopCart();
	/**
	 * 销毁
	 */
	public void destroy();
	
}
