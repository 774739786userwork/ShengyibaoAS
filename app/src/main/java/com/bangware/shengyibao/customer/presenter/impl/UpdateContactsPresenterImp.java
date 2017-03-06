package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.UpdateContactsPresenter;
import com.bangware.shengyibao.customer.presenter.onUpdateContactsListener;
import com.bangware.shengyibao.customer.view.UpdateContactsView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class UpdateContactsPresenterImp implements UpdateContactsPresenter,onUpdateContactsListener {
	public static final String REQUEST_TAG = "UpdateContacts";
	private String requestTag;
	private CustomerModel mCustomerModel;
	private UpdateContactsView mUpdateContactsView;
	
	public UpdateContactsPresenterImp(UpdateContactsView mUpdateContactsView) {
		super();
		requestTag = requestTag+System.currentTimeMillis();
		this.mCustomerModel = new CustomerModelImpl();
		this.mUpdateContactsView = mUpdateContactsView;
	}
   //修改联系人
	@Override
	public void updateContacts(User user, String contacts_id, String name,
							   String mobile1, String mobile2, String position) {
		mCustomerModel.updateContacts(requestTag, user, contacts_id,name,mobile1,mobile2,position, this);
	}
	//删除联系人
	@Override
	public void deleteContacts(User user, String contacts_id) {
		mCustomerModel.deleteContacts(requestTag, user, contacts_id, this);
	}

	@Override
	public void destory() {
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onUpdateContactsSuccess(String successMessage) {
		mUpdateContactsView.hideLoading();
		mUpdateContactsView.deleteContactsSuccessMessage(successMessage);
	}

	@Override
	public void onUpdateFailure(String errorMessage) {
		mUpdateContactsView.showMessage(errorMessage);
	}

	@Override
	public void ondeleteContactsSuccess(String successMessage) {
		mUpdateContactsView.hideLoading();
		mUpdateContactsView.deleteContactsSuccessMessage(successMessage);
	}

	@Override
	public void ondeleteFailure(String errorMessage) {
		mUpdateContactsView.showMessage(errorMessage);
	}

	
}
