package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;


public interface OnCustomerInfoListener {
	// 加载客户信息
	void onLoadCustomerInfo(List<Customer> customers);
	void onError(String errorMessage);
}
