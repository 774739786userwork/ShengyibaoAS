package com.bangware.shengyibao.salesamount.model;


import com.bangware.shengyibao.salesamount.presenter.OnSalesAmountListener;
import com.bangware.shengyibao.user.model.entity.User;

public interface SalesAmountModel {
	public void loadCustomerContacts(String requestTag, User user, String begin_date, String end_date, int nPage, int nSpage,
									 OnSalesAmountListener salesAmountListener);
}
