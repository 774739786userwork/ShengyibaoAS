package com.bangware.shengyibao.customer.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface CustomerPurchasePresenter {
	//客户进货记录
	void queryCustomerPurchaseData(User user,String customer_id, int nPage, int nSpage, String begin_date, String end_date);
	
	void destory();
}
