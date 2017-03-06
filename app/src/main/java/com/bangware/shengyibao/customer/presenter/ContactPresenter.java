package com.bangware.shengyibao.customer.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface ContactPresenter {
	//查看联系人
	void loadContact(User user,String customerId);
	
	void destory();
}
