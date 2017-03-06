package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.AddContactsPresenter;
import com.bangware.shengyibao.customer.presenter.OnAddContactsListener;
import com.bangware.shengyibao.customer.view.AddContactsView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


/**
 * 添加联系人PresenterImpl
 * @author zcb
 *
 */
public class AddContactsPresenterImp implements AddContactsPresenter,OnAddContactsListener {
	public static final String REQUEST_TAG = "AddContacts";
	private String requestTag;
	private CustomerModel mCustomerModel;
	private AddContactsView mAddContactsView;
	
	public AddContactsPresenterImp(AddContactsView mAddContactsView) {
		super();
		requestTag = requestTag+System.currentTimeMillis();
		this.mCustomerModel = new CustomerModelImpl();
		this.mAddContactsView = mAddContactsView;
	}

	@Override
	public void addContacts(User user, String customer_id, String name, String mobile1, String mobile2, String position) {
		mCustomerModel.addContacts(requestTag, user, customer_id,name,mobile1,mobile2,position, this);
	}

	@Override
	public void destory() {
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onAddContactsSuccess(String successMessage) {
		mAddContactsView.hideLoading();
		mAddContactsView.addContactsSuccessMessage(successMessage);
	}

	@Override
	public void onAddFailure(String errorMessage) {
		mAddContactsView.showMessage(errorMessage);
	}

}
