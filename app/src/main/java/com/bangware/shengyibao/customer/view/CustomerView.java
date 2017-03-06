package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Customer;

import java.util.List;


public interface CustomerView extends BaseView {
	
	void addCustomers(List<Customer> customers);
	void showLoadFailureMsg(String errorMessage);
}
