package com.bangware.shengyibao.customer.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Contacts;

import java.util.List;



public interface ContactView extends BaseView {
	void loadContact(List<Contacts> contactList);
}
