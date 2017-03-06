package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.user.model.entity.User;

public interface CustomerInfoPresenter {
	//客户信息展示
	void queryCustomerInfoData(User user,String customer_id);
		
	void destory();
}
