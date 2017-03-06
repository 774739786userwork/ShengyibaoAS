package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;

/**
 * 主页月开单客户查询响应事件接口
 * @author ccssll
 *
 */
public interface OnMonthCustomerBillingRecordListener {
	void onLoadDataSuccess(List<Customer> customers);
	void onLoadDataFailure(String errorMessage);
}
