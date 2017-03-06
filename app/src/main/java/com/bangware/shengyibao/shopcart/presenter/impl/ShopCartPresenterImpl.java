package com.bangware.shengyibao.shopcart.presenter.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.shopcart.model.ShopCartModel;
import com.bangware.shengyibao.shopcart.model.entity.ShopCart;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.shopcart.model.entity.StockInfo;
import com.bangware.shengyibao.shopcart.model.impl.ShopCartModelImpl;
import com.bangware.shengyibao.shopcart.presenter.ShopCartListener;
import com.bangware.shengyibao.shopcart.presenter.ShopCartPresenter;
import com.bangware.shengyibao.shopcart.view.ShopCartView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class ShopCartPresenterImpl implements ShopCartPresenter {
	public static final String REQUEST_TAG = "ShopCart";
	private ShopCart shopCart;
	private ShopCartView shopCartView;
	private ShopCartModel shopCartModel;
	
	private String requestTag;
	
	public ShopCartPresenterImpl(ShopCartView shopCartView){
		this.requestTag=REQUEST_TAG + System.currentTimeMillis();
		this.shopCartView = shopCartView;
		this.shopCartModel = new ShopCartModelImpl();
		this.shopCart = new ShopCart();
		this.shopCart.setUser(AppContext.getInstance().getUser());
	}
	@Override
	public void loadStocks(User user) {
		String salerId = user.getEmployee_id();
		String token = user.getLogin_token();
		Date date = new Date();
		shopCartView.showLoading();
		shopCartModel.loadStocks(requestTag, salerId, date, token, new ShopCartListenerImpl());
	}
	@Override
	public void loadDeliveryNote(String deliveryNoteId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addGoods(ShopCartGoods goods) {
		shopCart.addGoods(goods);
		updateDeliveryNote();
		shopCartView.doChanged(shopCart);
	}
	@Override
	public void removeGoods(Product product) {
		shopCart.removeGoods(product);
		updateDeliveryNote();
		shopCartView.doChanged(shopCart);
	} 
	@Override
	public void removeAllGoods(){
		shopCart.removeAllGoods();
		updateDeliveryNote();
		shopCartView.doChanged(shopCart);
	}
	
	@Override
	public ShopCart getShopCart() {
		// TODO Auto-generated method stub
		return this.shopCart;
	}
	@Override
	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}
	class ShopCartListenerImpl implements ShopCartListener {
		@Override
		public void onDeliveryNodeLoaded(DeliveryNote deliveryNote) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onStockLoaded(StockInfo stockInfo) {
			shopCartView.hideLoading();
			shopCartView.showMessage(stockInfo.getMsg());
			shopCartView.loadProductStock(stockInfo.getProducts());
			shopCart.setCarId(stockInfo.getCarId());
			shopCart.setCarNumber(stockInfo.getCarNumber());
		}
		@Override
		public void onErrors(String errorMessage) {
			shopCartView.showMessage(errorMessage);
		}
	}
	/**
	 * shopcart的总数量，总金额，总押金的累加
	 */
	private void updateDeliveryNote(){
		List<ShopCartGoods> shopCartGoodsList = shopCart.getAllGoodsList();
		int totalVolumes = 0;
		double totalAmount = 0;
		double p_totalFordgift=0;
		int totalFordgift=0;
		for(ShopCartGoods goods: shopCartGoodsList){
			totalVolumes+=goods.getTotalVolume();
			totalAmount+=goods.getTotalAmount()+goods.getTotalForegift();
			p_totalFordgift=goods.getTotalForegift();
			totalFordgift+=goods.getTotalForegift();
		}
		BigDecimal bd = new BigDecimal(totalAmount);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		shopCart.setTotalAmount(bd.doubleValue());
		shopCart.setTotalVolumes(totalVolumes);
		shopCart.setTotalForegift(totalFordgift);
		shopCart.setP_totalForegift(p_totalFordgift);
	}
}
