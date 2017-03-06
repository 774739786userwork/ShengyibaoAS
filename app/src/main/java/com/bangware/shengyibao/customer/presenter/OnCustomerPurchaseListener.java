package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;

import java.util.List;

public interface OnCustomerPurchaseListener {
	//客户进货记录监听事件
	void onPurchaseLoaded(List<CustomerPurchase> purchaseInfo);
	void onError(String errorMessage);
}
