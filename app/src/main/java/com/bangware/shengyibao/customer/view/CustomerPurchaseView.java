package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.entity.CustomerPurchase;

import java.util.List;


public interface CustomerPurchaseView extends BaseView {
	void loadPurchaseData(List<CustomerPurchase> purchases);
	
	void loadCustomerInfoData(List<Customer> customers);
}
