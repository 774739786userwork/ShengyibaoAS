package com.bangware.shengyibao.shopcart.presenter;

import com.bangware.shengyibao.deliverynote.model.entity.DeliveryNote;
import com.bangware.shengyibao.shopcart.model.entity.StockInfo;

public interface ShopCartListener {

	public void onDeliveryNodeLoaded(DeliveryNote deliveryNote);
	public void onStockLoaded(StockInfo stockInfo);
	public void onErrors(String errorMessage);
}
