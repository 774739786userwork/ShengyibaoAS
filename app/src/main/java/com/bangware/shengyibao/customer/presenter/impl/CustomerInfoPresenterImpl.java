package com.bangware.shengyibao.customer.presenter.impl;

import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.CustomerInfoPresenter;
import com.bangware.shengyibao.customer.presenter.OnCustomerInfoListener;
import com.bangware.shengyibao.customer.view.CustomerPurchaseView;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;



public class CustomerInfoPresenterImpl implements CustomerInfoPresenter,OnCustomerInfoListener {
	public static final String REQUEST_TAG = "Customer";
	private String requestTag;
	private CustomerModel mCustomerModel;
	private CustomerPurchaseView mPurchaseView;
	
	public CustomerInfoPresenterImpl(CustomerPurchaseView mPurchaseView) {
		// TODO Auto-generated constructor stub
		requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.mPurchaseView=mPurchaseView;
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	@Override
	public void queryCustomerInfoData(User user,String customer_id) {
		// TODO Auto-generated method stub
		mPurchaseView.showLoading();
		mCustomerModel.queryCustomerInfo(requestTag, user, customer_id, this);
	}

	@Override
	public void destory() {
		// TODO Auto-generated method stub
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}

	@Override
	public void onLoadCustomerInfo(List<Customer> customers) {
		// TODO Auto-generated method stub
		mPurchaseView.hideLoading();
		mPurchaseView.loadCustomerInfoData(customers);
	}

	@Override
	public void onError(String errorMessage) {
		// TODO Auto-generated method stub
		mPurchaseView.hideLoading();
		mPurchaseView.showMessage(errorMessage);
	}

}
