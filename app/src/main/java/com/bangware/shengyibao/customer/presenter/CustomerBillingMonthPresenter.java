package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface CustomerBillingMonthPresenter {
	//客户月开单、未开单数据加载
	void loadCustomerBillingMonthData(User user,int nPage, int nSpage, int show_type, String compositor);
	
	void destroy();
}
