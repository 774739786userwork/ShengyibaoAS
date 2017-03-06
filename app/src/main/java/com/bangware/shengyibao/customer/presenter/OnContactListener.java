package com.bangware.shengyibao.customer.presenter;

import com.bangware.shengyibao.customer.model.entity.Contacts;

import java.util.List;


public interface OnContactListener {
	//联系人
	void onLoadContact(List<Contacts> contacts);
	
	void onLoadFailure(String errorMessage);
}
