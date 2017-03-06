package com.bangware.shengyibao.customercontacts.presenter;

import com.bangware.shengyibao.customer.model.entity.Contacts;

import java.util.List;


public interface OnCustomerContactsListener {
	void onLoadCustomerContactsSuccess(List<Contacts> contacts);
	void onLoadCustomerContactsFailure(String errorMessage);
}
