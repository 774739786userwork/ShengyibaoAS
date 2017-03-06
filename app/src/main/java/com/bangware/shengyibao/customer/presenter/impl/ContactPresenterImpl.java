package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.ContactPresenter;
import com.bangware.shengyibao.customer.presenter.OnContactListener;
import com.bangware.shengyibao.customer.view.ContactView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;


public class ContactPresenterImpl implements ContactPresenter,OnContactListener {
	public static final String REQUEST_TAG = "Contact";
	private String requestTag;
	private CustomerModel mCustomerMdel;
	private ContactView mContactView;
	
	public ContactPresenterImpl(ContactView mContactView) {
		// TODO Auto-generated constructor stub
		requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.mContactView=mContactView;
		this.mCustomerMdel = new CustomerModelImpl();
	}
	@Override
	public void loadContact(User user,String customerId) {
		// TODO Auto-generated method stub
		mCustomerMdel.loadContact(requestTag, customerId, user, this);
	}

	//销毁请求队列
	@Override
	public void destory() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onLoadContact(List<Contacts> contacts) {
		// TODO Auto-generated method stub
		mContactView.hideLoading();
		mContactView.loadContact(contacts);
	}

	@Override
	public void onLoadFailure(String errorMessage) {
		// TODO Auto-generated method stub
		mContactView.hideLoading();
		mContactView.showMessage(errorMessage);
	}

}
