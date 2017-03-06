package com.bangware.shengyibao.customer.presenter;


import com.bangware.shengyibao.user.model.entity.User;

public interface UpdateContactsPresenter {
	void updateContacts(User user, String contacts_id, String name, String mobile1, String mobile2, String position);
	void deleteContacts(User user, String contacts_id);
	void destory();
}
