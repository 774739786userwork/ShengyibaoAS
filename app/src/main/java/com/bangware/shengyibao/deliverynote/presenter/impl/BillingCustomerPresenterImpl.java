package com.bangware.shengyibao.deliverynote.presenter.impl;
import com.bangware.shengyibao.customer.model.CustomerModel;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.customer.model.impl.CustomerModelImpl;
import com.bangware.shengyibao.customer.presenter.OnCustomerListener;
import com.bangware.shengyibao.customer.view.CustomerDetailView;
import com.bangware.shengyibao.customer.view.CustomerView;
import com.bangware.shengyibao.deliverynote.presenter.BillingCustomerPresenter;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.utils.volley.DataRequest;

import java.util.List;



public class BillingCustomerPresenterImpl implements BillingCustomerPresenter, OnCustomerListener {
	
	public  static final String  REQUEST_TAG = "BillingCustomer";
	private String requestTag;
	private CustomerModel mCustomerModel;
	private CustomerView mCustomerView;
	
	public BillingCustomerPresenterImpl(CustomerView newCustomersView) {
		this.requestTag = REQUEST_TAG+System.currentTimeMillis();
		this.mCustomerView=newCustomersView;
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	public BillingCustomerPresenterImpl(CustomerDetailView mDetailView) {
		// TODO Auto-generated constructor stub
		this.mCustomerModel = new CustomerModelImpl();
	}
	
	//调用客户列表数据加载方法传递参数
	@Override
	public void loadBilingCustomerData(int nPage, int nSpage, String phone,
			String shopName, String employeed_id, User user) {
		// TODO Auto-generated method stub
		mCustomerView.showLoading();
		mCustomerModel.loadBilingCustomerData(requestTag, user, nPage, nSpage, phone, shopName, employeed_id, this);
	}

	//调用客户列表数据加载方法传递对象
	@Override
	public void onLoadDataSuccess(List<Customer> customers) {
		// TODO Auto-generated method stub
		if(customers != null){
			mCustomerView.addCustomers(customers);
		}
		mCustomerView.hideLoading();
	}


	//加载失败
	@Override
	public void onLoadDataFailure(String errorMessage) {
		// TODO Auto-generated method stub
		mCustomerView.hideLoading();
		mCustomerView.showLoadFailureMsg(errorMessage);
	}

	public void destroy(){
		DataRequest.getInstance().cancelRequestByTag(requestTag);
	}


	
}
