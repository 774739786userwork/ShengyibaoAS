package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Customer;

public interface CustomerDetailView extends BaseView {
	void loadCustomer(Customer customer);
	void showFailureMsg(String errorMessage);
}
