package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;


public interface OnCustomerListener {
	// 加载客户列表数据
	void onLoadDataSuccess(List<Customer> customers);
	void onLoadDataFailure(String errorMessage);
}
