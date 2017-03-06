package com.bangware.shengyibao.customercontacts.view;

import com.bangware.shengyibao.activity.BaseView;
import com.bangware.shengyibao.customer.model.entity.Contacts;

import java.util.List;


public interface CustomerContactsView extends BaseView {
	 
	 void doCustomerContactsLoadSuccess(List<Contacts> Contacts);
}
