package com.bangware.shengyibao.customercontacts.presenter.impl;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customercontacts.model.CustomerContactsModel;
import com.bangware.shengyibao.customercontacts.model.impl.CustomerContactsModelImpl;
import com.bangware.shengyibao.customercontacts.presenter.CustomerContactsPresenter;
import com.bangware.shengyibao.customercontacts.presenter.OnCustomerContactsListener;
import com.bangware.shengyibao.customercontacts.view.CustomerContactsView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class CustomerContactsPresenterImpl implements CustomerContactsPresenter,OnCustomerContactsListener {
	
	public  static final String  REQUEST_TAG = "CustomerContacts";
	
    private CustomerContactsModel customerContactsModel;
    private CustomerContactsView contactsView;
    private String requestTag;
	public CustomerContactsPresenterImpl(
			CustomerContactsView contactsView) {
		 this.requestTag=REQUEST_TAG+System.currentTimeMillis();
		this.customerContactsModel = new CustomerContactsModelImpl();
		this.contactsView = contactsView;
	}
	@Override
	public void loadCustomerContacts(User user, int nPage, int nSpage,
									 String contactName, String phone) {
		contactsView.showLoading();
		customerContactsModel.loadCustomerContacts(requestTag,user, nPage, nSpage, contactName, phone, this);
	}
	@Override
	public void onLoadCustomerContactsSuccess(List<Contacts> contacts) {
		if(contacts != null){
			contactsView.doCustomerContactsLoadSuccess(contacts);
			contactsView.hideLoading();
		}
	}
	@Override
	public void onLoadCustomerContactsFailure(String errorMessage) {
		contactsView.showMessage(errorMessage);
	}
	@Override
	public void onDestroy() {
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

}
