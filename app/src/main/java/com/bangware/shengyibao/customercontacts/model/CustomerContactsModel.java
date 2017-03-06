package com.bangware.shengyibao.customercontacts.model;


import com.bangware.shengyibao.customercontacts.presenter.OnCustomerContactsListener;
import com.bangware.shengyibao.user.model.entity.User;


public interface CustomerContactsModel {
	public void loadCustomerContacts(String requestTag, User user, int nPage, int nSpage, String phone,
									 String contactName,
									 OnCustomerContactsListener customerContactsListener);
}
