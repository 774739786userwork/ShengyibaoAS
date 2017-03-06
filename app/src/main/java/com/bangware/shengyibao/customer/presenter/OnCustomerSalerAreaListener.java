package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.CustomerSalerAreaInfo;

import java.util.List;


public interface OnCustomerSalerAreaListener {
	// 加载营销区域数据
	void onLoadCustomerSalerArea(CustomerSalerAreaInfo salerareaInfos);
	void onError(String errorMessage);
}
