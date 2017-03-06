package com.bangware.shengyibao.customercontacts.presenter;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.user.model.entity.User;

import java.util.List;


public interface CustomerContactsPresenter {
	public void loadCustomerContacts(User user, int nPage, int nSpage, String contactName, String phone);
	void onDestroy();
	void onLoadCustomerContactsSuccess(List<Contacts> contacts);
}
