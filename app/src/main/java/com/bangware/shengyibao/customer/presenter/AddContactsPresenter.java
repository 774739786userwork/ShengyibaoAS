package com.bangware.shengyibao.customer.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface AddContactsPresenter {
	void addContacts(User user, String customer_id, String name, String mobile1, String mobile2, String position);
	void destory();
}
